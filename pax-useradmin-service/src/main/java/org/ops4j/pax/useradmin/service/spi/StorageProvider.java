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

import java.util.Collection;
import java.util.Map;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.useradmin.Group;
import org.osgi.service.useradmin.Role;
import org.osgi.service.useradmin.User;
import org.osgi.service.useradmin.UserAdmin;

/**
 * The StorageProvider interface defines the methods needed by a UserAdmin
 * implementation to maintain persistent UserAdmin data.
 */
public interface StorageProvider {

    /**
     * Create a new user with the given name. The user initially has no
     * properties or credentials assigned.
     * 
     * @see UserAdmin#createRole(String, int)
     * @param factory
     *            The <code>UserAdminFactory</code> used to create the
     *            implementation object.
     * @param name
     *            The user name.
     * @return An object implementing the <code>User</code> interface or null if
     *         a role with the given name already exists.
     * @throws StorageException
     *             if the user could not be created
     */
    User createUser(UserAdminFactory factory, String name) throws StorageException;

    /**
     * Create a new group with the given name. The group initially has no
     * properties or credentials assigned.
     * 
     * @see UserAdmin#createRole(String, int)
     * @param factory
     *            The <code>UserAdminFactory</code> used to create the
     *            implementation object.
     * @param name
     *            The group name.
     * @return An object implementing the <code>Group</code> interface or null
     *         if a role with the given name already exists.
     * @throws StorageException
     *             if the user could not be created
     */
    Group createGroup(UserAdminFactory factory, String name) throws StorageException;

    /**
     * Deletes the role with the given name. The role is also removed from all
     * groups it is a member of.
     * 
     * @see UserAdmin#removeRole(String)
     * @param role
     *            The <code>Role</code> to delete.
     * @return True if the role could be deleted.
     * @throws StorageException
     *             if an error occured while storing the role
     */
    boolean deleteRole(Role role) throws StorageException;

    // group management

    /**
     * Retrieve basic members of the given group. Eventually creates new Role
     * objects via the given factory.
     * 
     * @see Group#getMembers()
     * @param factory
     *            The <code>UserAdminFactory</code> used to create member roles.
     * @param group
     *            The <code>Group</code> whose members are retrieved.
     * @return A collection of <code>Role</code> objects that are basic members
     *         of the given group.
     */
    Collection<Role> getMembers(UserAdminFactory factory, Group group) throws StorageException;

    /**
     * Retrieve required members of the given group. Eventually creates new Role
     * objects via the given factory.
     * 
     * @see Group#getRequiredMembers()
     * @param factory
     *            The <code>UserAdminFactory</code> used to create member roles.
     * @param group
     *            The <code>Group</code> whose members are retrieved.
     * @return A collection of <code>Role</code> objects that are required
     *         members of the given group.
     */
    Collection<Role> getRequiredMembers(UserAdminFactory factory, Group group) throws StorageException;

    /**
     * Adds a role as a basic member to a group.
     * 
     * @see Group#addMember(Role)
     * @param group
     *            The <code>Group</code> to add the <code>Role</code> as basic
     *            member.
     * @param role
     *            The <code>Role</code> to add.
     * @return True if the given role was added - false otherwise.
     */
    boolean addMember(Group group, Role role) throws StorageException;

    /**
     * Adds a role as a required member to a group.
     * 
     * @see Group#addRequiredMember(Role)
     * @param group
     *            The <code>Group</code> to add the <code>Role</code> as
     *            required member.
     * @param role
     *            The <code>Role</code> to add.
     * @return True if the given role was added - false otherwise.
     */
    boolean addRequiredMember(Group group, Role role) throws StorageException;

    /**
     * Removes a member from the given group.
     * 
     * @see Group#removeMember(Role)
     * @param group The group the member should be removed from.
     * @param role The role to be removed.
     * @return true if the removal was successful, false if the member could not be removed for semantic reasons.
     */
    boolean removeMember(Group group, Role role) throws StorageException;

    // property management

    /**
     * Sets a <code>String</code> attribute to a role.
     * 
     * @param role
     *            The <code>Role</code> to set the attribute to.
     * @param key
     *            The key of the attribute.
     * @param value
     *            The value of the attribute.
     */
    void setRoleAttribute(Role role, String key, Object value) throws StorageException;

    /**
     * Removes an attribute from a role.
     * 
     * @param role
     *            The <code>Role</code> to remove the attribute from.
     * @param key
     *            The key of the attribute.
     */
    void removeRoleAttribute(Role role, String key) throws StorageException;

    /**
     * Removes all attributes from the given role.
     * 
     * @param role
     *            The <code>Role</code> to remove the attribute(s) from.
     */
    void clearRoleAttributes(Role role) throws StorageException;

    // credential management

    /**
     * @return the provider that gives acces to credential information
     */
    CredentialProvider getCredentialProvider();

    // role getters & finders

    /**
     * Returns the role with the given name.
     * 
     * @see UserAdmin#getRole(String)
     * @param factory
     *            The <code>UserAdminFactory</code> used to eventually create
     *            the implementation object.
     * @param name
     *            The role to find.
     * @return A <code>Role</code> implementation.
     */
    Role getRole(UserAdminFactory factory, String name) throws StorageException;

    /**
     * Retrieves the user with the given attributes.
     * 
     * @see UserAdmin#getUser(String, String)
     * @param factory
     *            The <code>UserAdminFactory</code> used to eventually create
     *            the implementation object.
     * @param key
     *            The attribute key to search for.
     * @param value
     *            The attribute value to search for.
     * @return The <code>User</code> object matching the query.
     */
    User getUser(UserAdminFactory factory, String key, String value) throws StorageException;

    /**
     * Returns the roles that match the given filter.
     * 
     * @see UserAdmin#getRoles(String)
     * @param factory
     *            The <code>UserAdminFactory</code> used to eventually create
     *            the implementation object.
     * @param filter Search filter to use or finding the roles.
     * @return A Collection of Roles that are matching the filter.
     */
    Collection<Role> findRoles(UserAdminFactory factory, String filter) throws StorageException;

    /**
     * Called whenever a configuration is detected for this provider
     * 
     * @param properties New configuration properties for the StorageProvider
     * @throws ConfigurationException If the configuration provided is incosistent, incomplete or in any other way can not be used.
     */
    void configurationUpdated(Map<String, ?> properties) throws ConfigurationException;
}
