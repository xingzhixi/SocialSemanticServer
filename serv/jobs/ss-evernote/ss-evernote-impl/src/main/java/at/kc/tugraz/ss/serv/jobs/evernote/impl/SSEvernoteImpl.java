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
package at.kc.tugraz.ss.serv.jobs.evernote.impl;

import at.kc.tugraz.socialserver.utils.SSFileU;
import at.kc.tugraz.socialserver.utils.SSLogU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.conf.conf.SSCoreConf;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSEntityDescA;
import at.kc.tugraz.ss.datatypes.datatypes.enums.SSEntityE;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.par.SSEntityDescGetPar;
import at.kc.tugraz.ss.serv.db.api.SSDBSQLI;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.serv.jobs.evernote.api.SSEvernoteClientI;
import at.kc.tugraz.ss.serv.jobs.evernote.api.SSEvernoteServerI;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteInfo;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNoteDesc;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNoteStoreGetPar;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNotebooksGetPar;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNotebooksLinkedGetPar;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNotebooksSharedGetPar;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNotesGetPar;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNotesLinkedGetPar;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteResourceDesc;
import at.kc.tugraz.ss.serv.jobs.evernote.impl.fct.sql.SSEvernoteSQLFct;
import at.kc.tugraz.ss.serv.serv.api.SSConfA;
import at.kc.tugraz.ss.serv.serv.api.SSEntityDescriberI;
import at.kc.tugraz.ss.serv.serv.api.SSEntityHandlerImplI;
import at.kc.tugraz.ss.serv.serv.api.SSServImplWithDBA;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.edam.notestore.NotesMetadataList;
import com.evernote.edam.notestore.NotesMetadataResultSpec;
import com.evernote.edam.notestore.SyncChunk;
import com.evernote.edam.type.LinkedNotebook;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.SharedNotebook;
import java.util.ArrayList;
import java.util.List;

public class SSEvernoteImpl extends SSServImplWithDBA implements SSEvernoteClientI, SSEvernoteServerI, SSEntityHandlerImplI, SSEntityDescriberI{
  
  private final SSEvernoteSQLFct sqlFct;
  
  public SSEvernoteImpl(final SSConfA conf, final SSDBSQLI dbSQL) throws Exception{
    
    super(conf, null, dbSQL);
    
    this.sqlFct = new SSEvernoteSQLFct(dbSQL);
  }

  @Override
  public SSEntityDescA getDescForEntity(
    final SSEntityDescGetPar par,
    final SSEntityDescA      entityDesc) throws Exception{
    
    if(
      SSStrU.equals(entityDesc.type, SSEntityE.evernoteNote) ||
      SSStrU.equals(entityDesc.type, SSEntityE.evernoteResource)){
      
      if(par.getThumb){
      
        entityDesc.thumb = 
         getThumbBase64(
           par.user, 
           par.entity);
      }
    }
    
    if(SSStrU.equals(entityDesc.type, SSEntityE.evernoteNote)){
     
      return SSEvernoteNoteDesc.get(
        entityDesc,
        sqlFct.getNote(par.entity).notebook);
    }
    
    if(SSStrU.equals(entityDesc.type, SSEntityE.evernoteResource)){
      
      return SSEvernoteResourceDesc.get(
        entityDesc,
        sqlFct.getResource(par.entity).note);
    }
    
    return entityDesc;
  }
  
  private String getThumbBase64(
    final SSUri  user,
    final SSUri  file) throws Exception{
    
    try{
      
      final List<SSUri> thumbUris = SSServCaller.entityThumbsGet(user, file);
      
      if(thumbUris.isEmpty()){
        SSLogU.warn("thumb couldnt be retrieved from file " + file);
        return null;
      }
      
      final String pngFilePath = SSCoreConf.instGet().getSsConf().getLocalWorkPath() + SSServCaller.fileIDFromURI (user, thumbUris.get(0));
      
      return SSFileU.readPNGToBase64Str(pngFilePath);
      
    }catch(Exception error){
      SSLogU.warn("base 64 file thumb couldnt be retrieved");
      return null;
    }
  }
  
  @Override
  public Boolean copyUserEntity(
    final SSUri        user,
    final List<SSUri>  users,
    final SSUri        entity,
    final List<SSUri>  entitiesToExclude,
    final SSEntityE    entityType) throws Exception{
    
    return false;
  }
  
  @Override
  public List<SSUri> getSubEntities(
    final SSUri         user,
    final SSUri         entity,
    final SSEntityE     type) throws Exception{

    return null;
  }
    
  @Override
  public Boolean setUserEntityPublic(
    final SSUri          userUri,
    final SSUri          entityUri, 
    final SSEntityE   entityType,
    final SSUri          publicCircleUri) throws Exception{

    return false;
  }
  
  @Override
  public Boolean shareUserEntity(
    final SSUri          userUri, 
    final List<SSUri>    userUrisToShareWith,
    final SSUri          entityUri, 
    final SSUri          entityCircleUri,
    final SSEntityE   entityType) throws Exception{
    
    return false;
  }
  
