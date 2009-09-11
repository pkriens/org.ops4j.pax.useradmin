/*
 * Copyright 2009 Matthias Kuespert
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
package org.ops4j.pax.useradmin.itest.service;

import static org.ops4j.pax.exam.CoreOptions.*;

import org.junit.Assert;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.useradmin.service.UserAdminConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.useradmin.UserAdmin;

/**
 * Abstract base class for all integration tests.
 * 
 * @author Matthias Kuespert
 * @since 12.07.2009
 */
public abstract class UserAdminTestBase {

    /**
     * Change this to false to disable security
     */
    private static boolean m_doEnableSecurity = true;
    
    /**
     * Abstract method used to retrieve the <code>BundleContext</code>.
     * 
     * @return <code>BundleContext</code> used throughout the tests.
     */
    protected abstract BundleContext getBundleContext();

    /** Abstract method to retrieve the type of StorageProvider the bundle provides.
     * 
     * @return An identifying string value.
     */
    protected abstract String getProviderType();
    
    /**
     * Checks if security should be enabled when running the test.
     * 
     * @return True if security should be enabled in tests, false otherwise.
     */
    protected static boolean doEnableSecurity() {
        return m_doEnableSecurity;
    }
    
    /**
     * @return The basic OSGi framework configuration used to run the tests.
     */
    protected static Option getBasicFrameworkConfiguration() {
        return composite(mavenBundle().groupId("org.ops4j.pax.logging")
                                      .artifactId("pax-logging-api")
                                      .version("1.3.0").startLevel(1),
                         mavenBundle().groupId("org.ops4j.pax.logging")
                                      .artifactId("pax-logging-service")
                                      .version("1.3.0").startLevel(1),
                         mavenBundle().groupId("org.apache.felix")
                                      .artifactId("org.apache.felix.eventadmin")
                                      .version("1.0.0").startLevel(2),
                         mavenBundle().groupId("org.apache.felix")
                                      .artifactId("org.apache.felix.configadmin")
                                      .version("1.0.4").startLevel(2),
                         mavenBundle().groupId("org.ops4j.pax.useradmin")
                                      .artifactId("pax-useradmin-service")
                                      .version("0.0.1-SNAPSHOT")
                                      .startLevel(6));
    }

    /**
     * @return The <code>UserAdmin</code> service instance to test.
     */
    protected UserAdmin getUserAdmin() {
        BundleContext context = getBundleContext();
        try {
            for (ServiceReference reference : context.getServiceReferences(UserAdmin.class.getName(), null)) {
                String type = (String) reference.getProperty(UserAdminConstants.STORAGEPROVIDER_TYPE);
                if (null != type) {
                    if (type.equals(getProviderType())) {
                        return (UserAdmin) context.getService(reference);
                    }
                }
            }
        } catch (InvalidSyntaxException e) {
        }
        Assert.fail("No UserAdmin service found for provider " + getProviderType());
        return null;
    }
}
