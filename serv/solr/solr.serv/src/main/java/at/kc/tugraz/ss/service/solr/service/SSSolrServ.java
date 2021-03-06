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
 package at.kc.tugraz.ss.service.solr.service;

import at.kc.tugraz.ss.conf.api.SSCoreConfA;
import at.kc.tugraz.ss.serv.serv.api.SSServA;
import at.kc.tugraz.ss.serv.serv.api.SSServImplA;
import at.kc.tugraz.ss.service.filerepo.conf.SSFileRepoConf;
import at.kc.tugraz.ss.service.solr.api.SSSolrClientI;
import at.kc.tugraz.ss.service.solr.api.SSSolrServerI;
import at.kc.tugraz.ss.service.solr.impl.*;
import java.util.List;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;

public class SSSolrServ extends SSServA{
  
  public    static final SSServA              inst       = new SSSolrServ(SSSolrClientI.class, SSSolrServerI.class);
  protected static ConcurrentUpdateSolrServer solrServer = null;
  
  protected SSSolrServ(
    final Class servImplClientInteraceClass, 
    final Class servImplServerInteraceClass) {
    
    super(servImplClientInteraceClass, servImplServerInteraceClass);
  }
  
  @Override
  protected SSServImplA createServImplForThread() throws Exception{
    return new SSSolrImpl((SSFileRepoConf)servConf, solrServer);
  }

  @Override
  public void initServ() throws Exception{
     
    if(!servConf.use){
      return;
    }
    
    solrServer = new ConcurrentUpdateSolrServer(((SSFileRepoConf)servConf).getPath(), 1, 10);
  }
  
  @Override
  public SSCoreConfA getConfForCloudDeployment(
    final SSCoreConfA coreConfA, 
    final List<Class> configuredServs) throws Exception{
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void schedule() throws Exception{
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}