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
 package at.kc.tugraz.ss.service.search.service;

import at.kc.tugraz.socialserver.utils.SSDateU;
import at.kc.tugraz.ss.conf.api.SSCoreConfA;
import at.kc.tugraz.ss.serv.serv.api.SSServA;
import at.kc.tugraz.ss.serv.serv.api.SSServImplA;
import at.kc.tugraz.ss.service.search.api.SSSearchClientI;
import at.kc.tugraz.ss.service.search.api.SSSearchServerI;
import at.kc.tugraz.ss.service.search.impl.SSSearchImpl;
import at.kc.tugraz.ss.service.search.service.task.SSSearchResultPagesCacheCleanerTask;
import java.util.List;

public class SSSearchServ extends SSServA{
  
  public static final SSSearchServ  inst = new SSSearchServ(SSSearchClientI.class, SSSearchServerI.class);
  
  protected SSSearchServ(
    final Class servImplClientInteraceClass, 
    final Class servImplServerInteraceClass){
    
    super(servImplClientInteraceClass, servImplServerInteraceClass);
  }
  
  @Override
  protected SSServImplA createServImplForThread() throws Exception{
    return new SSSearchImpl(servConf);
  }

  @Override
  public void initServ() throws Exception{
  }
  
  public void schedule() throws Exception{
    
    if(!servConf.use){
      return;
    }
    
    SSDateU.scheduleAtFixedRate(
      new SSSearchResultPagesCacheCleanerTask(),
      SSDateU.getDatePlusMinutes(5),
      5 * SSDateU.minuteInMilliSeconds);
  }
  
  @Override
  public SSCoreConfA getConfForCloudDeployment(
    final SSCoreConfA coreConfA, 
    final List<Class> configuredServs) throws Exception{
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}