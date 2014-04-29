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
package at.kc.tugraz.ss.service.disc.datatypes;

import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityA;

public class SSDiscEntryContent extends SSEntityA {

  public static SSDiscEntryContent get(final String content) throws Exception{
    
    if(SSStrU.isEmpty(content)){
      return null; 
    }
    
    final String tmp = SSStrU.replaceLineFeedsWithTextualRepr(content);
    
    if(SSStrU.isEmpty(tmp)){
      return null; 
    }
    
    return new SSDiscEntryContent(tmp);
  }

  public static String toStr(final SSDiscEntryContent content){
    return SSStrU.toString(content);
  }
  
  private SSDiscEntryContent(final String value){
    super(value);
  }

  public static Boolean isEmpty(final SSDiscEntryContent content){
    
    if(
      content == null ||
      content.toString().isEmpty()){
      return true;
    }
    
    return false;
  }
  
  @Override
  public Object jsonLDDesc() {
    return SSVarU.xsd + SSStrU.colon + SSStrU.valueString;
  }
}