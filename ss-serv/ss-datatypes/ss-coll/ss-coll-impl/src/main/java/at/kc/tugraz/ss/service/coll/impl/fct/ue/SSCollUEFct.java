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
package at.kc.tugraz.ss.service.coll.impl.fct.ue;

import at.kc.tugraz.socialserver.utils.SSLogU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.service.userevent.datatypes.SSUEE;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntryAddPar;
import at.kc.tugraz.ss.service.coll.datatypes.pars.SSCollUserEntryDeletePar;

public class SSCollUEFct {
  
  public static void collUserEntryDelete(final SSCollUserEntryDeletePar par){
    
    if(!par.saveUE){
      return;
    }
    
    try{
      
      SSServCaller.ueAdd(
        par.user,
        par.entry,
        SSUEE.removeCollectionItem,
        SSStrU.toStr(par.coll),
        false);
      
      SSServCaller.ueAdd(
        par.user,
        par.coll,
        SSUEE.changeCollectionByRemoveCollectionItem,
        SSStrU.toStr(par.entry),
        false);
      
    }catch(Exception error){
      SSLogU.warn("storing ue failed");
    }
  }
  
  public static void collUserUnSubscribeColl(final SSCollUserEntryDeletePar par){
    
    if(!par.saveUE){
      return;
    }
    
    try{
      
      SSServCaller.ueAdd(
        par.user,
        par.entry,
        SSUEE.unSubscribeCollection,
        SSStrU.empty,
        false);
      
    }catch(Exception error){
      SSLogU.warn("storing ue failed");
    }
  }
  
  public static void collUserDeleteColl(final SSCollUserEntryDeletePar par){
    
    if(!par.saveUE){
      return;
    }
    
    try{
      
      SSServCaller.ueAdd(
        par.user,
        par.entry,
        SSUEE.removeCollection,
        SSStrU.empty,
        false);
      
    }catch(Exception error){
      SSLogU.warn("storing ue failed");
    }
  }
  
//  public static void collUserShareColl(final SSCollUserSharePar par){
//    
//    if(!par.saveUE){
//      return;
//    }
//        
//    try{
//      
//      SSServCaller.ueAdd(
//        par.user,
//        par.coll,
//        SSUEEnum.shareCollection,
//        SSStrU.empty,
//        par.shouldCommit);
//      
//    }catch(Exception error){
//      SSLogU.warn("storing ue failed");
//    }    
//  }

  public static void collUserEntryAdd(final SSCollUserEntryAddPar par){
    
    //TODO dtheiler: re-implement this
//    if(!par.saveUE){
//      return;
//    }
//        
//    try{
//      
//      if(par.addNewColl){
//
//        if(SSSpaceEnum.isPrivate(par.space)){
//          SSServCaller.ueAdd(
//          par.user,
//          par.collEntry,
//          SSUEEnum.createPrivateCollection,
//          SSStrU.empty,
//          par.shouldCommit);
//        }
//                
//        if(SSSpaceEnum.isShared(par.space)){
//        
//          SSServCaller.ueAdd(
//            par.user,
//            par.collEntry,
//            SSUEEnum.createSharedCollection,
//            SSStrU.empty,
//            par.shouldCommit);
//        }
//      }
//      
//      if(SSSpaceEnum.isFollow(par.space)){
//        
//        SSServCaller.ueAdd(
//          par.user,
//          par.collEntry,
//          SSUEEnum.subscribeCollection,
//          SSStrU.empty,
//          par.shouldCommit);
//      }
//      
//      if(SSSpaceEnum.isSharedOrFollow(par.space)){
//        
//        SSServCaller.ueAdd(
//          par.user,
//          par.collEntry,
//          SSUEEnum.addSharedCollectionItem,
//          SSStrU.toStr(par.coll),
//          par.shouldCommit);
//        
//        SSServCaller.ueAdd(
//          par.user,
//          par.coll,
//          SSUEEnum.changeCollectionByAddSharedCollectionItem,
//          SSStrU.toStr(par.collEntry),
//          par.shouldCommit);
//      }
//      
//      if(SSSpaceEnum.isPrivate(par.space)){
//        
//        SSServCaller.ueAdd(
//          par.user,
//          par.collEntry,
//          SSUEEnum.addPrivateCollectionItem,
//          SSStrU.toStr(par.coll),
//          par.shouldCommit);
//        
//        SSServCaller.ueAdd(
//          par.user,
//          par.coll,
//          SSUEEnum.changeCollectionByAddPrivateCollectionItem,
//          SSStrU.toStr(par.collEntry),
//          par.shouldCommit);
//      }
//      
//    }catch(Exception error){
//      SSLogU.warn("storing ue failed");
//    }
  }
}
