package me.smartstart.core.service

import me.smartstart.core.domain.Permission
import me.smartstart.core.domain.User

interface UserService extends GenericService<User, Long> {

    User findUserByUsername(String username)

    Permission findPermissionByName(String name)
}