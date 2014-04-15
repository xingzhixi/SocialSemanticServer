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
package at.kc.tugraz.ss.serv.datatypes.entity.api;

import at.kc.tugraz.ss.adapter.socket.datatypes.SSSocketCon;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;

public interface SSEntityClientI {

  public void entityTypeGet                            (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityDescGet                            (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityLabelGet                           (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityLabelSet                           (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityUserDirectlyAdjoinedEntitiesRemove (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  
  public void entityUserCircleCreate                   (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityUserEntitiesToCircleAdd            (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityUserUsersToCircleAdd               (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
  public void entityUserPublicSet                      (final SSSocketCon sSCon, final SSServPar parA) throws Exception;
}
