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
package at.kc.tugraz.ss.serv.jobs.evernote.conf;

import at.kc.tugraz.ss.serv.serv.api.SSServConfA;
import java.util.List;

public class SSEvernoteConf extends SSServConfA{
  
  public Boolean      addToGraph         = false;
  public String       companyName         = "KTI";
  public String       appName             = "EvernoteDataSync";
  public String       appVersion          = "1.0";
  public String       evernoteEnvironment = "sandbox";
  public List<String> authTokens          = null; //"S=s1:U=8b810:E=1499f470bf9:C=1424795dffc:P=1cd:A=en-devtoken:V=2:H=6eb18db6ec86ef2dcd064c99d4478523";
}