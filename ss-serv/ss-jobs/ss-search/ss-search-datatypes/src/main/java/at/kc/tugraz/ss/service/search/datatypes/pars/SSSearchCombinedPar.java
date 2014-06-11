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
 package at.kc.tugraz.ss.service.search.datatypes.pars;

import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import java.util.ArrayList;
import java.util.List;

public class SSSearchCombinedPar extends SSServPar{
  
  public List<String> keywords              = new ArrayList<String>();
  public List<SSUri>  entities              = new ArrayList<SSUri>();
  public Boolean      onlySubEntities       = null;
  public Boolean      includeTags           = null;
  public Boolean      includeTextualContent = null;
  public Boolean      includeLabel          = null;
  public Boolean      includeDescription    = null;
  public Boolean      includeMIs            = null;
  
  public SSSearchCombinedPar(SSServPar par) throws Exception{
    
    super(par);
    
    try{
      
      if(pars != null){
        keywords                    = (List<String>) pars.get(SSVarU.keywords);
        entities                    = (List<SSUri>)  pars.get(SSVarU.entities);
        onlySubEntities             = (Boolean)      pars.get(SSVarU.onlySubEntities);
        includeTags                 = (Boolean)      pars.get(SSVarU.includeTags);
        includeTextualContent       = (Boolean)      pars.get(SSVarU.includeTextualContent);
        includeLabel                = (Boolean)      pars.get(SSVarU.includeLabel);
        includeDescription          = (Boolean)      pars.get(SSVarU.includeDescription);
        includeMIs                  = (Boolean)      pars.get(SSVarU.includeMIs);
      }
      
      if(clientPars != null){
        keywords                 = SSStrU.splitDistinctWithoutEmptyAndNull(clientPars.get(SSVarU.keywords), SSStrU.comma);
        
        try{
          entities               = SSUri.get(SSStrU.splitDistinctWithoutEmptyAndNull(clientPars.get(SSVarU.entities), SSStrU.comma));
        }catch(Exception error){}
        
        try{
          onlySubEntities        = Boolean.valueOf(clientPars.get(SSVarU.onlySubEntities));
        }catch(Exception error){}
        
        try{
          includeTags            = Boolean.valueOf(clientPars.get(SSVarU.includeTags));
        }catch(Exception error){}
        
        try{
          includeTextualContent  = Boolean.valueOf(clientPars.get(SSVarU.includeTextualContent));
        }catch(Exception error){}
        
        try{
          includeLabel           = Boolean.valueOf(clientPars.get(SSVarU.includeLabel));
        }catch(Exception error){}
        
        try{
          includeDescription     = Boolean.valueOf(clientPars.get(SSVarU.includeDescription));
        }catch(Exception error){}
        
        try{
          includeMIs             = Boolean.valueOf(clientPars.get(SSVarU.includeMIs));
        }catch(Exception error){}
      }
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
}