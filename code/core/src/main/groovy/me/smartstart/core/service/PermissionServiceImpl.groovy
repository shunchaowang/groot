package me.smartstart.core.service

import me.smartstart.core.domain.Permission
import org.springframework.stereotype.Service

@Service
class PermissionServiceImpl implements PermissionService {

    @Override
    Permission findByName(String name) {
        return null
    }
}
