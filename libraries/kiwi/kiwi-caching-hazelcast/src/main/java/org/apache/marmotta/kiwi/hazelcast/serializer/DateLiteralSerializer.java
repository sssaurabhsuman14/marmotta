/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.marmotta.kiwi.hazelcast.serializer;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.apache.marmotta.kiwi.model.rdf.KiWiDateLiteral;
import org.apache.marmotta.kiwi.model.rdf.KiWiUriResource;

import java.io.IOException;
import java.util.Date;

/**
 * Add file description here!
 *
 * @author Sebastian Schaffert (sschaffert@apache.org)
 */
public class DateLiteralSerializer implements StreamSerializer<KiWiDateLiteral> {


    @Override
    public int getTypeId() {
        return ExternalizerIds.DATE_LITERAL;
    }

    @Override
    public void write(ObjectDataOutput output, KiWiDateLiteral object) throws IOException {
        output.writeLong(object.getId());
        output.writeLong(object.getDateContent().getTime());
        output.writeObject(object.getDatatype());

        output.writeLong(object.getCreated().getTime());

    }

    @Override
    public KiWiDateLiteral read(ObjectDataInput input) throws IOException {
        long id = input.readLong();
        Date content = new Date(input.readLong());

        KiWiUriResource dtype = (KiWiUriResource) input.readObject();

        Date created = new Date(input.readLong());

        KiWiDateLiteral r = new KiWiDateLiteral(content, dtype, created);
        r.setId(id);

        return r;
    }

    @Override
    public void destroy() {

    }
}
