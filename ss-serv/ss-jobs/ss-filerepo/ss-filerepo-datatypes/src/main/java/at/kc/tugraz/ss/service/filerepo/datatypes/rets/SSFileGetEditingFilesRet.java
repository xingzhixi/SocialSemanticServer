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
 package at.kc.tugraz.ss.service.filerepo.datatypes.rets;

import at.kc.tugraz.socialserver.utils.SSMethU;
import at.kc.tugraz.ss.serv.jsonld.util.SSJSONLDU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.serv.datatypes.SSServRetI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSFileGetEditingFilesRet extends SSServRetI{

	public List<String> fileUris  = new ArrayList<String>();
	public List<String> fileNames = new ArrayList<String>();
	
	public SSFileGetEditingFilesRet(SSMethU op, List<String> fileUris, List<String> fileNames){
    
    super(op);
    
    if(fileUris != null){
      this.fileUris = fileUris;
    }
    
    if(fileNames != null){
      this.fileNames = fileNames;
    }
  }
  
  @Override
  public Map<String, Object> jsonLDDesc(){
    
    Map<String, Object> ld           = new HashMap<String, Object>();
    Map<String, Object> fileUrisObj  = new HashMap<String, Object>();
    Map<String, Object> fileNamesObj = new HashMap<String, Object>();
    
    fileUrisObj.put(SSJSONLDU.id,        SSVarU.xsd + SSStrU.colon + SSStrU.valueString);
    fileUrisObj.put(SSJSONLDU.container, SSJSONLDU.set);
    
    ld.put(SSVarU.fileUris, fileUrisObj);
    
    fileNamesObj.put(SSJSONLDU.id,        SSVarU.xsd + SSStrU.colon + SSStrU.valueString);
    fileNamesObj.put(SSJSONLDU.container, SSJSONLDU.set);
    
    ld.put(SSVarU.fileNames, fileNamesObj);
    
    return ld;
  }

  /*************** getters to allow for json enconding ********************/
  public List<String> getFileUris() {
		return fileUris;
	}

	public List<String> getFileNames() {
		return fileNames;
	}
}