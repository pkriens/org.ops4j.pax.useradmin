/**
 * Copyright 2013 OPS4J
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
package org.ops4j.pax.useradmin.provider.jpa;

/**
 * Defines some service constants
 */
public interface ConfigurationConstants {

    public static final String SERVICE_PID          = "org.ops4j.pax.useradmin.jpa";

    public static final String TRACKED_SERVICE_ID   = "org.ops4j.pax.useradmin.tracked.service_id";

    public static final String STORAGEPROVIDER_TYPE = "Java Persistence API";

    public static final String PUNIT_NAME           = "org.ops4j.pax.useradmin.jpa.punit";

}