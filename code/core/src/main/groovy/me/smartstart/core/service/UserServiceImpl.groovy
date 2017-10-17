package me.smartstart.core.service

import me.smartstart.core.domain.Permission
import me.smartstart.core.domain.User
import me.smartstart.core.repository.PermissionRepository
import me.smartstart.core.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    PermissionRepository permissionRepository

    @Override
    User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
    }

    @Override
    Permission findPermissionByName(String name) {
        return permissionRepository.findByName(name)
    }

    @Override
    User create(User user) {
        return null
    }

    @Override
    User get(Long aLong) {
        return null
    }

    @Override
    User update(User user) {
        return null
    }

    @Override
    User delete(Long aLong) {
        return null
    }

    @Override
    Long countAll() {
        return null
    }

    @Override
    List<User> getAll() {
        return null
    }
}
