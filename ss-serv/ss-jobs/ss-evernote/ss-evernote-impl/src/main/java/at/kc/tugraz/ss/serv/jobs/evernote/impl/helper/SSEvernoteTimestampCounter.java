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
package at.kc.tugraz.ss.serv.jobs.evernote.impl.helper;

import at.kc.tugraz.socialserver.utils.SSDateU;

public class SSEvernoteTimestampCounter {

  private static Long    counter = 0L;
  private static Integer times   = 0; 
  
  public static Long get(){
    
    synchronized(counter){
      
      if(times > 10){
        counter = 0L;
        times   = 0;
      }
      
      counter += SSDateU.minuteInMilliSeconds;
      
      times++;
    }
    
    return counter;
  }
}