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
package at.kc.tugraz.ss.test.app;

import at.kc.tugraz.sss.app.conf.SSAppConf;
import at.kc.tugraz.sss.app.serv.SSAppServ;

public class SSAppTester extends Thread{

  @Override
  public void run(){
    
    SSAppConf appConf = (SSAppConf) SSAppServ.inst.servConf;
    
    if(!appConf.executeOpAtStartUp){
      return;
    }      
    
    switch(appConf.op){
      case appsGet: new Thread(new SSAppsGetTest(appConf)).start(); break;
      case appAdd:  new Thread(new SSAppAddTest (appConf)).start(); break;
    }
  }  
}
