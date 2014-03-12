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
package at.kc.tugraz.ss.serv.jobs.evernote.impl.helper;

import at.kc.tugraz.socialserver.utils.SSStrU;
import at.kc.tugraz.ss.datatypes.datatypes.SSLabelStr;
import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import com.evernote.edam.type.LinkedNotebook;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.Resource;

public class SSEvernoteLabelHelper {
  
  public SSEvernoteLabelHelper() {
  }
  
  public SSLabelStr getNormalOrSharedNotebookLabel(SSUri notebookUri, Notebook notebook) throws Exception{
    
    try{
      return SSLabelStr.get(notebook.getName());
    }catch(Exception error){
      return getDefaultLabel(notebookUri);
    }
  }
  
  public SSLabelStr getLinkedNotebookLabel(LinkedNotebook linkedNotebook, SSUri notebookUri) throws Exception {
    
    try{
      return SSLabelStr.get(linkedNotebook.getShareName());
    }catch(Exception error){
      return getDefaultLabel(notebookUri);
    }
  }
  
  public SSLabelStr getLinkedNoteLabel(Note note, SSUri noteUri) throws Exception {
    
    try{
      return SSLabelStr.get(note.getTitle());
    }catch(Exception error){
      return getDefaultLabel(noteUri);
    }
  }
  
  public SSLabelStr getNoteLabel(Note note, SSUri noteUri) throws Exception {
    
    try{
      return SSLabelStr.get(note.getTitle());
    }catch(Exception error){
      return getDefaultLabel(noteUri);
    }
  }
  
  public SSLabelStr getResourceLabel(Resource resource, SSUri resourceUri) throws Exception{
    
    if(resource == null){
      return null;
    }
    
    if(
      resource.getAttributes() == null ||
      SSStrU.isEmpty(resource.getAttributes().getFileName())){
      return getDefaultLabel(resourceUri);
    }
    
    return SSLabelStr.get(resource.getAttributes().getFileName());
  }
  
  private SSLabelStr getDefaultLabel(SSUri noteUri) throws Exception{
          
    return SSLabelStr.get(SSStrU.empty);

//    if(noteUri ==  null){
//      return null;
//    }
//    
//    return SSLabelStr.get(SSStrU.toString(noteUri));
  }
}
