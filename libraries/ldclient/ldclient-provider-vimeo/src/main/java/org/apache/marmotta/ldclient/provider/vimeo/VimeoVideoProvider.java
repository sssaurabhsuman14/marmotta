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
package org.apache.marmotta.ldclient.provider.vimeo;

import at.newmedialab.sesame.commons.model.Namespaces;
import com.google.common.collect.ImmutableList;
import org.apache.marmotta.ldclient.api.endpoint.Endpoint;
import org.apache.marmotta.ldclient.api.provider.DataProvider;
import org.apache.marmotta.ldclient.provider.vimeo.mapping.VimeoDateMapper;
import org.apache.marmotta.ldclient.provider.xml.AbstractXMLDataProvider;
import org.apache.marmotta.ldclient.provider.xml.mapping.CommaSeparatedMapper;
import org.apache.marmotta.ldclient.provider.xml.mapping.XPathLiteralMapper;
import org.apache.marmotta.ldclient.provider.xml.mapping.XPathURIMapper;
import org.apache.marmotta.ldclient.provider.xml.mapping.XPathValueMapper;
import org.openrdf.model.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A data provider that allows to wrap Vimeo Videos.
 * <p/>
 * Author: Sebastian Schaffert
 */
public class VimeoVideoProvider extends AbstractXMLDataProvider implements DataProvider {

    private static final String NS_MEDIA = "http://www.w3.org/ns/ma-ont#";

    private static Map<String,XPathValueMapper> mediaOntMappings = new HashMap<String, XPathValueMapper>();
    static {
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#title",        new XPathLiteralMapper("/videos/video/title"));
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#locator",      new XPathLiteralMapper("/videos/video/url","anyURI")); // URI
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#hasCreator",   new XPathURIMapper("/videos/video/user_url"));               // URI
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#hasPublisher", new XPathURIMapper("/videos/video/user_url"));               // URI
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#date",         new VimeoDateMapper("/videos/video/upload_date"));
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#description",  new XPathLiteralMapper("/videos/video/description"));
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#hasKeyword",   new CommaSeparatedMapper("/videos/video/tags"));
        mediaOntMappings.put("http://www.w3.org/ns/ma-ont#duration",     new XPathLiteralMapper("/videos/video/duration","integer"));
        mediaOntMappings.put("http://xmlns.com/foaf/0.1/thumbnail",      new XPathLiteralMapper("/videos/video/thumbnail_large","anyURI"));
        mediaOntMappings.put("http://rdfs.org/sioc/ns#num_views",        new XPathLiteralMapper("/videos/video/stats_number_of_plays","integer"));
        mediaOntMappings.put("http://rdfs.org/sioc/ns#num_replies",      new XPathLiteralMapper("/videos/video/stats_number_of_comments","integer"));
    }


    private static Logger log = LoggerFactory.getLogger(VimeoVideoProvider.class);


    /**
     * Return the name of this data provider. To be used e.g. in the configuration and in log messages.
     *
     * @return
     */
    @Override
    public String getName() {
        return "Vimeo Video";
    }

    /**
     * Return the list of mime types accepted by this data provider.
     *
     * @return
     */
    @Override
    public String[] listMimeTypes() {
        return new String[] { "application/xml"};
    }

    /**
     * Build the URL to use to call the webservice in order to retrieve the data for the resource passed as argument.
     * In many cases, this will just return the URI of the resource (e.g. Linked Data), but there might be data providers
     * that use different means for accessing the data for a resource, e.g. SPARQL or a Cache.
     *
     *
     * @param resource
     * @param endpoint endpoint configuration for the data provider (optional)
     * @return
     */
    @Override
    public List<String> buildRequestUrl(String resource, Endpoint endpoint) {
        if(resource.startsWith("http://vimeo.com/")) {
            String video_id = resource.substring("http://vimeo.com/".length());
            String url = "http://vimeo.com/api/v2/video/" + video_id + ".xml";
            return Collections.singletonList(url);
        } else
            throw new RuntimeException("invalid Vimeo URI: "+resource);
    }


    /**
     * Return a mapping table mapping from RDF properties to XPath Value Mappers. Each entry in the map is evaluated
     * in turn; in case the XPath expression yields a result, the property is added for the processed resource.
     *
     * @return
     * @param requestUrl
     */
    @Override
    protected Map<String, XPathValueMapper> getXPathMappings(String requestUrl) {
        return mediaOntMappings;
    }

    /**
     * Return a list of URIs that should be added as types for each processed resource.
     *
     * @return
     * @param resource
     */
    @Override
    protected List<String> getTypes(URI resource) {
        return ImmutableList.of(NS_MEDIA + "MediaResource", NS_MEDIA + "VideoTrack", Namespaces.NS_LMF_TYPES + "VimeoVideo");
    }
}