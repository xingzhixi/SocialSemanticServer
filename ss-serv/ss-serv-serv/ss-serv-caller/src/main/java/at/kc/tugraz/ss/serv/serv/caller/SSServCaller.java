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
package at.kc.tugraz.ss.serv.serv.caller;

import at.kc.tugraz.socialserver.service.broadcast.datatypes.enums.SSBroadcastEnum;
import at.kc.tugraz.socialserver.utils.SSMethU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSLabelStr;
import at.kc.tugraz.ss.datatypes.datatypes.SSSpaceEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSTagLabel;
import at.kc.tugraz.ss.datatypes.datatypes.SSUEEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityDescA;
import at.kc.tugraz.ss.serv.datatypes.learnep.datatypes.SSLearnEp;
import at.kc.tugraz.ss.serv.datatypes.learnep.datatypes.SSLearnEpVersion;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSEntityCircleTypeE;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSEntityRightTypeE;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSCircle;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSEntity;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteInfo;
import at.kc.tugraz.ss.serv.modeling.ue.datatypes.SSModelUEMILabel;
import at.kc.tugraz.ss.serv.serv.api.SSServA;
import at.kc.tugraz.ss.serv.solr.datatypes.SSSolrKeywordLabel;
import at.kc.tugraz.ss.service.coll.datatypes.SSColl;
import at.kc.tugraz.ss.service.disc.datatypes.SSDisc;
import at.kc.tugraz.ss.service.filerepo.datatypes.rets.SSFileCanWriteRet;
import at.kc.tugraz.ss.service.rating.datatypes.SSRatingOverall;
import at.kc.tugraz.ss.service.tag.datatypes.SSTag;
import at.kc.tugraz.ss.service.tag.datatypes.SSTagFrequ;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSUser;
import at.kc.tugraz.ss.service.userevent.datatypes.SSUE;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.type.LinkedNotebook;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.SharedNotebook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSServCaller {
  
  public static SSUri createLearnEp(SSUri userUri, SSLabelStr label, SSSpaceEnum space, Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSUri               result = null;
    
    try{
      opPars.put(SSVarU.shouldCommit,  shouldCommit);
      opPars.put(SSVarU.user,          userUri);
      opPars.put(SSVarU.label,         label);
      opPars.put(SSVarU.space,         space);
      
      result = (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpCreate, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static SSUri addLearnEpVersionCircle(
    SSUri      userUri,
    SSUri      learnEpVersionUri,
    SSLabelStr label,
    Float      xLabel,
    Float      yLabel,
    Float      xR,
    Float      yR,
    Float      xC,
    Float      yC,
    Boolean    shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSUri               result = null;
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpVersionUri, learnEpVersionUri);
      opPars.put(SSVarU.label,             label);
      opPars.put(SSVarU.xLabel,            xLabel);
      opPars.put(SSVarU.yLabel,            yLabel);
      opPars.put(SSVarU.xR,                xR);
      opPars.put(SSVarU.yR,                yR);
      opPars.put(SSVarU.xC,                xC);
      opPars.put(SSVarU.yC,                yC);
      
      result = (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionAddCircle, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static SSUri addLearnEpVersionEntity(
    SSUri   userUri,
    SSUri   learnEpVersionUri,
    SSUri   entityUri,
    Float   x,
    Float   y,
    Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSUri               result = null;
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpVersionUri, learnEpVersionUri);
      opPars.put(SSVarU.entityUri,         entityUri);
      opPars.put(SSVarU.x,                 x);
      opPars.put(SSVarU.y,                 y);
      
      result = (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionAddEntity, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void setLearnEpCurrentVersion(SSUri userUri, SSUri learnEpVersionUri, Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpVersionUri, learnEpVersionUri);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionCurrentSet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static SSUri setLearnEpVersionTimelineState(
    SSUri   userUri,
    SSUri   learnEpVersionUri,
    Long    startTime,
    Long    endTime,
    Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSUri               result = null;
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpVersionUri, learnEpVersionUri);
      opPars.put(SSVarU.startTime,         startTime);
      opPars.put(SSVarU.endTime,           endTime);
      
      result = (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionSetTimelineState, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static SSUri createLearnEpVersion(SSUri userUri, SSUri learnEpUri, Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSUri               result = null;
    
    try{
      opPars.put(SSVarU.shouldCommit,  shouldCommit);
      opPars.put(SSVarU.user,          userUri);
      opPars.put(SSVarU.learnEpUri,    learnEpUri);
      
      result = (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionCreate, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static SSLearnEpVersion getCurrentLearnEpVersion(SSUri userUri) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSLearnEpVersion    result = null;
    
    try{
      opPars.put(SSVarU.user, userUri);
      
      result = (SSLearnEpVersion) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionCurrentGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void entityAddAtCreationTime(
    final SSUri        userUri,
    final SSUri        uri,
    final SSLabelStr   label,
    final Long         creationTime,
    final SSEntityEnum type,
    final Boolean      shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    uri);
    opPars.put(SSVarU.label,        label);
    opPars.put(SSVarU.creationTime, creationTime);
    opPars.put(SSVarU.entityType,   type);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.entityAddAtCreationTime, opPars));
  }
  
  public static void addUser(
    final SSUri      userUri,
    final SSLabelStr userLabel,
    final Boolean    shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.userUri,      userUri);
    opPars.put(SSVarU.userLabel,    userLabel);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.userAdd, opPars));
  }
  
  public static void entityAdd(
    final SSUri        userUri,
    final SSUri        uri,
    final SSLabelStr   label,
    final SSEntityEnum type,
    final Boolean      shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    uri);
    opPars.put(SSVarU.label,        label);
    opPars.put(SSVarU.entityType,   type);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.entityAdd, opPars));
  }
  
  public static List<SSUE> getUEs(
    SSUri    userUri,
    SSUri    forUserUri,
    SSUri    entityUri,
    SSUEEnum eventType,
    Long     startTime,
    Long     endTime) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    List<SSUE>          result = null;
    
    try{
      opPars.put(SSVarU.user,      userUri);
      opPars.put(SSVarU.forUser,   forUserUri);
      opPars.put(SSVarU.resource,  entityUri);
      opPars.put(SSVarU.eventType, eventType);
      opPars.put(SSVarU.startTime, startTime);
      opPars.put(SSVarU.endTime,   endTime);
      
      result = (List<SSUE>) SSServA.callServViaServer(new SSServPar(SSMethU.uEsGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void formatAudioAndVideoFileNamesInDir() throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      SSServA.callServViaServer(new SSServPar(SSMethU.fileSysLocalFormatAudioAndVideoFileNamesInDir, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static List<Note> getEvernoteNotes(NoteStoreClient noteStore, String notebookGuid) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    List<Note>          result = null;
    
    try{
      opPars.put(SSVarU.noteStore,     noteStore);
      opPars.put(SSVarU.notebookGuid,  notebookGuid);
      
      result = (List<Note>) SSServA.callServViaServer(new SSServPar(SSMethU.evernoteNotesGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static List<Notebook> getEvernoteNotebooks(NoteStoreClient noteStore) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    List<Notebook>      result = null;
    
    try{
      opPars.put(SSVarU.noteStore,     noteStore);
      
      result = (List<Notebook>) SSServA.callServViaServer(new SSServPar(SSMethU.evernoteNotebooksGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void addTextToFileNamesAtBeginInDir() throws Exception{
    
    Map<String, Object>  opPars = new HashMap<String, Object>();
    
    try{
      SSServA.callServViaServer(new SSServPar(SSMethU.fileSysLocalAddTextToFilesNamesAtBeginInDir, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static List<SharedNotebook> getEvernoteSharedNotebooks(NoteStoreClient noteStore) throws Exception{
    
    Map<String, Object>  opPars = new HashMap<String, Object>();
    List<SharedNotebook> result = null;
    
    try{
      opPars.put(SSVarU.noteStore,     noteStore);
      
      result = (List<SharedNotebook>) SSServA.callServViaServer(new SSServPar(SSMethU.evernoteNotebooksSharedGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static SSEvernoteInfo getEvernoteInfo(SSUri userUri, String authToken) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSEvernoteInfo      result = null;
    
    try{
      opPars.put(SSVarU.user,      userUri);
      opPars.put(SSVarU.authToken, authToken);
      
      result = (SSEvernoteInfo) SSServA.callServViaServer(new SSServPar(SSMethU.evernoteNoteStoreGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static List<Note> getEvernoteLinkedNotes(NoteStoreClient noteStore, LinkedNotebook linkedNotebook) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    List<Note>          result = null;
    
    try{
      opPars.put(SSVarU.noteStore,      noteStore);
      opPars.put(SSVarU.linkedNotebook, linkedNotebook);
      
      result = (List<Note>) SSServA.callServViaServer(new SSServPar(SSMethU.evernoteNotesLinkedGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static List<LinkedNotebook> getEvernoteLinkedNotebooks(NoteStoreClient noteStore) throws Exception{
    
    Map<String, Object>  opPars = new HashMap<String, Object>();
    List<LinkedNotebook> result = null;
    
    try{
      opPars.put(SSVarU.noteStore,     noteStore);
      
      result = (List<LinkedNotebook>) SSServA.callServViaServer(new SSServPar(SSMethU.evernoteNotebooksLinkedGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void entityLabelSet(
    final SSUri      entityUri,
    final SSLabelStr label,
    final Boolean    shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.entityUri,    entityUri);
    opPars.put(SSVarU.label,        label);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.entityLabelSet, opPars));
  }
    
  public static void importDataFromEvernote(SSUri userUri, String authToken, Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      opPars.put(SSVarU.user,         userUri);
      opPars.put(SSVarU.authToken,    authToken);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.dataImportEvernote, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static void addUEAtCreationTime(
    SSUri    userUri,
    SSUri    entityUri,
    SSUEEnum evenType,
    String   content,
    Long     creationTime,
    Boolean  shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      opPars.put(SSVarU.user,         userUri);
      opPars.put(SSVarU.resource,     entityUri);
      opPars.put(SSVarU.eventType,    evenType);
      opPars.put(SSVarU.content,      content);
      opPars.put(SSVarU.creationTime, creationTime);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.uEAddAtCreationTime, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static void addTagsAtCreationTime(SSUri userUri, SSUri entityUri, List<SSTagLabel> tagList, SSSpaceEnum space, Long creationTime, Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      opPars.put(SSVarU.user,         userUri);
      opPars.put(SSVarU.resource,     entityUri);
      opPars.put(SSVarU.tagStrings,   tagList);
      opPars.put(SSVarU.space,        space);
      opPars.put(SSVarU.creationTime, creationTime);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.tagsAddAtCreationTime, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static SSUri logUserIn(SSLabelStr userLabel, Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.userLabel,    userLabel);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.userLogin, opPars));
  }
  
  public static List<SSUri> getAllUsers() throws Exception{
    return (List<SSUri>) SSServA.callServViaServer(new SSServPar(SSMethU.userAll, new HashMap<String, Object>()));
  }
  
  public static List<SSUser> usersGet(
    final List<SSUri> userUris) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.userUris, userUris);
    
    return (List<SSUser>) SSServA.callServViaServer(new SSServPar(SSMethU.usersGet, opPars));
  }
  
  public static SSLearnEpVersion getLearnEpVersion(SSUri userUri, SSUri learnEpCurrentVersionUri) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSLearnEpVersion    result = null;
    
    try{
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpVersionUri, learnEpCurrentVersionUri);
      
      result = (SSLearnEpVersion) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void updateLearnEpVersionCircle(
    SSUri      userUri,
    SSUri      learnEpCircleUri,
    SSLabelStr label,
    Float      xLabel,
    Float      yLabel,
    Float      xR,
    Float      yR,
    Float      xC,
    Float      yC,
    Boolean    shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpCircleUri,  learnEpCircleUri);
      opPars.put(SSVarU.label,             label);
      opPars.put(SSVarU.xLabel,            xLabel);
      opPars.put(SSVarU.yLabel,            yLabel);
      opPars.put(SSVarU.xR,                xR);
      opPars.put(SSVarU.yR,                yR);
      opPars.put(SSVarU.xC,                xC);
      opPars.put(SSVarU.yC,                yC);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionUpdateCircle, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static void updateLearnEpVersionEntity(
    SSUri   userUri,
    SSUri   learnEpEntityUri,
    SSUri   entityUri,
    Float   x,
    Float   y,
    Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpEntityUri,  learnEpEntityUri);
      opPars.put(SSVarU.entityUri,         entityUri);
      opPars.put(SSVarU.x,                 x);
      opPars.put(SSVarU.y,                 y);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionUpdateEntity, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static void removeLearnEpVersionCircle(
    SSUri   userUri,
    SSUri   learnEpCircleUri,
    Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpCircleUri,  learnEpCircleUri);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionRemoveCircle, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static void removeLearnEpVersionEntity(
    SSUri   userUri,
    SSUri   learnEpEntityUri,
    Boolean shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit,      shouldCommit);
      opPars.put(SSVarU.user,              userUri);
      opPars.put(SSVarU.learnEpEntityUri,  learnEpEntityUri);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionRemoveEntity, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static List<SSLearnEp> getLearnEps(SSUri userUri) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    List<SSLearnEp>     result = null;
    
    try{
      opPars.put(SSVarU.user, userUri);
      
      result = (List<SSLearnEp>) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpsGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static List<SSLearnEpVersion> getLearnEpVersions(SSUri userUri, SSUri learnEpUri) throws Exception{
    
    Map<String, Object>     opPars = new HashMap<String, Object>();
    List<SSLearnEpVersion>  result = null;
    
    try{
      opPars.put(SSVarU.user,       userUri);
      opPars.put(SSVarU.learnEpUri, learnEpUri);
      
      result = (List<SSLearnEpVersion>) SSServA.callServViaServer(new SSServPar(SSMethU.learnEpVersionsGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  public static void broadCastUpdate(
    SSUri           userUri,
    SSUri           enityUri,
    SSBroadcastEnum broadcastType,
    Boolean         shouldCommit) throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      opPars.put(SSVarU.user,         userUri);
      opPars.put(SSVarU.resource,     enityUri);
      opPars.put(SSVarU.type,         broadcastType);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.broadcastUpdate, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }

  public static SSUri vocURIPrefixGet() throws Exception{
    
    Map<String, Object> opPars = new HashMap<String, Object>();
    SSUri               result = null;
    
    try{
      result = (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.vocURIPrefixGet, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
    
    return result;
  }
  
  /* colls */
  
  public static List<SSUri> collSearchWithTagWithin(
    final SSUri      userUri, 
    final SSUri      collUri, 
    final SSTagLabel tag) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,               userUri);
    opPars.put(SSVarU.collUri,            collUri);
    opPars.put(SSVarU.tag,                tag);
    
    return (List<SSUri>) SSServA.callServViaServer(new SSServPar(SSMethU.collSearchWithTagWithin, opPars)); 
  }
    
  public static SSUri collToCircleAdd(
    final SSUri   userUri, 
    final SSUri   collCircleUri, 
    final SSUri   collUri,
    final Boolean shouldCommit) throws Exception{
     
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,               userUri);
    opPars.put(SSVarU.collUri,            collUri);
    opPars.put(SSVarU.collCircleUri,      collCircleUri);
    opPars.put(SSVarU.shouldCommit,       shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.collToCircleAdd, opPars)); 
  }
    
  public static SSUri collUserShareWithUser(
    final SSUri    userUri, 
    final SSUri    userUriToShareWith, 
    final SSUri    collUri, 
    final SSUri    collCircleUri, 
    final Boolean  shouldCommit) throws Exception{
  
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,               userUri);
    opPars.put(SSVarU.userUriToShareWith, userUriToShareWith);
    opPars.put(SSVarU.collUri,            collUri);
    opPars.put(SSVarU.collCircleUri,      collCircleUri);
    opPars.put(SSVarU.shouldCommit,       shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.collUserShareWithUser, opPars)); 
  }
  
  public static SSColl collUserWithEntries(
    final SSUri userUri, 
    final SSUri collUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user, userUri);
    opPars.put(SSVarU.coll, collUri);
    
    return (SSColl) SSServA.callServViaServer(new SSServPar(SSMethU.collUserWithEntries, opPars));
  }
  
  public static SSUri collUserSetPublic(
    final SSUri   userUri,
    final SSUri   collUri,
    final SSUri   publicCircleUri,
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,            userUri);
    opPars.put(SSVarU.collUri,         collUri);
    opPars.put(SSVarU.publicCircleUri, publicCircleUri);
    opPars.put(SSVarU.shouldCommit,    shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.collUserSetPublic, opPars));
  }
  
  public static SSColl collUserRootGet(final SSUri userUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    
    return (SSColl) SSServA.callServViaServer(new SSServPar(SSMethU.collUserRootGet, opPars));
  }
  
  public static List<SSColl> collsUserWithEntries(final SSUri userUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user, userUri);
    
    return (List<SSColl>) SSServA.callServViaServer(new SSServPar(SSMethU.collsUserWithEntries, opPars));
  }
  
  public static Boolean collUserEntryDelete(
    final SSUri        userUri, 
    final SSUri        collEntryUri, 
    final SSUri        parentCollUri, 
    final Boolean      saveUE,
    final Boolean      shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.saveUE,       saveUE);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.coll,         parentCollUri);
    opPars.put(SSVarU.collEntry,    collEntryUri);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.collUserEntryDelete, opPars));
  }
  
  public static SSUri collUserEntryAdd(
    final SSUri        user,
    final SSUri        coll,
    final SSUri        collEntry,
    final SSLabelStr   label,
    final Boolean      addNewColl,
    final Boolean      saveUE,
    final Boolean      shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,              user);
    opPars.put(SSVarU.shouldCommit,      shouldCommit);
    opPars.put(SSVarU.saveUE,            saveUE);
    opPars.put(SSVarU.coll,              coll);
    opPars.put(SSVarU.collEntry,         collEntry);
    opPars.put(SSVarU.collEntryLabel,    label);
    opPars.put(SSVarU.addNewColl,        addNewColl);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.collUserEntryAdd, opPars));
  }
  
  public static Boolean collUserEntriesAdd(
    final SSUri             userUri,
    final SSUri             collUri,
    final List<SSUri>       collEntries,
    final List<SSLabelStr>  collEntryLabels,
    final Boolean           saveUE,
    final Boolean           shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.saveUE,       saveUE);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.coll,         collUri);
    opPars.put(SSVarU.entries,      collEntries);
    opPars.put(SSVarU.entryLabels,  collEntryLabels);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.collUserEntriesAdd, opPars));
  }
  
  public static void collUserRootAdd(
    final SSUri   userUri, 
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.user,         userUri);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.collUserRootAdd, opPars));
  }
  
  public static List<SSColl> collUserHierarchyGet(
    final SSUri userUri, 
    final SSUri collUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.collUri,      collUri);
    
    return (List<SSColl>) SSServA.callServViaServer(new SSServPar(SSMethU.collUserHierarchyGet, opPars));
  }
  
  public static List<SSTagFrequ> collUserCumulatedTagsGet(
    final SSUri userUri, 
    final SSUri collUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.collUri,      collUri);
    
    return (List<SSTagFrequ>) SSServA.callServViaServer(new SSServPar(SSMethU.collUserCumulatedTagsGet, opPars));
  }
  
  public static List<SSColl> collsUserEntityIsInGet(
    final SSUri userUri,
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    entityUri);
    
    return (List<SSColl>) SSServA.callServViaServer(new SSServPar(SSMethU.collsUserEntityIsInGet, opPars));
  }
  
  public static Boolean collUserEntriesDelete(
    final SSUri       userUri,
    final SSUri       collUri,
    final List<SSUri> collEntries,
    final Boolean     shouldCommit) throws Exception{
      
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.coll,         collUri);
    opPars.put(SSVarU.collEntries,  collEntries);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.collUserEntriesDelete, opPars));
  }
  
  public static Boolean collsUserCouldSubscribeGet(
    final SSUri       userUri) throws Exception{
      
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.collsUserCouldSubscribeGet, opPars));
  }

  /* solr */

  public static List<String> solrSearch(
    final SSSolrKeywordLabel keyword,
    final Integer            maxResults) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.keyword,    keyword);
    opPars.put(SSVarU.maxResults, maxResults);
    
    return (List<String>) SSServA.callServViaServer(new SSServPar(SSMethU.solrSearch, opPars));
  }
  
  public static void solrAddDoc(
    final SSUri   userUri,
    final String  fileID,
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit,    shouldCommit);
    opPars.put(SSVarU.user,            userUri);
    opPars.put(SSVarU.id,              fileID);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.solrAddDoc, opPars));
  }
  
  /* disc */

  public static List<SSDisc> discsUserAllGet(final SSUri userUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user, userUri);
    
    return (List<SSDisc>) SSServA.callServViaServer(new SSServPar(SSMethU.discsUserAllGet, opPars));
  }
  
  public static List<SSUri> discUrisUserForTargetGet(
    final SSUri   userUri,
    final SSUri   entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    entityUri);
    
    return (List<SSUri>) SSServA.callServViaServer(new SSServPar(SSMethU.discUrisUserForTargetGet, opPars));
  }
  
  public static SSDisc discUserWithEntriesGet(
    final SSUri   userUri, 
    final SSUri   discUri, 
    final Integer maxDiscEntries) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.disc,           discUri);
    opPars.put(SSVarU.maxDiscEntries, maxDiscEntries);
    
    return (SSDisc) SSServA.callServViaServer(new SSServPar(SSMethU.discUserWithEntriesGet, opPars));
  }
  
  public static void discUserRemove(
    final SSUri   userUri,
    final SSUri   entityUri) throws Exception{
  
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    entityUri);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.discUserRemove, opPars));
  }
  
  /* rating */

  public static SSRatingOverall ratingOverallGet(
    final SSUri userUri, 
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.resource,    entityUri);
    
    return (SSRatingOverall) SSServA.callServViaServer(new SSServPar(SSMethU.ratingOverallGet, opPars));
  }
  
  public static void ratingsUserRemove(
    final SSUri   userUri,
    final SSUri   entityUri,
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    entityUri);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.ratingsUserRemove, opPars));
  }
  
  public static Integer ratingUserGet(
    final SSUri userUri,
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.resource,    entityUri);
    
    return (Integer) SSServA.callServViaServer(new SSServPar(SSMethU.ratingUserGet, opPars));
  }
  
  /* entity */

  public static SSEntity entityGet(
    final SSUri      userUri, 
    final SSUri      entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,      userUri);
    opPars.put(SSVarU.entityUri, entityUri);
    
    return (SSEntity) SSServA.callServViaServer(new SSServPar(SSMethU.entityGet, opPars));
  }
  
  public static List<SSUri> entitySearchWithTagWithin(
    final SSUri      userUri, 
    final SSUri      entityUri, 
    final SSTagLabel tag) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,      userUri);
    opPars.put(SSVarU.entityUri, entityUri);
    opPars.put(SSVarU.tag,       tag);
    
    return (List<SSUri>) SSServA.callServViaServer(new SSServPar(SSMethU.entitySearchWithTagWithin, opPars));
  }
  
  public static Long entityCreationTimeGet(final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.entityUri,    entityUri);
    
    return (Long) SSServA.callServViaServer(new SSServPar(SSMethU.entityCreationTimeGet, opPars));
  }
  
  public static SSLabelStr entityLabelGet(final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.entityUri, entityUri);
    
    return (SSLabelStr) SSServA.callServViaServer(new SSServPar(SSMethU.entityLabelGet, opPars));
  }
  
  public static SSUri entityUserDirectlyAdjoinedEntitiesRemove(
    final SSUri   userUri,
    final SSUri   entityUri           ,
    final Boolean removeUserTags      ,
    final Boolean removeUserRatings   ,
    final Boolean removeFromUserColls ,
    final Boolean removeUserLocations, 
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,                  userUri);
    opPars.put(SSVarU.entityUri,             entityUri);
    opPars.put(SSVarU.removeUserTags,        removeUserTags);
    opPars.put(SSVarU.removeUserRatings,     removeUserRatings);
    opPars.put(SSVarU.removeFromUserColls,   removeFromUserColls);
    opPars.put(SSVarU.removeUserLocations,   removeUserLocations);
    opPars.put(SSVarU.shouldCommit,          shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserDirectlyAdjoinedEntitiesRemove, opPars));
  }
  
  public static SSUri entityAuthorGet(
    final SSUri userUri,
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.entityUri,   entityUri);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityAuthorGet, opPars));
  }
  
  public static SSEntityEnum entityTypeGet(final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.entityUri, entityUri);
    
    return (SSEntityEnum) SSServA.callServViaServer(new SSServPar(SSMethU.entityTypeGet, opPars));
  }
  
  public static SSEntityDescA entityDescGet(
    final SSUri   userUri, 
    final SSUri   entityUri,
    final Boolean getTags,
    final Boolean getOverallRating,
    final Boolean getDiscUris) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,             userUri);
    opPars.put(SSVarU.entityUri,        entityUri);
    opPars.put(SSVarU.getTags,          getTags);
    opPars.put(SSVarU.getOverallRating, getOverallRating);
    opPars.put(SSVarU.getDiscUris,      getDiscUris);
    
    return (SSEntityDescA) SSServA.callServViaServer(new SSServPar(SSMethU.entityDescGet, opPars));
  }
  
  public static void entityRemove(
    final SSUri   entityUri, 
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.entityUri,    entityUri);
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.entityRemove, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static List<SSCircle> entityUserEntityCirclesGet(
    final SSUri userUri,
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.entityUri,   entityUri);
    
    return (List<SSCircle>) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserEntityCirclesGet, opPars));
  }
  
  public static Boolean entityUserAllowedIs(
    final SSUri                    userUri,
    final SSUri                    entityUri, 
    final SSEntityRightTypeE       accessRight) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.entityUri,   entityUri);
    opPars.put(SSVarU.accessRight, accessRight);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserAllowedIs, opPars));
  }
  
  public static Boolean entityUserCanEdit(
    final SSUri userUri, 
    final SSUri entityUri) throws Exception{
    
    return entityUserAllowedIs(userUri, entityUri, SSEntityRightTypeE.edit);
  }
  
  public static Boolean entityUserCanRead(
    final SSUri userUri, 
    final SSUri entityUri) throws Exception{
    
    return entityUserAllowedIs(userUri, entityUri, SSEntityRightTypeE.read);
  }
  
  public static Boolean entityUserCanAll(
    final SSUri userUri, 
    final SSUri entityUri) throws Exception{
    
    return entityUserAllowedIs(userUri, entityUri, SSEntityRightTypeE.all);
  }
  
  public static SSUri entityCirclePublicAdd(
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityCirclePublicAdd,  opPars));
  }
  
  public static List<SSCircle> entityUserCirclesGet(
    final SSUri   userUri,
    final Boolean withSystemGeneratedCircles) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,                       userUri);
    opPars.put(SSVarU.withSystemGeneratedCircles, withSystemGeneratedCircles);
    
    return (List<SSCircle>) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserCirclesGet, opPars));
  }
  
  public static SSUri entityCircleCreate(
    final SSUri                     userUri,
    final SSUri                     toAddEntityUri,
    final List<SSUri>               toAddUserUris, 
    final SSEntityCircleTypeE       circleType, 
    final SSLabelStr                label, 
    final SSUri                     circleAuthor,
    final Boolean                   shouldCommit) throws Exception{
    
    final Map<String, Object>       opPars     = new HashMap<String, Object>();
    final List<SSUri>               entityUris = new ArrayList<SSUri>();
    
    if(toAddEntityUri != null){
      entityUris.add (toAddEntityUri);
    }
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUris,     entityUris);
    opPars.put(SSVarU.userUris,       toAddUserUris);
    opPars.put(SSVarU.circleType,     circleType);
    opPars.put(SSVarU.label,          label);
    opPars.put(SSVarU.circleAuthor,   circleAuthor);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityCircleCreate, opPars));
  }
  
  public static SSUri entityCircleCreate(
    final SSUri                     userUri,
    final SSUri                     toAddEntityUri,
    final SSUri                     toAddUserUri, 
    final SSEntityCircleTypeE       circleType, 
    final SSLabelStr                label,
    final SSUri                     circleAuthor,
    final Boolean                   shouldCommit) throws Exception{
    
    final Map<String, Object>       opPars     = new HashMap<String, Object>();
    final List<SSUri>               entityUris = new ArrayList<SSUri>();
    final List<SSUri>               userUris   = new ArrayList<SSUri>();
    
    if(toAddEntityUri != null){
      entityUris.add (toAddEntityUri);
    }
    
    if(toAddUserUri != null){
      userUris.add   (toAddUserUri);
    }
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUris,     entityUris);
    opPars.put(SSVarU.userUris,       userUris);
    opPars.put(SSVarU.circleType,     circleType);
    opPars.put(SSVarU.label,          label);
    opPars.put(SSVarU.circleAuthor,   circleAuthor);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityCircleCreate, opPars));
  }
  
  public static SSUri entityCircleCreate(
    final SSUri                     userUri,
    final List<SSUri>               entityUris,
    final List<SSUri>               userUris, 
    final SSEntityCircleTypeE       circleType, 
    final SSLabelStr                label,
    final SSUri                     circleAuthor,
    final Boolean                   shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUris,     entityUris);
    opPars.put(SSVarU.userUris,       userUris);
    opPars.put(SSVarU.circleType,     circleType);
    opPars.put(SSVarU.label,          label);
    opPars.put(SSVarU.circleAuthor,   circleAuthor);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityCircleCreate, opPars));
  }
  
  public static SSUri entityEntitiesToCircleAdd(
    final SSUri       userUri,
    final SSUri       circleUri, 
    final List<SSUri> entityUris,
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUris,     entityUris);
    opPars.put(SSVarU.circleUri,      circleUri);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityEntitiesToCircleAdd, opPars));
  }
  
  public static SSUri entityEntitiesToCircleAdd(
    final SSUri       userUri,
    final SSUri       circleUri, 
    final SSUri       entityUri,
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    final List<SSUri> entityUris     = new ArrayList<SSUri>();
    
    if(entityUri != null){
      entityUris.add(entityUri);
    }
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUris,     entityUris);
    opPars.put(SSVarU.circleUri,      circleUri);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityEntitiesToCircleAdd, opPars));
  }
  
  public static SSUri entityUserEntitiesToCircleAdd(
    final SSUri       userUri,
    final SSUri       circleUri, 
    final SSUri       entityUri,
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    final List<SSUri> entityUris     = new ArrayList<SSUri>();
    
    if(entityUri != null){
      entityUris.add(entityUri);
    }
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUris,     entityUris);
    opPars.put(SSVarU.circleUri,      circleUri);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserEntitiesToCircleAdd, opPars));
  }
  
  public static SSUri entityUsersToCircleAdd(
    final SSUri       userUri,
    final SSUri       circleUri,
    final SSUri       userUriToAdd, 
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars   = new HashMap<String, Object>();
    final List<SSUri>         userUris = new ArrayList<SSUri>();
    
    if(userUriToAdd != null){
      userUris.add(userUriToAdd);
    }
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.userUris,       userUris);
    opPars.put(SSVarU.circleUri,      circleUri);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);    
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityUsersToCircleAdd, opPars));
  }
  
  public static SSUri entityUsersToCircleAdd(
    final SSUri       userUri,
    final SSUri       circleUri,
    final List<SSUri> userUrisToAdd,
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars   = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.userUris,       userUrisToAdd);
    opPars.put(SSVarU.circleUri,      circleUri);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);    
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityUsersToCircleAdd, opPars));
  }
  
  public static SSUri entityUserUsersToCircleAdd(
    final SSUri       userUri,
    final SSUri       circleUri,
    final List<SSUri> userUris, 
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.userUris,       userUris);
    opPars.put(SSVarU.circleUri,      circleUri);
    opPars.put(SSVarU.shouldCommit,   shouldCommit);    
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserUsersToCircleAdd, opPars));
  }
  
  public static SSUri entityCircleURIPublicGet() throws Exception{
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityCircleURIPublicGet, new HashMap<String, Object>()));
  }
  
  public static List<SSEntityCircleTypeE> entityUserEntityCircleTypesGet(
    final SSUri userUri, 
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.entityUri,      entityUri);
    
    return (List<SSEntityCircleTypeE>) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserEntityCircleTypesGet, opPars));
  }

  public static SSEntityCircleTypeE entityMostOpenCircleTypeGet(
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.entityUri,      entityUri);
    
    return (SSEntityCircleTypeE) SSServA.callServViaServer(new SSServPar(SSMethU.entityMostOpenCircleTypeGet, opPars));
  }
    
  public static Boolean entityUserCircleDelete(
    final SSUri entityUri,
    final SSUri circleUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.entityUri, entityUri);
    opPars.put(SSVarU.circleUri, circleUri);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserCircleDelete, opPars));
  }

  public static SSUri entityUserPublicSet(
    final SSUri   userUri,
    final SSUri   entityUri,
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.entityUri,    entityUri);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.entityUserPublicSet, opPars));
  }
  
  /* user event */
  
  public static void ueAdd(
    final SSUri     userUri, 
    final SSUri     entityUri, 
    final SSUEEnum  evenType, 
    final String    content, 
    final Boolean   shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.resource,     entityUri);
    opPars.put(SSVarU.eventType,    evenType);
    opPars.put(SSVarU.content,      content);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.uEAdd, opPars));
  }
  
  /* user */
  public static Boolean userExists(
    final SSUri userUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.userExists, opPars));
  }
  
  /* modeling user event */

  public static List<SSUri> modelUEEntitiesForMiGet(
    final SSUri            userUri, 
    final SSModelUEMILabel mi) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user, userUri);
    opPars.put(SSVarU.mi,   mi);
    
    return (List<SSUri>) SSServA.callServViaServer(new SSServPar(SSMethU.modelUEEntitiesForMiGet, opPars));
  }
  
  public static void modelUEUpdate() throws Exception{
    SSServA.callServViaServer(new SSServPar(SSMethU.modelUEUpdate, new HashMap<String, Object>()));
  }
  
  public static List<String> modelUEMIsForEntityGet(
    final SSUri userUri,
    final SSUri entityUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.entityUri,   entityUri);
    
    return (List<String>) SSServA.callServViaServer(new SSServPar(SSMethU.modelUEMIsForEntityGet, opPars));
  }
  
  /* data export **/
  public static void dataExportUserEntityTags(
    final SSUri                     user,
    final Map<String, List<String>> tagsPerEntities,
    final String                    fileName,
    final Boolean                   wasLastLine) throws Exception {
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,              fileName);
    opPars.put(SSVarU.tagsPerEntities,       tagsPerEntities);
    opPars.put(SSVarU.user,                  user);
    opPars.put(SSVarU.wasLastLine,           wasLastLine);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.dataExportUserEntityTags, opPars));
  }
  
  public static void dataExportUserEntityTagTimestamps(
    final SSUri                     user,
    final Map<String, List<String>> tagsPerEntities,
    final Long                      timestampForTag,
    final String                    fileName,
    final Boolean                   wasLastLine) throws Exception {
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,              fileName);
    opPars.put(SSVarU.tagsPerEntities,       tagsPerEntities);
    opPars.put(SSVarU.user,                  user);
    opPars.put(SSVarU.timestamp,             timestampForTag);
    opPars.put(SSVarU.wasLastLine,           wasLastLine);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.dataExportUserEntityTagTimestamps, opPars));
  }
  
  public static void dataExportUserEntityTagCategories(
    final SSUri                     user,
    final Map<String, List<String>> tagsPerEntities,
    final Map<String, List<String>> categoriesPerEntities,
    final String                    fileName,
    final Boolean                   wasLastLine) throws Exception {
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,              fileName);
    opPars.put(SSVarU.tagsPerEntities,       tagsPerEntities);
    opPars.put(SSVarU.categoriesPerEntities, categoriesPerEntities);
    opPars.put(SSVarU.user,                  user);
    opPars.put(SSVarU.wasLastLine,           wasLastLine);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.dataExportUserEntityTagCategories, opPars));
  }
  
  public static void dataExportUserEntityTagCategoryTimestamps(
    final SSUri                     user,
    final Map<String, List<String>> tagsPerEntities,
    final Map<String, List<String>> categoriesPerEntities,
    final Long                      timestampForTag,
    final String                    fileName,
    final Boolean                   wasLastLine) throws Exception {
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,              fileName);
    opPars.put(SSVarU.tagsPerEntities,       tagsPerEntities);
    opPars.put(SSVarU.categoriesPerEntities, categoriesPerEntities);
    opPars.put(SSVarU.user,                  user);
    opPars.put(SSVarU.timestamp,             timestampForTag);
    opPars.put(SSVarU.wasLastLine,           wasLastLine);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.dataExportUserEntityTagCategoryTimestamps, opPars));
  }
  
  /*  tag  */
 
  public static void tagsAdd(final SSUri userUri, 
    final SSUri            entityUri, 
    final List<SSTagLabel> tagList, 
    final SSSpaceEnum      space, 
    final Boolean          shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      opPars.put(SSVarU.user,         userUri);
      opPars.put(SSVarU.resource,     entityUri);
      opPars.put(SSVarU.tagStrings,   tagList);
      opPars.put(SSVarU.space,        space);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.tagsAdd, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static List<SSTag> tagsUserGet(
    final SSUri       userUri, 
    final SSUri       entityUri, 
    final SSTagLabel  tagString, 
    final SSSpaceEnum space) throws Exception{
    
    final Map<String, Object>   opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,      userUri);
    opPars.put(SSVarU.resource,  entityUri);
    opPars.put(SSVarU.tagString, tagString);
    opPars.put(SSVarU.space,     space);
    
    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.tagsUserGet, opPars));
  }
  
  public static List<SSTagFrequ> tagUserFrequsGet(
    final SSUri       userUri, 
    final SSUri       entityUri, 
    final SSTagLabel tagLabel, 
    final SSSpaceEnum space) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.resource,    entityUri);
    opPars.put(SSVarU.tagString,   tagLabel);
    opPars.put(SSVarU.space,       space);
    
    return (List<SSTagFrequ>) SSServA.callServViaServer(new SSServPar(SSMethU.tagUserFrequsGet, opPars));
  }
  
  public static List<SSUri> tagUserEntitiesForTagGet(
    final SSUri       userUri,
    final SSTagLabel tagLabel,
    final SSSpaceEnum space) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,       userUri);
    opPars.put(SSVarU.tagString,  tagLabel);
    opPars.put(SSVarU.space,      space);
    
    return (List<SSUri>) SSServA.callServViaServer(new SSServPar(SSMethU.tagUserEntitiesForTagGet, opPars));
  }
  
  public static void tagsRemove(
    final SSUri         forUserUri,   
    final SSUri         entityUri,
    final SSTagLabel    tagLabel,
    final SSSpaceEnum   space,
    final Boolean       shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.forUser,      forUserUri);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.entityUri,    entityUri);
    opPars.put(SSVarU.tagLabel,     tagLabel);
    opPars.put(SSVarU.space,        space);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.tagsRemove, opPars));
  }
  
  public static void tagsUserRemove(
    final SSUri         userUri,
    final SSUri         entityUri,
    final SSTagLabel   tagString,
    final SSSpaceEnum   space,
    final Boolean       shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.resource,     entityUri);
    opPars.put(SSVarU.tagString,    tagString);
    opPars.put(SSVarU.space,        space);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.tagsUserRemove, opPars));
  }
  
  public static void tagAdd(
    final SSUri       userUri,
    final SSUri       entityUri,
    final SSTagLabel  tagString,
    final SSSpaceEnum space,
    final Boolean     shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.resource,     entityUri);
    opPars.put(SSVarU.space,        space);
    opPars.put(SSVarU.tagString,    tagString);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.tagAdd, opPars));
  }
  
  public static void tagAddAtCreationTime(
    final SSUri        userUri,
    final SSUri        entityUri,
    final SSTagLabel   tagString,
    final SSSpaceEnum  space,
    final Long         creationTime,
    final Boolean      shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.resource,     entityUri);
    opPars.put(SSVarU.tagString,    tagString);
    opPars.put(SSVarU.space,        space);
    opPars.put(SSVarU.creationTime, creationTime);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.tagAddAtCreationTime, opPars));
  }
  
  /* location */
  
  public static void locationsUserRemove(
    final SSUri        userUri,
    final SSUri        entityUri,
    final Boolean      shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    try{
      opPars.put(SSVarU.shouldCommit, shouldCommit);
      opPars.put(SSVarU.user,         userUri);
      opPars.put(SSVarU.entityUri,    entityUri);
      
      SSServA.callServViaServer(new SSServPar(SSMethU.locationsUserRemove, opPars));
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  /* recommendation */
  
   public static List<SSTag> recommTagsBaseLevelLearningWithContextBasedOnUserEntityTagTimestamp(
    final SSUri         userUri, 
    final SSUri         forUser, 
    final SSUri         entityUri, 
    final Integer       maxTags) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.forUser,        forUser);
    opPars.put(SSVarU.entityUri,      entityUri);
    opPars.put(SSVarU.maxTags,        maxTags);
    
    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsBaseLevelLearningWithContextBasedOnUserEntityTagTimestamp, opPars));
  }
  
  public static void recommTagsBaseLevelLearningWithContextBasedOnUserEntityTagTimestampUpdate() throws Exception {
    SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsBaseLevelLearningWithContextBasedOnUserEntityTagTimestampUpdate, new HashMap<String, Object>()));
  }
  
  public static List<SSTag> recommTagsLanguageModelBasedOnUserEntityTag(
    final SSUri         userUri, 
    final SSUri         forUser, 
    final SSUri         entityUri, 
    final Integer       maxTags) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.forUser,        forUser);
    opPars.put(SSVarU.entityUri,      entityUri);
    opPars.put(SSVarU.maxTags,        maxTags);
    
    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsLanguageModelBasedOnUserEntityTag, opPars));
  }
  
  public static void recommTagsLanguageModelBasedOnUserEntityTagUpdate() throws Exception {
    SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsLanguageModelBasedOnUserEntityTagUpdate, new HashMap<String, Object>()));
  }
  
  public static List<SSTag> recommTagsThreeLayersBasedOnUserEntityTagCategory(
    final SSUri         userUri, 
    final SSUri         forUser, 
    final SSUri         entityUri,
    final List<String>  categories,
    final Integer       maxTags) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.forUser,        forUser);
    opPars.put(SSVarU.entityUri,      entityUri);
    opPars.put(SSVarU.categories,     categories);
    opPars.put(SSVarU.maxTags,        maxTags);
    
    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsThreeLayersBasedOnUserEntityTagCategory, opPars));
  }
  
  public static void recommTagsThreeLayersBasedOnUserEntityTagCategoryUpdate() throws Exception {
    SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsThreeLayersBasedOnUserEntityTagCategoryUpdate, new HashMap<String, Object>()));
  }
  
  
  public static List<SSTag> recommTagsThreeLayersBasedOnUserEntityTagCategoryTimestamp(
    final SSUri         userUri, 
    final SSUri         forUser, 
    final SSUri         entityUri, 
    final List<String>  categories,
    final Integer       maxTags) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,           userUri);
    opPars.put(SSVarU.forUser,        forUser);
    opPars.put(SSVarU.entityUri,      entityUri);
    opPars.put(SSVarU.categories,     categories);
    opPars.put(SSVarU.maxTags,        maxTags);
    
    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsThreeLayersBasedOnUserEntityTagCategoryTimestamp, opPars));
  }
  
  public static void recommTagsThreeLayersBasedOnUserEntityTagCategoryTimestampUpdate() throws Exception {
    SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsThreeLayersBasedOnUserEntityTagCategoryTimestampUpdate, new HashMap<String, Object>()));
  }
  
  /* file */
  
  public static SSFileCanWriteRet fileCanWrite(
    final SSUri userUri,
    final SSUri fileUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user, userUri);
    opPars.put(SSVarU.uri,  fileUri);
    
    return (SSFileCanWriteRet) SSServA.callServViaServer(new SSServPar(SSMethU.fileCanWrite, opPars));
  }
  
  public static void fileUpdateWritingMinutes() throws Exception {
    SSServA.callServViaServer(new SSServPar(SSMethU.fileUpdateWritingMinutes, new HashMap<String, Object>()));
  }
  
  public static SSUri fileCreateUri(
    final SSUri  userUri,
    final String fileExt) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,          userUri);
    opPars.put(SSVarU.fileExtension, fileExt);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.fileCreateUri, opPars));
  }
  
  public static String fileIDFromURI(SSUri userUri, SSUri fileUri) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,    userUri);
    opPars.put(SSVarU.fileUri, fileUri);
    
    return (String) SSServA.callServViaServer(new SSServPar(SSMethU.fileIDFromURI, opPars));
  }
  
  public static void fileRemoveReaderOrWriter(
    final SSUri   userUri,
    final SSUri   fileUri,
    final Boolean write,
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit,  shouldCommit);
    opPars.put(SSVarU.user,          userUri);
    opPars.put(SSVarU.uri,           fileUri);
    opPars.put(SSVarU.write,         write);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.fileRemoveReaderOrWriter, opPars));
  }
  
  public static SSUri fileUriFromID(SSUri userUri, String fileID) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user, userUri);
    opPars.put(SSVarU.id,   fileID);
    
    return (SSUri) SSServA.callServViaServer(new SSServPar(SSMethU.fileUriFromID, opPars));
  }
  
  public static void dataImportMediaWikiUser(
    final SSUri   userUri,
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.shouldCommit, shouldCommit);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.dataImportMediaWikiUser, opPars));
  }
  
  /* scaff */
  
  public static List<String> scaffRecommTagsBasedOnUserEntityTag(
    final SSUri         userUri,
    final SSUri         forUserUri,
    final SSUri         entityUri,
    final Integer       maxTags) throws  Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,         userUri);
    opPars.put(SSVarU.forUserUri,   forUserUri);
    opPars.put(SSVarU.entityUri,    entityUri);
    opPars.put(SSVarU.maxTags,      maxTags);
    
    return (List<String>) SSServA.callServViaServer(new SSServPar(SSMethU.scaffRecommTagsBasedOnUserEntityTag, opPars));
  }
  
  public static List<String> scaffRecommTagsBasedOnUserEntityTagTime(
    final SSUri         userUri,
    final SSUri         forUserUri,
    final SSUri         entityUri,
    final Integer       maxTags) throws  Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,        userUri);
    opPars.put(SSVarU.forUserUri,  forUserUri);
    opPars.put(SSVarU.entityUri,   entityUri);
    opPars.put(SSVarU.maxTags,     maxTags);
    
    return (List<String>) SSServA.callServViaServer(new SSServPar(SSMethU.scaffRecommTagsBasedOnUserEntityTagTime, opPars));
  }
  
  public static List<String> scaffRecommTagsBasedOnUserEntityTagCategory(
    final SSUri         userUri,
    final SSUri         forUserUri,
    final SSUri         entityUri,
    final List<String>  categories,
    final Integer       maxTags) throws  Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,       userUri);
    opPars.put(SSVarU.forUserUri, forUserUri);
    opPars.put(SSVarU.entityUri,  entityUri);
    opPars.put(SSVarU.categories, categories);
    opPars.put(SSVarU.maxTags,    maxTags);
    
    return (List<String>) SSServA.callServViaServer(new SSServPar(SSMethU.scaffRecommTagsBasedOnUserEntityTagCategory, opPars));
  }
  
  public static List<String> scaffRecommTagsBasedOnUserEntityTagCategoryTime(
    final SSUri         userUri,
    final SSUri         forUserUri,
    final SSUri         entityUri,
    final List<String>  categories,
    final Integer       maxTags) throws  Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.user,       userUri);
    opPars.put(SSVarU.forUserUri, forUserUri);
    opPars.put(SSVarU.entityUri,  entityUri);
    opPars.put(SSVarU.categories, categories);
    opPars.put(SSVarU.maxTags,    maxTags);
    
    return (List<String>) SSServA.callServViaServer(new SSServPar(SSMethU.scaffRecommTagsBasedOnUserEntityTagCategoryTime, opPars));
  }
  
  /* data import */
  
  public static void dataImportUserResourceTagFromWikipedia(
    final SSUri   userUri, 
    final Boolean shouldCommit) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.shouldCommit,     shouldCommit);
    opPars.put(SSVarU.user,             userUri);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.dataImportUserResourceTagFromWikipedia, opPars));
  }
  
  public static Map<String, String> dataImportSSSUsersFromCSVFile( 
    final String fileName) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,     fileName);
    
    return (Map<String, String>) SSServA.callServViaServer(new SSServPar(SSMethU.dataImportSSSUsersFromCSVFile, opPars));
  }
  
  /* auth */
  
  public static void authUsersFromCSVFileAdd() throws Exception{
    SSServA.callServViaServer(new SSServPar(SSMethU.authUsersFromCSVFileAdd, new HashMap<String, Object>()));
  }
    
  public static void checkKey(final SSServPar par) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.key, par.key);
    
    SSServA.callServViaServer(new SSServPar(SSMethU.authCheckKey, opPars));
  }
  
  /* i5Cloud */
  
  public static Map<String, String> i5CloudAuth() throws Exception{
    return (Map<String, String>) SSServA.callServViaServer(new SSServPar(SSMethU.i5CloudAuth, new HashMap<String, Object>()));
  }
  
  public static Boolean i5CloudFileUpload(
    final String fileName,
    final String containerSpace,
    final String xAuthToken) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,   fileName);
    opPars.put(SSVarU.space,      containerSpace);
    opPars.put(SSVarU.xAuthToken, xAuthToken);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.i5CloudFileUpload, opPars));
  }
  
  public static Boolean i5CloudFileDownload(
    final String fileName,
    final String containerSpace,
    final String xAuthToken) throws Exception{
    
    final Map<String, Object> opPars = new HashMap<String, Object>();
    
    opPars.put(SSVarU.fileName,   fileName);
    opPars.put(SSVarU.space,      containerSpace);
    opPars.put(SSVarU.xAuthToken, xAuthToken);
    
    return (Boolean) SSServA.callServViaServer(new SSServPar(SSMethU.i5CloudFileDownload, opPars));
  } 
}



