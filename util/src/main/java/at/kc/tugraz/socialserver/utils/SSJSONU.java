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
package at.kc.tugraz.socialserver.utils;

import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

public class SSJSONU{

  public static final JsonToken jsonEnd = JsonToken.END_OBJECT;
  
  public static String jsonStr(Object obj) throws Exception{
    return new ObjectMapper().writeValueAsString(obj);
  }
  
  public static List<String> jsonArrayList(String jsonStr) throws Exception {
    
    ObjectMapper mapper = new ObjectMapper();
    
    JavaType     type   = mapper.getTypeFactory().constructCollectionType(List.class, String.class);
    
    return mapper.readValue(jsonStr, type);
  }
  
  public static Map<String, String> jsonMap(String jsonStr) throws Exception{
    return new ObjectMapper().readValue(jsonStr, new TypeReference<Map<String,String>>() { });
  }
  
  public static JsonParser jsonParser(String jsonStr) throws Exception{
    return new JsonFactory().createJsonParser(jsonStr);
  }
}