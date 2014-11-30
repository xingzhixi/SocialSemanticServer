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
 package at.kc.tugraz.ss.like.datatypes;

import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSEntityA;
import java.util.HashMap;
import java.util.Map;

public class SSLikes extends SSEntityA{
  
  public Integer likes      = null;
  public Integer dislikes   = null;
  public Integer like       = null;
  
  public static SSLikes get(
    final Integer     likes,
    final Integer     dislikes,
    final Integer     like) throws Exception{
    
    return new SSLikes(likes, dislikes, like);
  }
  
  protected SSLikes(
    final Integer   likes,
    final Integer   dislikes,
    final Integer   like) throws Exception{
    
    super(SSStrU.empty);
    
    this.likes    = likes;
    this.dislikes = dislikes;
    this.like     = like;
  }
  
  @Override
  public Object jsonLDDesc(){
    
    final Map<String, Object> ld = new HashMap<>();
    
    ld.put(SSVarU.likes,            SSVarU.xsd + SSStrU.colon + SSStrU.valueInteger);
    ld.put(SSVarU.dislikes,         SSVarU.xsd + SSStrU.colon + SSStrU.valueInteger);
    ld.put(SSVarU.like,             SSVarU.xsd + SSStrU.colon + SSStrU.valueInteger);
    
    return ld;
  }
}