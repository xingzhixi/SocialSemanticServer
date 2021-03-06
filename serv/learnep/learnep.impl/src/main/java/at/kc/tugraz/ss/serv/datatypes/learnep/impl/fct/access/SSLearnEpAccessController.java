/**
* Code contributed to the Learning Layers project
* http://www.learning-layers.eu
* Development is partly funded by the FP7 Programme of the European Commission under
* Grant Agreement FP7-ICT-318209.
* Copyright (c) 2015, Graz University of Technology - KTI (Knowledge Technologies Institute).
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
package at.kc.tugraz.ss.serv.datatypes.learnep.impl.fct.access;

import at.kc.tugraz.socialserver.utils.SSDateU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.datatypes.datatypes.SSCircleE;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.serv.datatypes.learnep.conf.SSLearnEpConf;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sss.serv.err.datatypes.SSErr;
import sss.serv.err.datatypes.SSErrE;

public class SSLearnEpAccessController{
  
  private static final Map<String, String>       lockedLearnEps               = new HashMap<>(); //learnEp, user
  private static final Map<String, Long>         lockedLearnEpsLockTimes      = new HashMap<>(); //learnEp, time the episode got locked
  private static final ReentrantReadWriteLock    learnEpsLock                 = new ReentrantReadWriteLock();
  
  public static Boolean lock(
    final SSUri         user,
    final SSUri         learnEp) throws Exception{
    
    try{
      
      learnEpsLock.writeLock().lock();
      
      if(
        lockedLearnEps.containsKey(SSStrU.toStr(learnEp)) &&
        SSStrU.equals(lockedLearnEps.get(SSStrU.toStr(learnEp)), SSStrU.toStr(user))){
        return true;
      }
      
      if(lockedLearnEps.containsKey(SSStrU.toStr(learnEp))){
        return false;
      }
      
      lockedLearnEps.put          (SSStrU.toStr(learnEp), SSStrU.toStr(user));
      lockedLearnEpsLockTimes.put (SSStrU.toStr(learnEp), SSDateU.dateAsLong());
      return true;
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      learnEpsLock.writeLock().unlock();
    }
  }
  
  public static Boolean unLock(
    final SSUri         learnEp) throws Exception{
    
    try{
      
      learnEpsLock.writeLock().lock();
      
      if(lockedLearnEps.containsKey(SSStrU.toStr(learnEp))){

        lockedLearnEps.remove          (SSStrU.toStr(learnEp));
        lockedLearnEpsLockTimes.remove (SSStrU.toStr(learnEp));
        
        return true;
      }
      
      return false;
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      learnEpsLock.writeLock().unlock();
    }
  }
  
  public static Boolean isLocked(final SSUri learnEp) throws Exception{
    
    try{
      
      learnEpsLock.readLock().lock();
      
      return lockedLearnEps.containsKey(SSStrU.toStr(learnEp));
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      learnEpsLock.readLock().unlock();
    }
  }
  
  public static Boolean hasLock(
    final SSUri user, 
    final SSUri learnEp) throws Exception{
    
    try{
      
      learnEpsLock.readLock().lock();
      
      if(
        lockedLearnEps.containsKey(SSStrU.toStr(learnEp)) &&
        SSStrU.equals(lockedLearnEps.get(SSStrU.toStr(learnEp)), SSStrU.toStr(user))){
        return true;
      }
      
      return false;
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      learnEpsLock.readLock().unlock();
    }
  }
  
  public static void checkHasLock(
    final SSLearnEpConf learnEpConf,
    final SSUri         user,
    final SSUri         learnEp) throws Exception{
    
    try{
      if(
        !learnEpConf.useEpisodeLocking ||
        !SSStrU.contains(SSServCaller.circleTypesGet(user, user, learnEp, true), SSCircleE.group)){
        return;
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return;
    }
    
    try{
      
      learnEpsLock.readLock().lock();
      
      if(!hasLock(user, learnEp)){
        throw new SSErr(SSErrE.userNeedsLockOnEntity);
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }finally{
      learnEpsLock.readLock().unlock();
    }
  }
  
  public static Long getPassedTime(final SSUri learnEp) throws Exception{
    
    try{
      learnEpsLock.readLock().lock();
      
      if(isLocked(learnEp)){
        return SSDateU.dateAsLong() - lockedLearnEpsLockTimes.get(SSStrU.toStr(learnEp));
      }else{
        return 0L;
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      learnEpsLock.readLock().unlock();
    }
  }
  
  public static Long getRemainingTime(final SSUri learnEp) throws Exception{
    return (SSDateU.minuteInMilliSeconds * 5) - SSLearnEpAccessController.getPassedTime(learnEp);
  }
  
  public static List<String> getLockedLearnEps() throws Exception{
    
    try{
      learnEpsLock.readLock().lock();
      
      return new ArrayList<>(lockedLearnEps.keySet());
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }finally{
      learnEpsLock.readLock().unlock();
    }
  }
}