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
package at.kc.tugraz.ss.service.coll.impl;

import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import at.kc.tugraz.ss.datatypes.datatypes.SSSpaceEnum;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserRootAddPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserWithEntriesPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntryAddPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserSharePar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntryChangePosPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollsUserWithEntriesPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntryDeletePar;
import at.kc.tugraz.socialserver.utils.*;
import at.kc.tugraz.ss.adapter.socket.datatypes.SSSocketCon;
import at.kc.tugraz.ss.serv.serv.api.SSServConfA;
import at.kc.tugraz.ss.serv.db.api.SSDBGraphI;
import at.kc.tugraz.ss.serv.db.api.SSDBSQLI;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSLabelStr;
import at.kc.tugraz.ss.serv.serv.api.SSServImplWithDBA;
import at.kc.tugraz.ss.service.coll.api.*;
import at.kc.tugraz.ss.service.coll.datatypes.*;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityDescA;
import at.kc.tugraz.ss.datatypes.datatypes.SSTagLabel;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.SSEntityDesc;
import at.kc.tugraz.ss.serv.datatypes.entity.datatypes.par.SSEntityUserDirectlyAdjoinedEntitiesRemovePar;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserParentGetPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserRootGetPar;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserEntryAddRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserEntryChangePosRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserEntryDeleteRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserParentGetRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserRootGetRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserShareRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollSharedAllRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserWithEntriesRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollsUserWithEntriesRet;
import at.kc.tugraz.ss.service.coll.impl.fct.sql.SSCollSQLFct;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntriesAddPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntriesDeletePar;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserEntriesAddRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserEntriesDeleteRet;
import at.kc.tugraz.ss.serv.serv.api.SSEntityHandlerImplI;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollEntityPrivateForUserIsPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollEntitySharedOrFollowedForUserIsPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserCummulatedTagsGetPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserHierarchyGetPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserSpaceGetPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollsUserEntityIsInGetPar;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserCummulatedTagsGetRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollUserHierarchyGetRet;
import at.kc.tugraz.ss.service.coll.datatypes.ret.SSCollsUserEntityIsInGetRet;
import at.kc.tugraz.ss.service.coll.impl.fct.ue.SSCollUEFct;
import at.kc.tugraz.ss.service.rating.datatypes.SSRatingOverall;
import at.kc.tugraz.ss.service.tag.datatypes.SSTag;
import at.kc.tugraz.ss.service.tag.datatypes.SSTagFrequ;
import java.lang.reflect.Method;
import java.util.*;

public class SSCollImpl extends SSServImplWithDBA implements SSCollClientI, SSCollServerI, SSEntityHandlerImplI{

  private final SSCollSQLFct sqlFct;

  public SSCollImpl(final SSServConfA conf, final SSDBGraphI dbGraph, final SSDBSQLI dbSQL) throws Exception{

    super(conf, dbGraph, dbSQL);

    sqlFct = new SSCollSQLFct(dbSQL);
  }
  
  @Override
  public void removeDirectlyAdjoinedEntitiesForUser(
    final SSEntityEnum                                  entityType,
    final SSEntityUserDirectlyAdjoinedEntitiesRemovePar par,
    final Boolean                                       shouldCommit) throws Exception{
    
    if(!par.removeFromUserColls){
      return;
    }
    
    try{
      final List<SSColl> collsUserEntityIsInGet = SSServCaller.collsUserEntityIsInGet(par.user, par.entityUri);
      
      for(SSColl coll : collsUserEntityIsInGet){
        SSServCaller.collUserEntryDelete(par.user, par.entityUri, coll.uri, true, shouldCommit);
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
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

    if(!SSEntityEnum.equals(entityType, SSEntityEnum.coll)){
      return SSEntityDesc.get(entityUri, label, creationTime, tags, overallRating, discUris, author);
    }

    return SSCollDesc.get(
      entityUri,
      label,
      creationTime, 
      tags, 
      overallRating,
      discUris,
      author);
  }

  /**
   * SSServRegisterableImplI 
   */
  @Override
  public List<SSMethU> publishClientOps() throws Exception{

    List<SSMethU> clientOps = new ArrayList<SSMethU>();

    Method[] methods = SSCollClientI.class.getMethods();

    for(Method method : methods){
      clientOps.add(SSMethU.get(method.getName()));
    }

    return clientOps;
  }

  @Override
  public List<SSMethU> publishServerOps() throws Exception{

    List<SSMethU> serverOps = new ArrayList<SSMethU>();

    Method[] methods = SSCollServerI.class.getMethods();

    for(Method method : methods){
      serverOps.add(SSMethU.get(method.getName()));
    }

    return serverOps;
  }

  @Override
  public void handleClientOp(SSSocketCon sSCon, SSServPar par) throws Exception{
    SSCollClientI.class.getMethod(SSMethU.toStr(par.op), SSSocketCon.class, SSServPar.class).invoke(this, sSCon, par);
  }

  @Override
  public Object handleServerOp(SSServPar par) throws Exception{
    return SSCollServerI.class.getMethod(SSMethU.toStr(par.op), SSServPar.class).invoke(this, par);
  }

  /**
   * ****
   * SSCollClientI *****
   */
  @Override
  public void collUserParentGet(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    SSColl collParent = collUserParentGet(par);

    sSCon.writeRetFullToClient(SSCollUserParentGetRet.get(collParent, par.op));
  }

  @Override
  public void collUserRootGet(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    SSColl collRoot = collUserRootGet(par);

    sSCon.writeRetFullToClient(SSCollUserRootGetRet.get(collRoot, par.op));
  }

  @Override
  public void collUserEntryDelete(final SSSocketCon sSCon, final SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserEntryDeleteRet.get(collUserEntryDelete(par), par.op));
  }

  @Override
  public void collUserEntriesDelete(final SSSocketCon sSCon, final SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserEntriesDeleteRet.get(collUserEntriesDelete(par), par.op));
  }

