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
 package at.kc.tugraz.socialserver.service.broadcast.datatypes.enums;

import at.kc.tugraz.socialserver.utils.*;
import at.kc.tugraz.ss.serv.jsonld.datatypes.api.SSJSONLDPropI;

public enum SSBroadcastEnum implements SSJSONLDPropI{

	suDiscssion,
	suCollectionAddSharedItem,
	suCollectionRemoveSharedItem;

  public static SSBroadcastEnum get(String type) throws Exception{
    
    if(SSStrU.isEmpty(type)){
      return null;
    }
    
    return SSBroadcastEnum.valueOf(type);
  }
  
  @Override
  public Object jsonLDDesc(){
    return SSLinkU.xsd + SSStrU.valueString;
  }
}