package me.smartstart.core.service

import me.smartstart.core.domain.Permission

interface PermissionService {

    Permission findByName(String name)
}