  @Override
  public Boolean addEntityToCircle(
    final SSUri        userUri, 
    final SSUri        circleUri, 
    final SSUri        entityUri, 
    final SSEntityE entityType) throws Exception{
    
    return false;
  }
  
  @Override
  public void removeDirectlyAdjoinedEntitiesForUser(
    final SSUri       userUri, 
    final SSEntityE   entityType,
    final SSUri       entityUri,
    final Boolean     removeUserTags,
    final Boolean     removeUserRatings,
    final Boolean     removeFromUserColls,
    final Boolean     removeUserLocations) throws Exception{
  }
  
  @Override
  public SSEvernoteInfo evernoteNoteStoreGet(SSServPar parA) throws Exception {
    
    SSEvernoteNoteStoreGetPar par    = new SSEvernoteNoteStoreGetPar(parA);
    SSEvernoteInfo            result = null;
    EvernoteAuth              evernoteAuth;
    ClientFactory             clientFactory;
    
    try{
      
      evernoteAuth   = new EvernoteAuth   (EvernoteService.PRODUCTION, par.authToken);
      clientFactory  = new ClientFactory  (evernoteAuth);
     
      UserStoreClient userStore = clientFactory.createUserStoreClient();
      NoteStoreClient noteStore = clientFactory.createNoteStoreClient();
      SSUri           shardUri  = SSUri.get(userStore.getPublicUserInfo(userStore.getUser().getUsername()).getWebApiUrlPrefix());
      
      result         = SSEvernoteInfo.get (userStore, noteStore, shardUri);
      
//      https://sandbox.evernote.com/shard/s1/sh/72ddd50f-5d13-46e3-b32d-d2b314ced5c1/ea77ae0587d735f39a94868ce3ddab5f
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  @Override 
  public List<Notebook> evernoteNotebooksGet(SSServPar parA) throws Exception {
    
    SSEvernoteNotebooksGetPar par       = new SSEvernoteNotebooksGetPar(parA);
    List<Notebook>            notebooks = null;
    
    try{
			notebooks = par.noteStore.listNotebooks();
    }catch (Exception error){
			SSServErrReg.regErrThrow(error);
		}
		
		return notebooks;
  }
  
  @Override 
  public List<SharedNotebook> evernoteNotebooksSharedGet(SSServPar parA) throws Exception {
    
    SSEvernoteNotebooksSharedGetPar par             = new SSEvernoteNotebooksSharedGetPar(parA);
    List<SharedNotebook>            sharedNotebooks = null;
    
    try{
			sharedNotebooks = par.noteStore.listSharedNotebooks();
    }catch (Exception error){
			SSServErrReg.regErrThrow(error);
		}
		
		return sharedNotebooks;
  }
  
  @Override 
  public List<LinkedNotebook> evernoteNotebooksLinkedGet(SSServPar parA) throws Exception{
    
    SSEvernoteNotebooksLinkedGetPar par             = new SSEvernoteNotebooksLinkedGetPar(parA);
    List<LinkedNotebook>            linkedNotebooks = null;
    
    try{
			linkedNotebooks = par.noteStore.listLinkedNotebooks();
    }catch (Exception error){
			SSServErrReg.regErrThrow(error);
		}
		
		return linkedNotebooks;
  }
  
  @Override
  public List<Note> evernoteNotesGet(SSServPar parA) throws Exception {
    
    SSEvernoteNotesGetPar   par        = new SSEvernoteNotesGetPar(parA);
    List<Note>              notes      = new ArrayList<>();
    NotesMetadataResultSpec resultSpec = new NotesMetadataResultSpec();
    NoteFilter              noteFilter = new NoteFilter();
    NotesMetadataList       noteList;
//    NoteCollectionCounts    noteCount;
    
    try{
      
      resultSpec.setIncludeAttributes(true);
      resultSpec.setIncludeCreated(true);
      resultSpec.setIncludeDeleted(true);
      resultSpec.setIncludeNotebookGuid(true);
      resultSpec.setIncludeTitle(true);
      resultSpec.setIncludeUpdated(true);
      
      noteFilter.setNotebookGuid(par.notebookGuid);
      
//      noteCount = par.noteStore.findNoteCounts    (noteFilter, false);
      noteList  = par.noteStore.findNotesMetadata (noteFilter, 0, 100, resultSpec);
      
      for (NoteMetadata note : noteList.getNotes()) {
        notes.add(par.noteStore.getNote(note.getGuid(), true, false, false, false));
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return notes;
  }
  
  @Override
  public List<Note> evernoteNotesLinkedGet(final SSServPar parA) throws Exception{
    
    SSEvernoteNotesLinkedGetPar par       = new SSEvernoteNotesLinkedGetPar(parA);
    List<Note>                  notes     = new ArrayList<>();
    SyncChunk                   synChunk;
    
    try{
			synChunk = par.noteStore.getLinkedNotebookSyncChunk(par.linkedNotebook, 0, 256, true);
      notes    = synChunk.getNotes();
      
    }catch (Exception error){
      SSServErrReg.regErrThrow(error);
		}
		
		return notes;
  }
}