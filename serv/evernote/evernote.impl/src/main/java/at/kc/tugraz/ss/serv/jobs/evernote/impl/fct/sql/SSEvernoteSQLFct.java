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
package at.kc.tugraz.ss.serv.jobs.evernote.impl.fct.sql;

import at.kc.tugraz.socialserver.utils.SSSQLVarU;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.serv.db.api.SSDBSQLFct;
import at.kc.tugraz.ss.serv.db.api.SSDBSQLI;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteNote;
import at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par.SSEvernoteResource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSEvernoteSQLFct extends SSDBSQLFct {
  
  public SSEvernoteSQLFct(final SSDBSQLI dbSQL) throws Exception{
    super(dbSQL);
  }
  
  public void addUserIfNotExists(
    final SSUri  user,
    final String authToken) throws Exception{
    
    try{
      final Map<String, String> inserts    =  new HashMap<>();
      final Map<String, String> uniqueKeys =  new HashMap<>();
      
      insert(inserts, SSSQLVarU.userId,      user);
      insert(inserts, SSSQLVarU.authToken,   authToken);
      insert(inserts, SSSQLVarU.usn,         0);
      
      uniqueKey(uniqueKeys, SSSQLVarU.userId, user);
      
      dbSQL.insertIfNotExists(evernoteUserTable, inserts, uniqueKeys);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public void setUSN(
    final String  authToken,
    final Integer usn) throws Exception{
    
    try{
      final Map<String, String> updates    =  new HashMap<>();
      final Map<String, String> wheres     =  new HashMap<>();
      
      where  (wheres,  SSSQLVarU.authToken, authToken);
      update (updates, SSSQLVarU.usn,       usn);
      
      dbSQL.update(evernoteUserTable, wheres, updates);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
    
  public String getAuthToken(
    final SSUri user) throws Exception{
    
    ResultSet resultSet = null;
    
    try{
      final List<String>        columns = new ArrayList<>();
      final Map<String, String> wheres  = new HashMap<>();
      
      column(columns, SSSQLVarU.userId);
      column(columns, SSSQLVarU.authToken);
      
      where(wheres, SSSQLVarU.userId, user);
      
      resultSet = dbSQL.select(evernoteUserTable, columns, wheres, null, null, null);
      
      checkFirstResult(resultSet);
        
      return bindingStr(resultSet, SSSQLVarU.authToken);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      dbSQL.closeStmt(resultSet);
    }
  }
  
  public void addNoteIfNotExists(
    final SSUri notebookUri,
    final SSUri noteUri) throws Exception{
    
    try{
      final Map<String, String> inserts    =  new HashMap<>();
      final Map<String, String> uniqueKeys =  new HashMap<>();
      
      insert(inserts, SSSQLVarU.noteId,      noteUri);
      insert(inserts, SSSQLVarU.notebookId,  notebookUri);
      
      uniqueKey(uniqueKeys, SSSQLVarU.noteId, noteUri);
      
      dbSQL.insertIfNotExists(evernoteNoteTable, inserts, uniqueKeys);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }

  public void addResourceIfNotExists(
    final SSUri noteUri,
    final SSUri resourceUri) throws Exception{
    
    try{
      final Map<String, String> inserts    =  new HashMap<>();
      final Map<String, String> uniqueKeys =  new HashMap<>();
      
      insert(inserts, SSSQLVarU.entityId,    resourceUri);
      insert(inserts, SSSQLVarU.noteId,      noteUri);
      
      uniqueKey(uniqueKeys, SSSQLVarU.entityId, resourceUri);
      
      dbSQL.insertIfNotExists(evernoteResourceTable, inserts, uniqueKeys);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }

  public SSEvernoteResource getResource(
    final SSUri resourceId) throws Exception{
    
    ResultSet resultSet = null;
    
    try{
      final List<String>        columns = new ArrayList<>();
      final Map<String, String> wheres  = new HashMap<>();
      
      column(columns, SSSQLVarU.noteId);
      column(columns, SSSQLVarU.entityId);
      
      where(wheres, SSSQLVarU.entityId, resourceId);
      
      resultSet = dbSQL.select(evernoteResourceTable, columns, wheres, null, null, null);
      
      checkFirstResult(resultSet);
        
      return SSEvernoteResource.get(
        resourceId, 
        bindingStrToUri(resultSet, SSSQLVarU.noteId), 
        null, 
        null);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      dbSQL.closeStmt(resultSet);
    }
  }

  public Integer getUSN(final String authToken) throws Exception{
    
    ResultSet resultSet = null;
    
    try{
      final List<String>        columns = new ArrayList<>();
      final Map<String, String> wheres = new HashMap<>();
      
      column(columns, SSSQLVarU.authToken);
      column(columns, SSSQLVarU.usn);
      
      where(wheres, SSSQLVarU.authToken, authToken);
      
      resultSet = dbSQL.select(evernoteUserTable, columns, wheres, null, null, null);
      
      try{
        
        checkFirstResult(resultSet);
        
        return bindingStrToInteger(resultSet, SSSQLVarU.usn);
      }catch(Exception error){
        SSServErrReg.reset();
        return 0;
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      dbSQL.closeStmt(resultSet);
    }
  }
  
  public SSEvernoteNote getNote(
    final SSUri noteUri) throws Exception{
    
    ResultSet resultSet = null;
    
    try{
      final List<String>        columns = new ArrayList<>();
      final Map<String, String> wheres  = new HashMap<>();
      
      column(columns, SSSQLVarU.noteId);
      column(columns, SSSQLVarU.notebookId);
      
      where(wheres, SSSQLVarU.noteId, noteUri);
      
      resultSet = dbSQL.select(evernoteNoteTable, columns, wheres, null, null, null);
      
      checkFirstResult(resultSet);
      
      return SSEvernoteNote.get(
        noteUri,
        bindingStrToUri(resultSet, SSSQLVarU.notebookId));
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      dbSQL.closeStmt(resultSet);
    }
  }
}