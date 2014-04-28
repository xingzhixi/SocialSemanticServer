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
package at.kc.tugraz.ss.service.user.impl;

import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import at.kc.tugraz.ss.adapter.socket.datatypes.SSSocketCon;
import at.kc.tugraz.ss.serv.serv.api.SSServConfA;
import at.kc.tugraz.ss.serv.db.api.SSDBGraphI;
import at.kc.tugraz.ss.serv.db.api.SSDBSQLI;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSLabelStr;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityDescA;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSEntityDesc;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.par.SSEntityUserDirectlyAdjoinedEntitiesRemovePar;
import at.kc.tugraz.ss.serv.db.datatypes.sql.err.SSSQLDeadLockErr;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.serv.serv.api.SSEntityHandlerImplI;
import at.kc.tugraz.ss.serv.serv.api.SSServImplWithDBA;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import at.kc.tugraz.ss.service.rating.datatypes.SSRatingOverall;
import at.kc.tugraz.ss.service.tag.datatypes.SSTag;
import at.kc.tugraz.ss.service.user.api.*;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSUser;
import at.kc.tugraz.ss.service.user.datatypes.SSUserDesc;
import at.kc.tugraz.ss.service.user.datatypes.pars.SSUserAddPar;
import at.kc.tugraz.ss.service.user.datatypes.pars.SSUserExistsPar;
import at.kc.tugraz.ss.service.user.datatypes.pars.SSUserLoginPar;
import at.kc.tugraz.ss.service.user.datatypes.pars.SSUsersGetPar;
import at.kc.tugraz.ss.service.user.datatypes.ret.SSUserAllRet;
import at.kc.tugraz.ss.service.user.datatypes.ret.SSUserLoginRet;
import at.kc.tugraz.ss.service.user.impl.functions.sql.SSUserSQLFct;
import java.util.*;

public class SSUserImpl extends SSServImplWithDBA implements SSUserClientI, SSUserServerI, SSEntityHandlerImplI{
  
//  private final SSUserGraphFct       graphFct;
  private final SSUserSQLFct         sqlFct;
  
  public SSUserImpl(final SSServConfA conf, final SSDBGraphI dbGraph, final SSDBSQLI dbSQL) throws Exception{
    
    super(conf, dbGraph, dbSQL);
    
//    graphFct = new SSUserGraphFct (this);
    sqlFct   = new SSUserSQLFct   (this);
  }
  
  /* SSEntityHandlerImplI */
  
  @Override
  public void removeDirectlyAdjoinedEntitiesForUser(
    final SSEntityEnum                                  entityType,
    final SSEntityUserDirectlyAdjoinedEntitiesRemovePar par) throws Exception{
  }
  
  @Override
  public Boolean setUserEntityPublic(
    final SSUri          userUri,
    final SSUri          entityUri, 
    final SSEntityEnum   entityType,
    final SSUri          publicCircleUri) throws Exception{

    return false;
  }
  
  @Override
  public Boolean shareUserEntity(
    final SSUri          userUri, 
    final List<SSUri>    userUrisToShareWith,
    final SSUri          entityUri, 
    final SSUri          entityCircleUri,
    final SSEntityEnum   entityType) throws Exception{
    
    return false;
  }
  
  @Override
  public Boolean addEntityToCircle(
    final SSUri        userUri, 
    final SSUri        circleUri, 
    final SSUri        entityUri, 
    final SSEntityEnum entityType) throws Exception{
    
    return false;
  }  
  
  @Override
  public SSEntityDescA getDescForEntity(
    final SSEntityEnum    entityType,
    final SSUri           userUri,
    final SSUri           entityUri,
    final SSLabelStr      label,
    final Long            creationTime,
    final List<SSTag>     tags,
    final SSRatingOverall overallRating,
    final List<SSUri>     discUris,
    final SSUri           author) throws Exception{
    
    if(!SSEntityEnum.equals(entityType, SSEntityEnum.user)){
      return SSEntityDesc.get(entityUri, label, creationTime, tags, overallRating, discUris, author);
    }
    
    return SSUserDesc.get(
      entityUri,
      label,
      creationTime,
      tags,
      overallRating,
      discUris,
      author);
  }
  
