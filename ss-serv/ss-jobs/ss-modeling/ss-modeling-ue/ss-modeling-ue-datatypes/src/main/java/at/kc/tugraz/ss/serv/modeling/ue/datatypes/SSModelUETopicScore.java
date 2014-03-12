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
 package at.kc.tugraz.ss.serv.modeling.ue.datatypes;

import at.kc.tugraz.ss.datatypes.datatypes.SSTagLabel;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityA;
import java.util.HashMap;
import java.util.Map;

public class SSModelUETopicScore extends SSEntityA {
  
	public SSTagLabel    topic   = null;
	public Integer        level   = -1;
	public Integer        frequ   = -1;
	
	public SSModelUETopicScore(
			SSTagLabel topic,
			int         level,
			int         frequ){
    
    super(level);
		
		this.topic      = topic;
		this.level      = level;
		this.frequ      = frequ;
	}
  
  @Override
  public Object jsonLDDesc(){
    
    Map<String, Object> ld = new HashMap<String, Object>();
    
    ld.put(SSVarU.topic, SSVarU.sss + SSStrU.colon + SSTagLabel.class.getName());
    ld.put(SSVarU.level, SSVarU.xsd + SSStrU.colon + SSStrU.valueInteger);
    ld.put(SSVarU.frequ, SSVarU.xsd + SSStrU.colon + SSStrU.valueInteger);
    
    return ld;
  }
   
  /*************** getters to allow for json enconding ********************/
  public String getTopic(){
    return SSTagLabel.toStr(topic);
  }

  public int getLevel(){
    return level;
  }

  public int getFrequ(){
    return frequ;
  }
}