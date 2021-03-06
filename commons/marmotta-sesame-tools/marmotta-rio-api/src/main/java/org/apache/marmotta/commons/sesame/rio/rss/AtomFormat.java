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
package org.apache.marmotta.commons.sesame.rio.rss;

import org.openrdf.rio.RDFFormat;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Add file description here!
 * <p/>
 * Author: Sebastian Schaffert
 */
public class AtomFormat {

    public static final RDFFormat FORMAT = new RDFFormat(
            "Atom",
            Arrays.asList("application/atom+xml", "application/x-geoatom+xml"),
            Charset.forName("UTF-8"),
            Arrays.asList("atom","xml"),
            false,
            false
    );

}
