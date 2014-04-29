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
 package at.kc.tugraz.ss.service.tag.datatypes;

import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.SSSpaceEnum;
import at.kc.tugraz.ss.datatypes.datatypes.SSTagLabel;
import at.kc.tugraz.ss.serv.jsonld.datatypes.api.SSJSONLDPropI;
import java.util.HashMap;
import java.util.Map;

public class SSTagFrequ implements SSJSONLDPropI{
  
  public SSTagLabel    label  = null;
  public SSSpaceEnum    space  = null;
	public Integer        frequ  = -1;

  public static SSTagFrequ get(
    SSTagLabel    label,
    SSSpaceEnum    space,
    Integer        frequ){
    
    return new SSTagFrequ(label, space, frequ);
  }  
	
  private SSTagFrequ(
    SSTagLabel    label,
    SSSpaceEnum    space,
    Integer        frequ){
		
		this.label      = label;
    this.space      = space;
		this.frequ      = frequ;
	}
  
  @Override
  public Object jsonLDDesc() {
  
    Map<String, Object> ld = new HashMap<String, Object>();
    
    ld.put(SSVarU.label,   SSVarU.sss + SSStrU.colon + SSTagLabel.class.getName());
    ld.put(SSVarU.space,   SSVarU.sss + SSStrU.colon + SSSpaceEnum.class.getName());
    ld.put(SSVarU.frequ,   SSVarU.xsd + SSStrU.colon + SSStrU.valueInteger);
    
    return ld;
  }  
  
  /*************** getters to allow for json enconding ********************/
  public String getLabel() {
		return SSTagLabel.toStr(label);
	}
  
  public String getSpace() {
		return SSSpaceEnum.toStr(space);
	}

	public int getFrequ() {
		return frequ;
	}	
}