//public static List<SSTag> recommTagsFolkRank(
//    final SSUri         userUri, 
//    final SSUri         forUser, 
//    final SSUri         entityUri, 
//    final Integer       maxTags) throws Exception{
//    
//    final Map<String, Object> opPars = new HashMap<String, Object>();
//    
//    opPars.put(SSVarU.user,           userUri);
//    opPars.put(SSVarU.forUser,        forUser);
//    opPars.put(SSVarU.entityUri,      entityUri);
//    opPars.put(SSVarU.maxTags,        maxTags);
//    
//    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsFolkRank, opPars));
//  }
//  
//  public static List<SSTag> recommTagsLDA(
//    final SSUri         userUri, 
//    final SSUri         forUser, 
//    final SSUri         entityUri, 
//    final Integer       maxTags) throws Exception{
//    
//    final Map<String, Object> opPars = new HashMap<String, Object>();
//    
//    opPars.put(SSVarU.user,           userUri);
//    opPars.put(SSVarU.forUser,        forUser);
//    opPars.put(SSVarU.entityUri,      entityUri);
//    opPars.put(SSVarU.maxTags,        maxTags);
//    
//    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsLDA, opPars));
//  }
//    
//  public static List<SSTag> recommTagsCollaborativeFilteringOnUserSimilarity(
//    final SSUri         userUri, 
//    final SSUri         forUser, 
//    final SSUri         entityUri, 
//    final Integer       maxTags) throws Exception{
//    
//    final Map<String, Object> opPars = new HashMap<String, Object>();
//    
//    opPars.put(SSVarU.user,           userUri);
//    opPars.put(SSVarU.forUser,        forUser);
//    opPars.put(SSVarU.entityUri,      entityUri);
//    opPars.put(SSVarU.maxTags,        maxTags);
//    
//    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsCollaborativeFilteringOnUserSimilarity, opPars));
//  }
//  
//  public static List<SSTag> recommTagsCollaborativeFilteringOnEntitySimilarity(
//    final SSUri         userUri, 
//    final SSUri         forUser, 
//    final SSUri         entityUri, 
//    final Integer       maxTags) throws Exception{
//    
//    final Map<String, Object> opPars = new HashMap<String, Object>();
//    
//    opPars.put(SSVarU.user,           userUri);
//    opPars.put(SSVarU.forUser,        forUser);
//    opPars.put(SSVarU.entityUri,      entityUri);
//    opPars.put(SSVarU.maxTags,        maxTags);
//    
//    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsCollaborativeFilteringOnEntitySimilarity, opPars));
//  }
//  
//  public static List<SSTag> recommTagsAdaptedPageRank(
//    final SSUri         userUri, 
//    final SSUri         forUser, 
//    final SSUri         entityUri, 
//    final Integer       maxTags) throws Exception{
//    
//    final Map<String, Object> opPars = new HashMap<String, Object>();
//    
//    opPars.put(SSVarU.user,           userUri);
//    opPars.put(SSVarU.forUser,        forUser);
//    opPars.put(SSVarU.entityUri,      entityUri);
//    opPars.put(SSVarU.maxTags,        maxTags);
//    
//    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsAdaptedPageRank, opPars));
//  }

//public static List<SSTag> recommTagsTemporalUsagePatterns(
//    final SSUri         userUri, 
//    final SSUri         forUser, 
//    final SSUri         entityUri, 
//    final Integer       maxTags) throws Exception{
//    
//    final Map<String, Object> opPars = new HashMap<String, Object>();
//    
//    opPars.put(SSVarU.user,           userUri);
//    opPars.put(SSVarU.forUser,        forUser);
//    opPars.put(SSVarU.entityUri,      entityUri);
//    opPars.put(SSVarU.maxTags,        maxTags);
//    
//    return (List<SSTag>) SSServA.callServViaServer(new SSServPar(SSMethU.recommTagsTemporalUsagePatterns, opPars));
//  }