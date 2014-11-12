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
package at.kc.tugraz.sss.video.datatypes.par;

import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.socialserver.utils.SSVarU;
import at.kc.tugraz.ss.datatypes.datatypes.SSTextComment;
import at.kc.tugraz.ss.datatypes.datatypes.entity.SSUri;
import at.kc.tugraz.ss.datatypes.datatypes.label.SSLabel;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;
import at.kc.tugraz.ss.serv.err.reg.SSServErrReg;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ApiModel(value = "videoAnnotationAdd request parameter")
public class SSVideoAnnotationAddPar extends SSServPar{
  
  @ApiModelProperty(
    required = true,
    value = "video to add annotation for")
  public SSUri               video        = null;
  
  @XmlElement
  public void setVideo(final String video) throws Exception{
    this.video = SSUri.get(video);
  }
  
  @XmlElement
  @ApiModelProperty(
    required = false,
    value = "time point the annotation is attached to the video")
  public Long               timePoint        = null;
  
  @XmlElement
  @ApiModelProperty(
    required = false,
    value = "x coordinate the annotation is attached to the video")
  public Float               x        = null;
  
  
  @XmlElement
  @ApiModelProperty(
    required = false,
    value = "y coordinate the annotation is attached to the video")
  public Float               y        = null;
  
  @ApiModelProperty(
    required = false,
    value = "name")
  public SSLabel               label        = null;
  
  @XmlElement
  public void setLabel(final String label) throws Exception{
    this.label = SSLabel.get(label);
  }
  
  @ApiModelProperty(
    required = false,
    value = "description")
  public SSTextComment               description        = null;
  
  @XmlElement
  public void setDescription(final String description) throws Exception{
    this.description = SSTextComment.get(description);
  }
  
  public SSVideoAnnotationAddPar(){}
  
  public SSVideoAnnotationAddPar(SSServPar par) throws Exception{
    
    super(par);
    
    try{
      
      if(pars != null){
        video               = (SSUri)          pars.get(SSVarU.video);
        timePoint           = (Long)           pars.get(SSVarU.timePoint);
        x                   = (Float)          pars.get(SSVarU.x);
        y                   = (Float)          pars.get(SSVarU.y);
        label               = (SSLabel)        pars.get(SSVarU.label);
        description         = (SSTextComment)  pars.get(SSVarU.description);
      }
      
      if(par.clientJSONObj != null){
        
        video               = SSUri.get(par.clientJSONObj.get(SSVarU.video).getTextValue());
        
        try{
          timePoint =  par.clientJSONObj.get(SSVarU.timePoint).getLongValue();
        }catch(Exception error){}
        
        try{
          x =  par.clientJSONObj.get(SSVarU.x).getNumberValue().floatValue();
        }catch(Exception error){}
        
        try{
          y =  par.clientJSONObj.get(SSVarU.y).getNumberValue().floatValue();
        }catch(Exception error){}
        
        try{
          label =  SSLabel.get(par.clientJSONObj.get(SSVarU.label).getTextValue());
        }catch(Exception error){}
        
        try{
          description =  SSTextComment.get(par.clientJSONObj.get(SSVarU.description).getTextValue());
        }catch(Exception error){}
      }
    }catch(Exception error){
      SSServErrReg.regErrThrow(error);
    }
  }
  
  /* json getters */
  
  public String getVideo(){
    return SSStrU.removeTrailingSlash(video);
  }
    
  public String getLabel(){
    return SSStrU.toStr(label);
  }
  
  public String getDescription(){
    return SSStrU.toStr(description);
  }
}