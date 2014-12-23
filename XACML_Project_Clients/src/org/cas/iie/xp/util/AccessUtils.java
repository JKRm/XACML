/*
 * JetS3t : Java S3 Toolkit
 * Project hosted at http://bitbucket.org/jmurty/jets3t/
 *
 * Copyright 2008 James Murty
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cas.iie.xp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jets3t.service.security.AWSCredentials;
import org.jets3t.service.security.GSCredentials;


public class AccessUtils {

    public static final String SAMPLES_PROPERTIES_NAME = "accesskey.properties";
    public static final String AWS_ACCESS_KEY_PROPERTY_NAME = "awsAccessKey";
    public static final String AWS_SECRET_KEY_PROPERTY_NAME = "awsSecretKey";
    public static final String GS_ACCESS_KEY_PROPERTY_NAME = "gsAccessKey";
    public static final String GS_SECRET_KEY_PROPERTY_NAME = "gsSecretKey";

    
    public static AWSCredentials loadAWSCredentials() throws IOException {
    	
        InputStream propertiesIS =
            //ClassLoader.getSystemResourceAsStream(SAMPLES_PROPERTIES_NAME);
        AccessUtils.class.getResourceAsStream(SAMPLES_PROPERTIES_NAME);

        if (propertiesIS == null) {
            throw new RuntimeException("Unable to load test properties file from classpath: "
                + SAMPLES_PROPERTIES_NAME);
        }

        Properties testProperties = new Properties();
        
        testProperties.load(propertiesIS);

        if (!testProperties.containsKey(AWS_ACCESS_KEY_PROPERTY_NAME)) {
            throw new RuntimeException(
                "Properties file '" + SAMPLES_PROPERTIES_NAME
                + "' does not contain required property: " + AWS_ACCESS_KEY_PROPERTY_NAME);
        }
        
        if (!testProperties.containsKey(AWS_SECRET_KEY_PROPERTY_NAME)) {
            throw new RuntimeException(
                "Properties file '" + SAMPLES_PROPERTIES_NAME
                + "' does not contain required property: " + AWS_SECRET_KEY_PROPERTY_NAME);
        }

        AWSCredentials awsCredentials = new AWSCredentials(
        		
            testProperties.getProperty(AWS_ACCESS_KEY_PROPERTY_NAME),
            
            testProperties.getProperty(AWS_SECRET_KEY_PROPERTY_NAME));

        return awsCredentials;
    }

   
    public static GSCredentials loadGSCredentials() throws IOException {
        InputStream propertiesIS =
        	AccessUtils.class.getResourceAsStream(SAMPLES_PROPERTIES_NAME);

        if (propertiesIS == null) {
            throw new RuntimeException("Unable to load test properties file from classpath: "
                + SAMPLES_PROPERTIES_NAME);
        }

        Properties testProperties = new Properties();
        testProperties.load(propertiesIS);

        if (!testProperties.containsKey(GS_ACCESS_KEY_PROPERTY_NAME)) {
            throw new RuntimeException(
                "Properties file '" + SAMPLES_PROPERTIES_NAME
                + "' does not contain required property: " + GS_ACCESS_KEY_PROPERTY_NAME);
        }
        if (!testProperties.containsKey(GS_SECRET_KEY_PROPERTY_NAME)) {
            throw new RuntimeException(
                "Properties file '" + SAMPLES_PROPERTIES_NAME
                + "' does not contain required property: " + GS_SECRET_KEY_PROPERTY_NAME);
        }

        GSCredentials gsCredentials = new GSCredentials(
            testProperties.getProperty(GS_ACCESS_KEY_PROPERTY_NAME),
            testProperties.getProperty(GS_SECRET_KEY_PROPERTY_NAME));

        return gsCredentials;
     }

}
