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
 package at.kc.tugraz.ss.service.filerepo.api;

import at.kc.tugraz.ss.adapter.socket.datatypes.SSSocketCon;
import at.kc.tugraz.ss.serv.datatypes.SSServPar;

public interface SSFileRepoClientI{
  
  public void fileDownload              (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileUpload                (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileExtGet                (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileThumbGet              (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileReplace               (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileCanWrite              (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileSetReaderOrWriter     (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileRemoveReaderOrWriter  (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileWritingMinutesLeft    (final SSSocketCon sSCon, final SSServPar par) throws Exception;
  public void fileUserFileWrites        (final SSSocketCon sSCon, final SSServPar par) throws Exception;
}