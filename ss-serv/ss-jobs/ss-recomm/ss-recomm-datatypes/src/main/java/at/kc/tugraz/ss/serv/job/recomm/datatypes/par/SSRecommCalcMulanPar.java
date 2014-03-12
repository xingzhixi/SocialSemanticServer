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

package at.kc.tugraz.ss.serv.job.recomm.datatypes.par;

import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;

public class SSRecommCalcMulanPar extends SSServPar{

  public String  sampleName  = null;
  public Integer sampleCount = null;
  
  public SSRecommCalcMulanPar(SSServPar par) throws Exception{
    
    super(par);
    
    try{
      if(pars != null){
        this.sampleName  = (String)  pars.get(SSVarU.sampleName);
        this.sampleCount = (Integer) pars.get(SSVarU.sampleCount);
      }
      
      if(clientPars != null){
        this.sampleName  = clientPars.get  (SSVarU.sampleName);
        this.sampleCount = Integer.valueOf (clientPars.get(SSVarU.sampleCount));
      }
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
}