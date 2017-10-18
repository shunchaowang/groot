package me.smartstart.core.service

import me.smartstart.core.domain.Permission
import me.smartstart.core.domain.Role
import me.smartstart.core.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * The user management service is responsible for all user related apis, its scope includes user, role and permission.
 * This is an encapsulation layer to operate user.
 */
interface UserService {

    // User apis
    User saveUser(User user)

    User updateUser(User user)

    User activateUser(long id)

    User deactivateUser(long id)

    Long countAllUser()

    User getUser(long id)

    User findUserByUsername(String username)

    Page<User> findUsers(Pageable pageable)

    // Permission apis
    Permission getPermission(long id)

    Permission findPermissionByName(String name)

    Page<Permission> findPermissions(Pageable pageable)

    // Role apis

    Role getRole(long id)

    Role findRoleByName(String name)

    Page<Role> findRoles(Pageable pageable)
}