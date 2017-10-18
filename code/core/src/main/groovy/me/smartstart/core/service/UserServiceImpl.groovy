package me.smartstart.core.service

import me.smartstart.core.domain.Permission
import me.smartstart.core.domain.User
import me.smartstart.core.repository.PermissionRepository
import me.smartstart.core.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.awt.print.Pageable

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    PermissionRepository permissionRepository

    @Override
    @Transactional(readOnly = true)
    User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
    }

    @Override
    @Transactional(readOnly = true)
    Permission findPermissionByName(String name) {
        return permissionRepository.findByName(name)
    }

    @Override
    @Transactional
    User create(User user) {
        return null
    }

    @Override
    @Transactional(readOnly = true)
    User get(Long id) {
        return null
    }

    @Override
    @Transactional
    User update(User user) {
        return null
    }

    @Override
    @Transactional
    User delete(Long aLong) {
        return null
    }

    @Override
    @Transactional(readOnly = true)
    Long countAll() {
        return null
    }

    @Override
    @Transactional(readOnly = true)
    List<User> getAll() {
        return null
    }

    @Override
    @Transactional(readOnly = true)
    Page<User> findAll(Pageable pageable) {
        return null
    }
}
