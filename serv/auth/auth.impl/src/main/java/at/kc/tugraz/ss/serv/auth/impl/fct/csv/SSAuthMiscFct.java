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
package at.kc.tugraz.ss.serv.auth.impl.fct.csv;

import at.kc.tugraz.socialserver.utils.SSEncodingU;
import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.datatypes.datatypes.label.SSLabel;
import at.kc.tugraz.ss.serv.auth.impl.fct.sql.SSAuthSQLFct;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import java.security.MessageDigest;
import sss.serv.err.datatypes.SSErr;
import sss.serv.err.datatypes.SSErrE;

public class SSAuthMiscFct{
  
  public static String genKey(
    final String email, 
    final String password) throws Exception{

    final String toDigest = email + password;
    
    try{
      if(SSStrU.isEmpty(toDigest)){
        throw new Exception("to digest string not valid");
      }
      
      final MessageDigest digest = MessageDigest.getInstance(SSEncodingU.md5);
      
      byte[] hash = digest.digest(toDigest.getBytes());

      //converting byte array to Hexadecimal String
      final StringBuilder sb = new StringBuilder(2 * hash.length);
      
      for(byte b : hash){
        sb.append(String.format("%02x", b&0xff));
      }
      
      return sb.toString();
      
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
  
  public static String checkAndGetKey(
    final SSAuthSQLFct sqlFct,
    final SSUri        userUri,
    final String       email,
    final String       pass) throws Exception{
    
    try{
      
      if(!sqlFct.hasKey(userUri)){
        throw new SSErr(SSErrE.userIsNotRegistered);
      }
       
      final String key = sqlFct.getKey(userUri);
      
      if(!key.equals(genKey(email, pass))){
        throw new SSErr(SSErrE.userKeyWrong);
      }
      
      return key;
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
      return null;
    }
  }
}

// public static String checkPasswordAndGetUserKey(
//    final Map<String, String> passwordPerUser, 
//    final Map<String, String> keyPerUser, 
//    final SSLabel             userLabel,
//    final String              password) throws Exception{
//    
//    try{
//      final String userStr = SSStrU.toStr(userLabel);
//      
//      if(
//        !SSStrU.equals(password, passwordPerUser.get(userStr)) ||
//        keyPerUser.get(userStr) == null){
//        throw new Exception("user not registered");
//      }
//      
//      return keyPerUser.get(userStr);
//    }catch(Exception error){
//      SSServErrReg.regErrThrow(error);
//      return null;
//    }
//  }