  @Override
  public void collSharedAll(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollSharedAllRet.get(collSharedAll(par), par.op));
  }

  @Override
  public void collUserShare(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserShareRet.get(collUserShare(par), par.op));
  }

  @Override
  public void collUserEntryAdd(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserEntryAddRet.get(collUserEntryAdd(par), par.op));
  }

  @Override
  public void collUserEntriesAdd(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserEntriesAddRet.get(collUserEntriesAdd(par), par.op));
  }
  
  @Override
  public void collUserEntryChangePos(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserEntryChangePosRet.get(collUserEntryChangePos(par), par.op));
  }

  @Override
  public void collUserWithEntries(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserWithEntriesRet.get(collUserWithEntries(par), par.op));
  }

  @Override
  public void collsUserWithEntries(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollsUserWithEntriesRet.get(collsUserWithEntries(par), par.op));
  }

  @Override
  public void collUserHierarchyGet(SSSocketCon sSCon, SSServPar par) throws Exception{

    SSServCaller.checkKey(par);

    sSCon.writeRetFullToClient(SSCollUserHierarchyGetRet.get(collUserHierarchyGet(par), par.op));
  }
  
  @Override
  public void collUserCumulatedTagsGet(final SSSocketCon sSCon, final SSServPar parA) throws Exception{
    
    SSServCaller.checkKey(parA);

    sSCon.writeRetFullToClient(SSCollUserCummulatedTagsGetRet.get(collUserCumulatedTagsGet(parA), parA.op));
  }
  
  @Override
  public void collsUserEntityIsInGet(final SSSocketCon sSCon, final SSServPar parA) throws Exception{
    
    SSServCaller.checkKey(parA);

    sSCon.writeRetFullToClient(SSCollsUserEntityIsInGetRet.get(collsUserEntityIsInGet(parA), parA.op));
  }

   /*********************** SSCollServerI * *********************/
   
  @Override
  public Boolean collUserRootAdd(SSServPar parA) throws Exception{

    final SSCollUserRootAddPar par = new SSCollUserRootAddPar(parA);
    final SSUri rootCollUri;

    try{

      if(sqlFct.existsUserRootColl(par.user)){
        return true;
      }

      rootCollUri = sqlFct.createCollURI();

      SSServCaller.addEntity(
        par.user,
        rootCollUri,
        SSLabelStr.get(SSStrU.valueRoot),
        SSEntityEnum.coll);

      dbSQL.startTrans(par.shouldCommit);

      sqlFct.createColl     (rootCollUri);
      sqlFct.addUserRootColl(rootCollUri, par.user);

      dbSQL.commit(par.shouldCommit);

      return true;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public SSUri collUserEntryAdd(final SSServPar parA) throws Exception{

    final SSCollUserEntryAddPar par = new SSCollUserEntryAddPar(parA);
    final SSColl parentColl;

    try{

      parentColl = sqlFct.getUserColl(par.user, par.coll);

      if(
        par.addNewColl || 
        sqlFct.isColl(par.collEntry)){ //to add coll entry is coll itself

        if(
          SSSpaceEnum.isSharedOrFollow(parentColl.space) && 
          SSSpaceEnum.isPrivate(par.space)){
          SSServErrReg.regErrThrow(new Exception("cannot add private to shared / followed collection"));
          return null;
        }

        if(SSSpaceEnum.isFollow(par.space)){

          if(par.addNewColl){
            SSServErrReg.regErrThrow(new Exception("cannot add a new coll and follow the same at the same time"));
            return null;
          }

          if(!sqlFct.isRootColl(par.coll)){
            SSServErrReg.regErrThrow(new Exception("cannot follow coll on this level"));
            return null;
          }

          if(sqlFct.followsUserColl(par.user, par.collEntry)){
            SSServErrReg.regErrThrow(new Exception("coll already followed"));
            return null;
          }

          if(sqlFct.followsUserAParentOrSubColl(par.user, par.collEntry)){
            SSServErrReg.regErrThrow(new Exception("a sub or a parent coll is already followed"));
            return null;
          }
        }

        if(par.addNewColl){
          par.collEntry = sqlFct.createCollURI();
        }

        SSServCaller.addEntity(
          par.user,
          par.collEntry,
          par.collEntryLabel,
          SSEntityEnum.coll);

        dbSQL.startTrans(par.shouldCommit);

        if(par.addNewColl){
          sqlFct.createColl(par.collEntry);
        }

        sqlFct.addCollToUserColl(par.user, par.coll, par.collEntry, par.space, par.collEntryLabel);
        
        dbSQL.commit(par.shouldCommit);
        
      }else{   //coll entry is NO collection

        if(sqlFct.containsEntry(par.coll, par.collEntry)){
          return par.collEntry;
        }

        SSServCaller.addEntity(
          par.user,
          par.collEntry,
          par.collEntryLabel,
          SSEntityEnum.entity);

        dbSQL.startTrans(par.shouldCommit);

        sqlFct.addEntryToColl(par.coll, par.collEntry, par.collEntryLabel);
        
        dbSQL.commit(par.shouldCommit);
      }
      
      SSCollUEFct.collUserEntryAdd(par);

      return par.collEntry;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collUserEntriesAdd(final SSServPar parA) throws Exception{

    final SSCollUserEntriesAddPar par = new SSCollUserEntriesAddPar(parA);

    try{

      for(int counter = 0; counter < par.entries.size(); counter++){

        dbSQL.startTrans(par.shouldCommit);

        SSServCaller.collUserEntryAdd(
          par.user,
          par.coll,
          par.entries.get(counter),
          par.entryLabels.get(counter),
          par.entrySpaces.get(counter),
          -1,
          false,
          par.saveUE,
          false);

        dbSQL.commit(par.shouldCommit);
      }

      return true;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collUserEntriesDelete(final SSServPar parA) throws Exception{

    final SSCollUserEntriesDeletePar par = new SSCollUserEntriesDeletePar(parA);

    try{

      for(SSUri collEntryUri : par.collEntries){

        dbSQL.startTrans(par.shouldCommit);

        SSServCaller.collUserEntryDelete(par.user, collEntryUri, par.coll, par.saveUE, false);

        dbSQL.commit(par.shouldCommit);
      }
      
      return true;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collUserEntryDelete(final SSServPar parA) throws Exception{

    final SSCollUserEntryDeletePar par = new SSCollUserEntryDeletePar(parA);

    try{

      sqlFct.getUserColl(par.user, par.coll);

      if(sqlFct.isColl(par.collEntry)){ //to remove coll entry is coll itself

        if(sqlFct.followsUserColl(par.user, par.collEntry)){ //to remove coll is only followed by user

          dbSQL.startTrans(par.shouldCommit);

          sqlFct.unfollowColl(par.user, par.coll, par.collEntry);

          dbSQL.commit(par.shouldCommit);
          
          SSCollUEFct.collUserUnSubscribeColl(par);
          
        }else{//to remove coll at least private or shared

          dbSQL.startTrans(par.shouldCommit);

          //remove the coll
          sqlFct.removeColl(par.collEntry);

          dbSQL.commit(par.shouldCommit);
          
          SSCollUEFct.collUserDeleteColl(par);
        }
      }else{   //to remove coll entry is NO collection

        dbSQL.startTrans(par.shouldCommit);

        sqlFct.removeEntryFromColl(par.coll, par.collEntry);

        dbSQL.commit(par.shouldCommit);
      }

      SSCollUEFct.collUserEntryDelete(par);
      
      return true;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collUserShare(SSServPar parA) throws Exception{

    final SSCollUserSharePar par = new SSCollUserSharePar(parA);

    try{
      sqlFct.getUserColl(par.user, par.coll);

      dbSQL.startTrans(par.shouldCommit);

      sqlFct.shareColl(par.coll);

      dbSQL.commit(par.shouldCommit);
      
      SSCollUEFct.collUserShareColl(par);
      return true;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collUserEntryChangePos(SSServPar parA) throws Exception{

    final SSCollUserEntryChangePosPar par = new SSCollUserEntryChangePosPar(parA);
    final List<SSUri> collEntries = new ArrayList<SSUri>();
    final List<Integer> order = new ArrayList<Integer>();
    Integer counter = 0;

    try{

      sqlFct.getUserColl(par.user, par.coll);

      while(counter < par.order.size()){
        collEntries.add(SSUri.get(par.order.get(counter++)));
        order.add(Integer.valueOf(par.order.get(counter++)));
      }

      dbSQL.startTrans(par.shouldCommit);

      sqlFct.changeCollEntriesPos(par.coll, collEntries, order);

      dbSQL.commit(par.shouldCommit);

      return true;
    }catch(Exception error){
      dbSQL.rollBack(par.shouldCommit);
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public List<SSColl> collSharedAll(SSServPar par) throws Exception{

    final List<SSColl> sharedColls = new ArrayList<SSColl>();

    try{

      for(SSUri sharedCollUri : sqlFct.getAllSharedCollURIs()){
        sharedColls.add(sqlFct.getColl(sharedCollUri, SSSpaceEnum.sharedSpace));
      }

      return sharedColls;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public SSColl collUserWithEntries(SSServPar parA) throws Exception{

    final SSCollUserWithEntriesPar par = new SSCollUserWithEntriesPar(parA);

    try{
      return sqlFct.getUserCollWithEntries(par.user, par.coll, par.sort);
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public List<SSColl> collsUserWithEntries(SSServPar parA) throws Exception{

    final SSCollsUserWithEntriesPar par                  = new SSCollsUserWithEntriesPar(parA);
    final List<SSColl>              userCollsWithEntries = new ArrayList<SSColl>();

    try{

      for(String collUri : sqlFct.getAllUserCollURIs(par.user)){
        userCollsWithEntries.add(sqlFct.getUserCollWithEntries(par.user, SSUri.get(collUri), true));
      }

      return userCollsWithEntries;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public SSColl collUserRootGet(SSServPar parA) throws Exception{

    SSCollUserRootGetPar par = new SSCollUserRootGetPar(parA);

    try{
      return sqlFct.getUserCollWithEntries(par.user, sqlFct.getUserRootCollURI(par.user), true);
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public SSColl collUserParentGet(SSServPar parA) throws Exception{

    SSCollUserParentGetPar par = new SSCollUserParentGetPar(parA);

    try{
      return sqlFct.getUserCollWithEntries(par.user, sqlFct.getUserDirectParentCollURI(par.user, par.coll), true);
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public SSSpaceEnum collUserSpaceGet(SSServPar parA) throws Exception{

    SSCollUserSpaceGetPar par = new SSCollUserSpaceGetPar(parA);

    try{
      return sqlFct.getUserColl(par.user, par.collUri).space;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  @Override
  public List<SSColl> collUserHierarchyGet(SSServPar parA) throws Exception{

    final SSCollUserHierarchyGetPar par           = new SSCollUserHierarchyGetPar(parA);
    final List<SSUri>               hierarchy     = new ArrayList<SSUri>();
    final List<SSColl>              colls         = new ArrayList<SSColl>();
    final SSUri                     rootCollUri;
    SSUri directPartentCollUri;
    
    try{
      
      sqlFct.getUserColl(par.user, par.collUri);
      
      rootCollUri          = sqlFct.getUserRootCollURI         (par.user);
      directPartentCollUri = sqlFct.getUserDirectParentCollURI (par.user, par.collUri);
      
      while(SSUri.isNotSame(rootCollUri, directPartentCollUri)){
        
        hierarchy.add(directPartentCollUri);
        
        directPartentCollUri = sqlFct.getUserDirectParentCollURI (par.user, directPartentCollUri);
      }
      
      hierarchy.add(rootCollUri);

      for(SSUri collUri : hierarchy){
        
        colls.add(
          SSColl.get(
          collUri, 
          null, 
          null, 
          SSStrU.toString(SSServCaller.entityLabelGet(collUri)),
          null));
      }
      
      return colls;
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  @Override
  public List<SSTagFrequ> collUserCumulatedTagsGet(final SSServPar parA) throws Exception{
   
    final SSCollUserCummulatedTagsGetPar par            = new SSCollUserCummulatedTagsGetPar(parA);
    final Map<String, Integer>           tagLabelFrequs = new HashMap<String, Integer>();
    final List<SSTagFrequ>               tagFrequs      = new ArrayList<SSTagFrequ>();
    SSColl                               coll;
    String      tagLabel;

    try{
      coll = sqlFct.getUserCollWithEntries(par.user, par.collUri, false);
      
      for(SSTagFrequ tagFrequ : SSServCaller.tagUserFrequsGet(par.user, par.collUri, null, null)){
        
        tagLabel = tagFrequ.label.toString();
          
        if(tagLabelFrequs.containsKey(tagLabel)){
          tagLabelFrequs.put(tagLabel, tagLabelFrequs.get(tagLabel) + tagFrequ.frequ);
        }else{
          tagLabelFrequs.put(tagLabel, tagFrequ.frequ);
        }
      }
      
      for(SSCollEntry collEntry : coll.entries){
        
        if(SSEntityEnum.equals(collEntry.entityType, SSEntityEnum.coll)){
         
          for(SSTagFrequ tagFrequ : SSServCaller.collUserCumulatedTagsGet(par.user, collEntry.uri)){
            
            tagLabel = tagFrequ.label.toString();
            
            if(tagLabelFrequs.containsKey(tagLabel)){
              tagLabelFrequs.put(tagLabel, tagLabelFrequs.get(tagLabel) + tagFrequ.frequ);
            }else{
              tagLabelFrequs.put(tagLabel, tagFrequ.frequ);
            }
          }
          
        }else{
          
          for(SSTagFrequ tagFrequ : SSServCaller.tagUserFrequsGet(par.user, collEntry.uri, null, null)){
            
            tagLabel = tagFrequ.label.toString();
            
            if(tagLabelFrequs.containsKey(tagLabel)){
              tagLabelFrequs.put(tagLabel, tagLabelFrequs.get(tagLabel) + tagFrequ.frequ);
            }else{
              tagLabelFrequs.put(tagLabel, tagFrequ.frequ);
            }
          }
        }
      }
      
      for(Map.Entry<String, Integer> entry : tagLabelFrequs.entrySet()){
        
        tagFrequs.add(SSTagFrequ.get(
          SSTagLabel.get(entry.getKey()), 
          null, 
          entry.getValue()));
      }
      
      return tagFrequs;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collEntityPrivateForUserIs(final SSServPar parA) throws Exception{

    final SSCollEntityPrivateForUserIsPar          par = new SSCollEntityPrivateForUserIsPar(parA);
    final List<String>                             collUris;
    final List<String>                             userCollUris;
    
    try{
      
      userCollUris = sqlFct.getAllUserCollURIs          (par.user);
      collUris     = sqlFct.getCollUrisContainingEntity (par.entityUri);
        
      for(String collUri : SSStrU.retainAll(collUris, userCollUris)){

        if(SSSpaceEnum.isPrivate(sqlFct.getUserColl(par.user, SSUri.get(collUri)).space)){
          return true;
        }
      }
      
      return false;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }

  @Override
  public Boolean collEntitySharedOrFollowedForUserIs(final SSServPar parA) throws Exception{

    final SSCollEntitySharedOrFollowedForUserIsPar par = new SSCollEntitySharedOrFollowedForUserIsPar(parA);
    final List<String>                             collUris;
    final List<String>                             userCollUris;
    
    try{
      
      userCollUris = sqlFct.getAllUserCollURIs          (par.user);
      collUris     = sqlFct.getCollUrisContainingEntity (par.entityUri);
      
      for(String collUri : SSStrU.retainAll(collUris, userCollUris)){

        if(SSSpaceEnum.isSharedOrFollow(sqlFct.getUserColl(par.user, SSUri.get(collUri)).space)){
          return true;
        }
      }
        
      return false;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  @Override
  public List<SSColl> collsUserEntityIsInGet(final SSServPar parA) throws Exception{
    
    final SSCollsUserEntityIsInGetPar par      = new SSCollsUserEntityIsInGetPar(parA);
    final List<SSColl>                colls    = new ArrayList<SSColl>();
    final List<String>                collUris;
    final List<String>                userCollUris;
    
    try{
      
      userCollUris = sqlFct.getAllUserCollURIs          (par.user);
      collUris     = sqlFct.getCollUrisContainingEntity (par.entityUri);
       
      for(String collUri : SSStrU.retainAll(collUris, userCollUris)){
        colls.add(sqlFct.getUserColl(par.user, SSUri.get(collUri)));
      }
      
      return colls;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
}