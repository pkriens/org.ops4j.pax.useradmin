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

package org.ops4j.pax.useradmin.command.spi;

import java.util.Collection;
import java.util.Map;

import org.ops4j.pax.useradmin.command.CommandException;
import org.osgi.service.useradmin.Role;

/**
 * Interface which abstracts writing UserAdmin data.
 */
public interface UserAdminDataWriter {

    /**
     * Closes this writer. Writers are initially open and may relay some or all
     * data-storage actions to the close() call.
     * 
     * Writers cannot be re-opened.
     */
    void close()
            throws CommandException;

    /**
     * Creates a role. Changes to the returned object may not be synchronized to
     * the underlying storage.
     */
    Role createRole(int type, String name, Map<String, Object> properties, Map<String, Object> credentials)
            throws CommandException;

    /**
     * Adds members to a role.
     */
    void addMembers(Role role, Collection<String> basicMembers, Collection<String> requiredMembers)
            throws CommandException;
}
