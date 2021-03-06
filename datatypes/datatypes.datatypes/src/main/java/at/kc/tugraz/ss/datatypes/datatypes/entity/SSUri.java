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
package at.kc.tugraz.ss.datatypes.datatypes.entity;

import at.kc.tugraz.socialserver.utils.SSEncodingU;
import at.kc.tugraz.socialserver.utils.SSLinkU;
import at.kc.tugraz.socialserver.utils.SSLogU;
import at.kc.tugraz.socialserver.utils.SSObjU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.datatypes.datatypes.SSEntity;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
//import org.apache.commons.httpclient.URIException;
//import org.apache.commons.httpclient.util.URIUtil;

public class SSUri extends SSEntityA{
  
  public static Boolean isURI(final String string) throws Exception{
    
//    URIUtil.encodeQuery(string)
    //    new URL(uriString); //import java.net.URL;
    try{
      if(string == null){
        return false;
      }
      
      try{
        new URL(string);
        java.net.URI.create (string);
        URLEncoder.encode   (string, SSEncodingU.utf8);
        
        if(string.length() > 250){
          SSLogU.warn("uri too long (> 250 chars) to be stored in sss");
          return false;
        }
        
      }catch(Exception error){
        return false;
      }
      
      return true;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  public static List<SSUri> get(
    final List<String> strings, 
    final String       uriPrefix) throws Exception{
    
    if(strings == null){
      throw new Exception("pars null");
    }
    
    final List<SSUri> uris = new ArrayList<>();
    
    for(String string : strings){
      uris.add(get(string, uriPrefix));
    }
    
    return uris;
  }
  
  public static SSUri get(
    final String string, 
    final String uriPrefix) throws Exception{
    
    String decodedURI;
    
    try{
      decodedURI = SSEncodingU.decode(string);
    }catch(Exception error){
      decodedURI = string;
    }
    
    if(isURI(decodedURI)){
      return get(decodedURI);
    }else{
      return get(uriPrefix + decodedURI);
    }
  }
  
  public static SSUri get(final String string) throws Exception{
    return new SSUri(string);
  }
  
  public static SSUri get(
    final SSUri  uri,
    final String append) throws Exception{
    
    if(SSObjU.isNull(uri, append)){
      throw new Exception("invalid uri " + uri + " " + append);
    }
    
    return new SSUri(uri.toString() + append);
  }
  
  public static List<SSUri> get(final List<String> strings) throws Exception{

    if(strings == null){
      throw new Exception("pars null");
    }
    
    final List<SSUri> result = new ArrayList<>();
    
    for(String string : strings){
      result.add(get(string));
    }
    
    return result;
  }
  
  public static List<SSUri> getFromEntitites(final List<? extends SSEntity> entities) throws Exception{

    if(entities == null){
      throw new Exception("pars null");
    }
    
    final List<SSUri> result = new ArrayList<>();
    
    for(SSEntity entity : entities){
      result.add(entity.id);
    }
    
    return result;
  }
  
  public static void addDistinctWithoutNull(
    final List<SSUri>     entities,  
    final SSUri           entity) throws Exception{
    
    try{
      
      if(entities == null){
        throw new Exception("pars null");
      }
      
      if(entity == null){
        return;
      }
      
      if(SSStrU.contains(entities, entity)){
        return;
      }
      
      entities.add(entity);
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  public static void addDistinctWithoutNull(
    final List<SSUri>  entities,
    final List<SSUri>  toAddEntities) throws Exception{
    
    try{
      
      if(entities == null){
        throw new Exception("pars null");
      }
      
      if(toAddEntities == null){
        return;
      }
      
      for(SSUri entity : toAddEntities){
        
        if(entity == null){
          continue;
        }
        
        if(!SSStrU.contains(entities, entity)){
          entities.add(entity);
        }
      }
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }

  public static List<SSUri> asListWithoutNullAndEmpty(final SSUri... entities){
   
    final List<SSUri> result = new ArrayList<>();
    
    if(entities == null){
      return result;
    }
    
    for(SSUri entity : entities){
      
      if(SSStrU.isEmpty(entity)){
        continue;
      }
      
      result.add(entity);
    }
    
    return result;
  }
  
  public static List<SSUri> asListWithoutNullAndEmpty(final List<SSUri> entities){
   
    final List<SSUri> result = new ArrayList<>();
    
    if(entities == null){
      return result;
    }
    
    for(SSUri entity : entities){
      
      if(SSStrU.isEmpty(entity)){
        continue;
      }
      
      result.add(entity);
    }
    
    return result;
  }

  private SSUri(final String string) throws Exception{
   
    super(SSStrU.addTrailingSlash(string));
    
    if(!isURI(val)){
      throw new Exception("invalid uri " + val);
    }
  }
  
  @Override
  public Object jsonLDDesc(){
    return SSLinkU.schemaOrgUrl;
  }
}

//public static List<SSUri> distinctWithoutNull(
//    final List<SSUri> uris) throws Exception{
//
//    try{
//      
//      if(uris == null){
//        throw new Exception("pars null");
//      }
//      
//      final List<SSUri> result = new ArrayList<>();
//      
//      for (SSUri uri : uris) {
//        
//        if(
//          uri != null &&
//          !result.contains(uri.toString())){
//          
//          result.add(uri);
//        }
//      }
//      
//      return result;
//    }catch(Exception error){
//      SSServErrReg.regErrThrow(error);
//      return null;
//    }
//  }

//  public URI createUncheckedURI(String uri) {
//
//    try {
//      return new URI(uri, false);
//
//    } catch (Exception e) {
//      return null;
//    }
//  }

//  public static List<SSUri> get(final Collection<String> uris) throws Exception{
//    
//    List<SSUri> result = new ArrayList<>();
//    
//    for (String uri : uris) {
//      result.add(get(uri));
//    }
//    
//    return result;
//  }