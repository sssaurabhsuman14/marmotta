/**
 * Copyright (C) 2013 Salzburg Research.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.newmedialab.lmf.versioning.api;

import at.newmedialab.lmf.versioning.io.VersionSerializer;
import org.apache.marmotta.commons.http.ContentType;

import java.io.IOException;
import java.util.List;

/**
 * Manages serialisation writers for Version objects
 * <p/>
 * Author: Thomas Kurz (tkurz@apache.org)
 */
public interface VersionSerializerService {

    /**
     * returns a serializer for Version objects on a given ContentType
     * @param type a list of mimetype (from Accept header)
     * @return a serializer
     */
    public VersionSerializer getSerializer(List<ContentType> type) throws IOException;

}