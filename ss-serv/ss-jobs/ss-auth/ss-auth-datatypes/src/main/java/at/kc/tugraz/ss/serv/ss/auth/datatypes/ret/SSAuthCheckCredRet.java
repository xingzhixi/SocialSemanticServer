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
package at.kc.tugraz.ss.serv.ss.auth.datatypes.ret;

import at.kc.tugraz.socialserver.utils.SSLinkU;
import at.kc.tugraz.socialserver.utils.SSMethU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.serv.datatypes.SSServRetI;
import java.util.HashMap;
import java.util.Map;

public class SSAuthCheckCredRet extends SSServRetI{

  public String key = null;

  public static SSAuthCheckCredRet get(String key, SSMethU op){
    return new SSAuthCheckCredRet(key, op);
  }

  private SSAuthCheckCredRet(String key, SSMethU op){
    
    super(op);
    
    this.key = key;
  }

  @Override
  public Map<String, Object> jsonLDDesc(){
    
    Map<String, Object> ld = new HashMap<String, Object>();
    
    ld.put(SSVarU.key, SSLinkU.xsd + SSStrU.valueString);
    
    return ld;
  }
  
  public String getKey() {
    return key;
  }  
}