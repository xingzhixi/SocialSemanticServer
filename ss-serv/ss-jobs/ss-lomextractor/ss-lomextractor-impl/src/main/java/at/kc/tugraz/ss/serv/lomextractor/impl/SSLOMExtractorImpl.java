/**
 * Copyright 2013 Graz University of Technology - KTI (Knowledge Technologies Institute)
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
package at.kc.tugraz.ss.serv.lomextractor.impl;

import at.kc.tugraz.socialserver.utils.SSEncodingU;
import at.kc.tugraz.socialserver.utils.SSFileU;
import at.kc.tugraz.socialserver.utils.SSMethU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.adapter.socket.datatypes.SSSocketCon;
import at.kc.tugraz.ss.serv.lomextractor.api.SSLOMExtractorClientI;
import at.kc.tugraz.ss.serv.lomextractor.api.SSLOMExtractorServerI;
import at.kc.tugraz.ss.serv.lomextractor.conf.SSLOMExtractorConf;
import at.kc.tugraz.ss.serv.lomextractor.datatypes.SSLOMResource;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.serv.serv.api.SSServImplMiscA;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSLOMExtractorImpl extends SSServImplMiscA implements SSLOMExtractorClientI, SSLOMExtractorServerI{
  
  public SSLOMExtractorImpl(final SSLOMExtractorConf conf) throws Exception{
    super(conf);
  }
  
  /****** SSServRegisterableImplI ******/
  
  @Override
  public List<SSMethU> publishClientOps() throws Exception{
    
    List<SSMethU> clientOps = new ArrayList<SSMethU>();
        
    Method[] methods = SSLOMExtractorClientI.class.getMethods();
    
    for(Method method : methods){
      clientOps.add(SSMethU.get(method.getName()));
    }
    
    return clientOps;
  }
  
  @Override
  public List<SSMethU> publishServerOps() throws Exception{
    
    List<SSMethU> serverOps = new ArrayList<SSMethU>();
    
    Method[] methods = SSLOMExtractorServerI.class.getMethods();
    
    for(Method method : methods){
      serverOps.add(SSMethU.get(method.getName()));
    }
    
    return serverOps;
  }
  
  @Override
  public void handleClientOp(SSSocketCon sSCon, SSServPar par) throws Exception{
    SSLOMExtractorClientI.class.getMethod(SSMethU.toStr(par.op), SSSocketCon.class, SSServPar.class).invoke(this, sSCon, par);
  }
  
  @Override
  public Object handleServerOp(SSServPar par) throws Exception{
    return SSLOMExtractorServerI.class.getMethod(SSMethU.toStr(par.op), SSServPar.class).invoke(this, par);
  }
  
  /****** SSLOMExtractorClientI ******/
  
  /****** SSLOMExtractorServerI ******/
  
  @Override
  public boolean lomExtractFromDir(SSServPar par) throws Exception {
    
    SSLOMExtractorOutHandler   out       = new SSLOMExtractorOutHandler(this);
    SSLOMExtractorStats        stat      = new SSLOMExtractorStats();
    Map<String, SSLOMResource> resources = new HashMap<String, SSLOMResource>();
    Date                       start     = new Date();
    
    extractFromDir                      (resources, SSFileU.filesForDirPath(((SSLOMExtractorConf)conf).inputDirPath), stat);
    
    stat.calc                           (resources);
    
    out.writeResourcesToRecommInputFile (resources, (SSLOMExtractorConf)conf);
    out.writeResourcesToVizInputFile    (resources, (SSLOMExtractorConf)conf);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.en);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.hu);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.ro);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.fr);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.de);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.et);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.ru);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.el);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.no);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.lv);
    out.writeResourcesToLangFile        (resources, (SSLOMExtractorConf)conf, SSEncodingU.es);
    
    stat.stat(start);
    
    return true;
  }
  
  private void extractFromDir(
    Map<String, SSLOMResource> resources, 
    File[]                     files,
    SSLOMExtractorStats        stat) throws Exception{
    
    for(File file : files){
      
      if(file.isDirectory()){
        
        if(
          SSStrU.notEquals(file.getName(), SSStrU.valueOEORGANICEPRINTS) &&
          ((SSLOMExtractorConf)conf).useEdunet){
          extractFromDir(resources, file.listFiles(), stat);  
        }
        
        if(
          SSStrU.equals(file.getName(), SSStrU.valueOEORGANICEPRINTS) &&
          ((SSLOMExtractorConf)conf).useEPrints){
          extractFromDir(resources, file.listFiles(), stat);  
        }
        
      }else{
        stat.totalLOMFileCount++;
        new SSLOMExtractorInHandler().extractLOM(resources, file, stat);
      }
    }
  }
}