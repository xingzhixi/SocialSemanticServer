/**
* Code contributed to the Learning Layers project
* http://www.learning-layers.eu
* Development is partly funded by the FP7 Programme of the European Commission under
* Grant Agreement FP7-ICT-318209.
* Copyright (c) 2014, Graz University of Technology - KTI (Knowledge Technologies Institute).
* For a list of contributors see the AUTHORS file at the top-level directory of this distribution.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package at.kc.tugraz.ss.service.filerepo.impl;

import at.kc.tugraz.socialserver.utils.SSFileExtE;
import at.kc.tugraz.socialserver.utils.SSFileExtU;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.socialserver.utils.SSFileU;
import at.kc.tugraz.socialserver.utils.SSHTMLU;
import at.kc.tugraz.socialserver.utils.SSLogU;
import at.kc.tugraz.socialserver.utils.SSMimeTypeU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.adapter.socket.datatypes.SSSocketCon;
import at.kc.tugraz.ss.conf.conf.SSCoreConf;
import at.kc.tugraz.ss.datatypes.datatypes.enums.SSEntityE;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.serv.serv.api.SSServImplStartA;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import at.kc.tugraz.ss.service.filerepo.conf.SSFileRepoConf;
import at.kc.tugraz.ss.service.filerepo.datatypes.pars.SSFileUploadPar;
import at.kc.tugraz.ss.service.filerepo.datatypes.rets.SSFileUploadRet;
import at.kc.tugraz.ss.service.filerepo.impl.fct.SSFileServCaller;
import com.googlecode.sardine.SardineFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SSFileUploader extends SSServImplStartA{
  
  private final SSFileUploadPar       par;
  private       String                fileExt;
  private       FileOutputStream      fileOutputStream  = null;
  private       FileInputStream       fileInputStream   = null;
  private       String                fileId            = null;
  private       byte[]                fileChunk         = null;
  private       SSUri                 uri               = null;
  private       SSSocketCon           sSCon             = null;
  private       String                localWorkPath     = null;
  
  public SSFileUploader(
    final SSFileRepoConf     fileRepoConf, 
    final SSSocketCon        sSCon, 
    final SSServPar          par) throws Exception{
    
    super(fileRepoConf);
    
    this.sSCon             = sSCon;
    this.par               = new SSFileUploadPar(par);
    this.localWorkPath     = SSCoreConf.instGet().getSsConf().getLocalWorkPath();
    
    try{
      this.fileExt           = SSMimeTypeU.fileExtForMimeType             (this.par.mimeType);
      this.uri               = SSServCaller.vocURICreate                  (SSFileExtE.valueOf(this.fileExt));
      this.fileId            = SSServCaller.fileIDFromURI                 (par.user, uri);
      this.fileOutputStream  = SSFileU.openOrCreateFileWithPathForWrite   (localWorkPath + fileId);
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  @Override
  public void run(){
    
    try{
      
//    check whether WebSocket connections need this: 
      sendAnswer();
      
      while(true){
        
        fileChunk = sSCon.readFileChunkFromClient();
        
        if(fileChunk.length != 0){
          
          fileOutputStream.write        (fileChunk);
          fileOutputStream.flush        ();
          
//     check whether WebSocket connections need this: sendAnswer(SSStrU.valueGot);
          continue;
        }
        
        fileOutputStream.close();
        
        switch(((SSFileRepoConf)conf).fileRepoType){
          case fileSys: moveFileToLocalRepo(); break;
          case webdav:  uploadFileToWebDav();  break;
          case i5Cloud: uploadFileToI5Cloud(); break;
        }
        
        SSFileServCaller.addFileEntity           (par, uri,    true);
        SSFileServCaller.addFileContentsToSolr   (par, fileId, true);

        removeFileFromLocalWorkFolder();
        
        createFileThumb();
        
        sendAnswer();
        return;
      }
    }catch(Exception error1){
      
      SSServErrReg.regErr(error1);
      
      try{
        sSCon.writeErrorFullToClient(SSServErrReg.getServiceImplErrors(), par.op);
      }catch(Exception error2){
        SSServErrReg.regErr(error2);
      }
    }finally{
      
      if(fileOutputStream != null){
        
        try{
          fileOutputStream.close();
        }catch(IOException ex){
          SSLogU.err(ex);
        }
      }
      
      try{
        finalizeImpl();
      }catch(Exception error3){
        SSLogU.err(error3);
      }
    }
  }
  
  @Override
  protected void finalizeImpl() throws Exception{
    finalizeThread();
  }
  
  private void sendAnswer() throws Exception{
    sSCon.writeRetFullToClient(SSFileUploadRet.get(uri, par.op));
  }
  
  private void removeFileFromLocalWorkFolder() throws Exception{
    
    if(SSStrU.equals(localWorkPath, ((SSFileRepoConf)conf).getPath())){
      return;
    }
    
    try{
      SSFileU.delFile(localWorkPath + fileId);
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  private void moveFileToLocalRepo() throws Exception{
    
    if(SSStrU.equals(localWorkPath, ((SSFileRepoConf)conf).getPath())){
      return;
    }
    
    try{
      final File file = new File(localWorkPath + fileId);
      
      if(!file.renameTo(new File(((SSFileRepoConf)conf).getPath() + fileId))){
        throw new Exception("couldnt move file to local file repo");
      }
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  private void uploadFileToWebDav() throws Exception{
    
    try{
      fileInputStream = SSFileU.openFileForRead(localWorkPath + fileId);
      
      SardineFactory.begin(
        ((SSFileRepoConf)conf).user,
        ((SSFileRepoConf)conf).password).put(((SSFileRepoConf)conf).getPath() + fileId, fileInputStream);
      
      fileInputStream.close();
    }catch(Exception error){
      SSServErrReg.regErr(error);
    }
  }
  
  private void uploadFileToI5Cloud() throws Exception{

    try{
      SSServCaller.i5CloudFileUpload(this.fileId, "private", SSServCaller.i5CloudAuth().get(SSHTMLU.xAuthToken));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  //TODO dtheiler: currently works with local file repository only (not web dav or any remote stuff; even dont if localWorkPath != local file repo path)
  private void createFileThumb(){
    
    try{
      final String filePath          = localWorkPath + fileId;
      final SSUri  pngFileUri        = SSServCaller.vocURICreate                  (SSFileExtE.png);
      final String pngFilePath       = localWorkPath + SSServCaller.fileIDFromURI (par.user, pngFileUri);
      final String pdfFilePath       = localWorkPath + SSServCaller.fileIDFromURI (par.user, SSServCaller.vocURICreate     (SSFileExtE.pdf));
      Boolean      thumbCreated      = false;
      
      if(SSFileExtU.imageFileExts.contains(fileExt)){
        SSFileU.scalePNGAndWrite(ImageIO.read(new File(filePath)), pngFilePath, 500, 500);
        thumbCreated = true;
      }
      
      if(SSStrU.equals(SSFileExtE.pdf, fileExt)){
        SSFileU.writeScaledPNGFromPDF(filePath, pngFilePath, 500, 500, false);
        thumbCreated = true;
      }
      
      if(SSStrU.equals(SSFileExtE.doc, fileExt)){
        SSFileU.writePDFFromDoc       (filePath,    pdfFilePath);
        SSFileU.writeScaledPNGFromPDF (pdfFilePath, pngFilePath, 500, 500, false);
        thumbCreated = true;
      }
      
      if(thumbCreated){
        
        SSServCaller.entityEntityToPrivCircleAdd(
          par.user, 
          pngFileUri, 
          SSEntityE.thumbnail, 
          par.label, 
          null, 
          null, 
          false);
        
        SSServCaller.entityThumbAdd(
          par.user, 
          uri, 
          pngFileUri, 
          true);
      }
      
    }catch(Exception error){
      SSLogU.warn("thumb couldnt be created from file with ext :" + fileExt);
    }
  }
}