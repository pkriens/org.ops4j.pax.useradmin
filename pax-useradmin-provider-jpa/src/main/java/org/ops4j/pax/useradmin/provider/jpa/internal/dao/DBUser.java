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
package org.ops4j.pax.useradmin.provider.jpa.internal.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.Table;

import org.osgi.service.useradmin.Role;

/**
 * A user is represendted by this DAO in the database
 */
@Entity(name = "osgi_service_useradmin_User")
@Table(name = "osgi_service_useradmin_User")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DBUser extends DBRole {

    public DBUser() {
        setType(Role.USER);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKey(name = "key")
    @CollectionTable(name = "osgi_service_useradmin_User_credentials", joinColumns = @JoinColumn(name = "cred_key_id"))
    private final Map<String, DBProperty> credentials = new HashMap<String, DBProperty>();

    /**
     * @return the current value of credentials
     */
    public Map<String, DBProperty> getCredentials() {
        return credentials;
    }
}
