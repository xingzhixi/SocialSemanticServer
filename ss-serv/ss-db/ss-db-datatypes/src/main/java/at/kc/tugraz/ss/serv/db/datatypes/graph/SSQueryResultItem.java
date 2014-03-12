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
 package at.kc.tugraz.ss.serv.db.datatypes.graph;

import java.util.HashMap;

public class SSQueryResultItem {

  private HashMap<String, String> set = null;

  public SSQueryResultItem() {
    this.set = new HashMap<String, String>();
  }

  public void setBinding(String key, String value) {
    set.put(key, value);
  }

  public String getBinding(String key) {
    return set.get(key);
  }
}