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
package at.kc.tugraz.ss.service.userevent.impl.fct.misc;

import at.kc.tugraz.socialserver.utils.SSIDU;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import at.kc.tugraz.ss.serv.serv.caller.SSServCaller;
import at.kc.tugraz.ss.service.userevent.datatypes.SSUE;
import java.util.ArrayList;
import java.util.List;

public class SSUEMiscFct {
  
//  public static List<SSUE> filterUEs(
//    final List<SSUE> ues, 
//    final Long       startTime, 
//    final Long       endTime){
//    
//    final List<SSUE> uesAfterStartTimeExclusion = new ArrayList<SSUE>();
//    final List<SSUE> uesAfterEndTimeExclusion   = new ArrayList<SSUE>();
//    
//    if(startTime != null){
//      
//      for(SSUE ue : ues){
//      
//        if(ue.timestamp > startTime){
//          uesAfterStartTimeExclusion.add(ue);
//        }
//      }
//    }else{
//      uesAfterStartTimeExclusion.addAll(ues);
//    }
//    
//    if(endTime != null){
//      
//      for(SSUE ue : uesAfterStartTimeExclusion){
//      
//        if(ue.timestamp < endTime){
//          uesAfterEndTimeExclusion.add(ue);
//        }
//      }
//    }else{
//      uesAfterEndTimeExclusion.addAll(uesAfterStartTimeExclusion);
//    }
//    
//    return uesAfterEndTimeExclusion;
//  }

  public static SSUri createUEUri() throws Exception {
    return SSUri.get(SSIDU.uniqueID(objUE().toString()));
  }
  
  private static SSUri objUE() throws Exception{
    return SSUri.get(SSServCaller.vocURIPrefixGet(), SSEntityEnum.userEvent.toString());
  }
}