  /* SSUserClientI  */
  @Override
  public void userLogin(SSSocketCon sSCon, SSServPar par) throws Exception {
    
    SSServCaller.checkKey(par);
    
    SSUri user = userLogin(par);
    
    sSCon.writeRetFullToClient(SSUserLoginRet.get(user, par.op));
  }

  @Override
  public void userAll(SSSocketCon sSCon, SSServPar par) throws Exception {
    
//      if(SSAuthEnum.isSame(SSAuthServ.inst().getAuthType(), SSAuthEnum.wikiAuth)){
//        returnObj.object =  new SSAuthWikiDbCon(new SSAuthWikiConf()).getUserList(); //TODO remove new SSAuthWikiConf() --> take it from config
//      }else{
        
    SSServCaller.checkKey(par);
    
    sSCon.writeRetFullToClient(SSUserAllRet.get(userAll(par), par.op));
//      }
  }

  /* SSUserServerI */
  @Override
  public SSUri userLogin(final SSServPar parA) throws Exception{
    
    try{
      final SSUserLoginPar      par     = new SSUserLoginPar (parA);
      final SSUri               user    = createUserURI (par.userLabel);
      
      SSServCaller.addUser         (user, par.userLabel, par.shouldCommit);
      SSServCaller.collUserRootAdd (user, par.shouldCommit);
      
      return user;
    }catch(SSSQLDeadLockErr deadLockErr){
      
      if(dbSQL.rollBack(parA)){
        return userLogin(parA);
      }else{
        SSServErrReg.regErrThrow(deadLockErr);
        return null;
      }
      
    }catch(Exception error){
      dbSQL.rollBack(parA);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  @Override
  public Boolean userExists(final SSServPar parA) throws Exception{
    
    final SSUserExistsPar par = new SSUserExistsPar (parA);
    
    try{

      return sqlFct.existsUser(par.user);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  @Override
  public void userAdd(final SSServPar parA) throws Exception{
    
    try{
      
      final SSUserAddPar par = new SSUserAddPar (parA);
        
      dbSQL.startTrans(par.shouldCommit);
      
      SSServCaller.entityAdd(
        SSUri.get(SSUserGlobals.systemUserURI),
        par.userUri,
        par.userLabel,
        SSEntityEnum.user,
        false);
      
      dbSQL.commit(par.shouldCommit);
      
    }catch(SSSQLDeadLockErr deadLockErr){
      
      if(dbSQL.rollBack(parA)){
        userAdd(parA);
      }else{
        SSServErrReg.regErrThrow(deadLockErr);
      }
      
    }catch(Exception error){
      dbSQL.rollBack(parA);
      SSServErrReg.regErrThrow(error);
    }
  }
  
  @Override
  public List<SSUri> userAll(final SSServPar parA) throws Exception {
    return sqlFct.userAll();
  }

  @Override
  public SSUri userSystemGet(final SSServPar parA) throws Exception {
    return sqlFct.userSystem();
  }

  @Override 
  public List<SSUser> usersGet(final SSServPar parA) throws Exception{
    
    try{
      final SSUsersGetPar par   = new SSUsersGetPar(parA);
      final List<SSUser>  users = new ArrayList<SSUser>();
      
      for(SSUri userUri : par.userUris){
        users.add(sqlFct.getUser(userUri));
      }
      
      return users;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  private SSUri createUserURI(SSLabelStr userLabel) throws Exception {
    return sqlFct.createUserUri(userLabel);
  }
}

//@Override
//  public String userNameFromUri(SSServPar parI) throws Exception {
//    
//    SSUserNameFromUriPar par = new SSUserNameFromUriPar (parI);
//    
//    String userUri;
//    
//    if(SSObjU.isNull(par.user)){
//      return null;
//    }
//    
//    userUri = SSStrU.removeTrailingSlash(SSStrU.toString(par.user));
//    
//    return userUri.substring(userUri.lastIndexOf(SSStrU.slash) + 1, userUri.length());
//  }