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
package at.kc.tugraz.ss.serv.jobs.evernote.datatypes.par;

import at.kc.tugraz.ss.datatypes.datatypes.SSUri;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;

public class SSEvernoteInfo {

  public UserStoreClient userStore      = null;
  public NoteStoreClient noteStore      = null;
  public SSUri           shardUri       = null;
  
  public static SSEvernoteInfo get(UserStoreClient userStore, NoteStoreClient noteStore, SSUri shardUri){
    return new SSEvernoteInfo(userStore, noteStore, shardUri);
  }
  
  private SSEvernoteInfo(UserStoreClient userStore, NoteStoreClient noteStore, SSUri shardUri){
    this.userStore = userStore;
    this.noteStore = noteStore;
    this.shardUri  = shardUri;
  }
}