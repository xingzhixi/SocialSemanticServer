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
 package at.kc.tugraz.ss.serv.db.datatypes.graph;

import at.kc.tugraz.ss.datatypes.datatypes.SSEntityA;
import at.kc.tugraz.ss.datatypes.datatypes.SSUri;

public class SSStatement{

  public final SSUri              subject;
  public final SSUri              predicate;
  public final SSEntityA          object;

  public static SSStatement get(
    final SSUri             subject,
    final SSUri             predicate, 
    final SSEntityA         object) throws Exception{
    
    return new SSStatement(subject, predicate, object);
  }
  
  private SSStatement(
    SSUri             subject,
    SSUri             predicate, 
    SSEntityA         object) throws Exception{

    this.subject   = subject;
    this.predicate = predicate;
    this.object    = object;  
  }
}