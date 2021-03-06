/*
 * Copyright 2009 OPS4J
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
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.pax.useradmin.service.spi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.cm.ConfigurationException;

/**
 * Static methods that are used internally as well as by SPI implementations.
 */
public final class UserAdminTools {

    private UserAdminTools() {
    }

    /**
     * Retrieves a property and throws an exception if not found.
     * 
     * @param properties
     *            The properties used for lookup.
     * @param name
     *            The name of the property to retrieve.
     * @return The value of the property.
     * @throws ConfigurationException
     *             If the property is not found in the given properties.
     * @throws IllegalArgumentException
     *             If the properties argument is null.
     */
    @Deprecated
    public static String getMandatoryProperty(Dictionary<String, ?> properties, String name) throws ConfigurationException {
        if (null == properties) {
            throw new IllegalArgumentException("getMandatoryProperty() argument 'properties' must not be null");
        }
        String value = (String) properties.get(name);
        if (null == value) {
            throw new ConfigurationException(name, "no value found for property '" + name + "' - please check the configuration");
        }
        return value;
    }

    @Deprecated
    public static String getOptionalProperty(Dictionary<String, ?> properties, String name, String defaultValue) {
        if (null == properties) {
            throw new IllegalArgumentException("getOptionalProperty() argument 'properties' must not be null");
        }
        String value = (String) properties.get(name);
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    public static String getMandatoryProperty(Map<String, ?> properties, String name) throws ConfigurationException {
        if (null == properties) {
            throw new IllegalArgumentException("getMandatoryProperty() argument 'properties' must not be null");
        }
        return getMandatoryProperty((Dictionary<String, ?>) new Hashtable<String, Object>(properties), name);
    }

    /**
     * Retrieves a property and returns a default value if not found.
     * 
     * @param properties
     *            The properties used for lookup.
     * @param name
     *            The name of the property to retrieve.
     * @param defaultValue
     *            The default value to return if the property is not found.
     * @return The value of the property or the given default value.
     * @throws IllegalArgumentException
     *             If the properties argument is null.
     */
    public static String getOptionalProperty(Map<String, ?> properties, String name, String defaultValue) {
        if (null == properties) {
            throw new IllegalArgumentException("getOptionalProperty() argument 'properties' must not be null");
        }
        return getOptionalProperty((Dictionary<String, ?>) new Hashtable<String, Object>(properties), name, defaultValue);
    }

    public static byte[] stringToBytes(String input) {
        if (input == null) {
            return null;
        }
        if (Charset.isSupported("UTF-8")) {
            try {
                return input.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                //this should never happen!
                throw new AssertionError("Encoding UTF-8 not found even if it was advertised as supported!");
            }
        } else {
            return input.getBytes(Charset.defaultCharset());
        }
    }

    public static String bytesToString(byte[] input) {
        if (input == null) {
            return null;
        }
        if (Charset.isSupported("UTF-8")) {
            try {
                return new String(input, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                //this should never happen!
                throw new AssertionError("Encoding UTF-8 not found even if it was advertised as supported!");
            }
        } else {
            return new String(input, Charset.defaultCharset());
        }
    }
}
