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
 package at.kc.tugraz.ss.service.disc.datatypes;

import at.kc.tugraz.socialserver.utils.SSObjU;
import at.kc.tugraz.ss.serv.jsonld.util.SSJSONLDU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.SSLabelStr;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntityA;
import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import java.util.*;

public class SSDisc extends SSEntityA {
  
  public  SSUri             uri      = null;
  public  SSLabelStr        label    = null;
  public  SSUri             author   = null;
  public  SSUri             target   = null;
  public  List<SSDiscEntry> entries  = new ArrayList<SSDiscEntry>();

  public static SSDisc get(
    SSUri             uri,
    SSLabelStr        label,
    SSUri             author,
    SSUri             target,
    List<SSDiscEntry> entries){
    
    return new SSDisc(uri, label, author, target, entries);
  }

  private SSDisc(
    SSUri             uri,
    SSLabelStr        label,
    SSUri             author,
    SSUri             target,
    List<SSDiscEntry> entries){
    
    super(uri);
    
    this.uri      = uri;
    this.label    = label;
    this.author   = author;
    this.target   = target;
    
    if(SSObjU.isNotNull(entries)){
      this.entries  = entries;
    }
  }
  
  @Override
  public Object jsonLDDesc() {
   
    Map<String, Object> ld         = new HashMap<String, Object>();
    Map<String, Object> entriesObj = new HashMap<String, Object>();
    
    ld.put(SSVarU.uri, SSVarU.sss + SSStrU.colon + SSUri.class.getName());
    
    entriesObj.put(SSJSONLDU.id,        SSVarU.sss + SSStrU.colon + SSDiscEntry.class.getName());
    entriesObj.put(SSJSONLDU.container, SSJSONLDU.set);
    
    ld.put(SSVarU.entries, entriesObj);
    
    ld.put(SSVarU.author,  SSVarU.sss + SSStrU.colon + SSUri.class.getName());
    ld.put(SSVarU.target,  SSVarU.sss + SSStrU.colon + SSUri.class.getName());
    ld.put(SSVarU.label,   SSVarU.sss + SSStrU.colon + SSLabelStr.class.getName());
    
    return ld;
  }

  /*************** getters to allow for jason enconding ********************/
  public String getUri() throws Exception{
    return SSUri.toStrWithoutSlash(uri);
  }

  public String getLabel(){
    return SSLabelStr.toStr(label);
  }

  public String getAuthor() throws Exception{
    return SSUri.toStrWithoutSlash(author);
  }

  public String getTarget() throws Exception{
    return SSUri.toStrWithoutSlash(target);
  }

  public List<SSDiscEntry> getEntries(){
    return entries;
  }
}