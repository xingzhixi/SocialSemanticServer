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
 package at.kc.tugraz.ss.serv.modeling.ue.api;

import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import at.kc.tugraz.ss.serv.modeling.ue.datatypes.SSModelUERelation;
import at.kc.tugraz.ss.serv.modeling.ue.datatypes.SSModelUETopicScore;
import at.kc.tugraz.ss.serv.modeling.ue.datatypes.rets.SSModelUEResourceDetailsRet;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.datatypes.datatypes.SSTagLabel;
import java.util.List;

public interface SSModelUEServerI {
  
  public SSModelUEResourceDetailsRet        modelUEResourceDetails         (final SSServPar parA)     throws Exception;
  public List<SSUri>                        modelUERelatedPersons          (final SSServPar parA)     throws Exception;
  public List<String>                       modelUEMIsForEntityGet         (final SSServPar parA)     throws Exception;
  
  public void                               modelUEUpdate                  (final SSServPar parA)     throws Exception;
	public List<SSUri>                        modelUEEntitiesForMiGet        (final SSServPar parA)     throws Exception;
	public List<String>                       modelUEResourcesAll            (final SSServPar parA)     throws Exception;
	public List<SSUri>                        modelUEEditors                 (final SSServPar parA)     throws Exception;
	public SSUri                              modelUEResourceRecent          (final SSServPar parA)     throws Exception;
	public SSTagLabel                        modelUETopicRecent             (final SSServPar parA)     throws Exception;
	
	public List<SSUri>                        modelUEResourcesContributed    (final SSServPar parA)     throws Exception;
	public List<SSModelUETopicScore>          modelUETopicScores             (final SSServPar parA)     throws Exception;
	public List<SSModelUERelation>            modelUEModelRelations          (final SSServPar parA)     throws Exception;
  
//  public List<SSUri>                        modelUEParentResources     (SSServerPar par)     throws Exception;
//  public List<SSUri>                        getOwnedSharedColls               (SSServerPar par)     throws Exception;
//	public List<SSUri>                        getOwnedFollowedColls             (SSServerPar par)     throws Exception;
//	public List<SSUri>                        getContributedDiscs               (SSServerPar par)     throws Exception;
}