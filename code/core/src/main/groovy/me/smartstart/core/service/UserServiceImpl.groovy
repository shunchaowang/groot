package me.smartstart.core.service

import me.smartstart.core.domain.Permission
import me.smartstart.core.domain.Role
import me.smartstart.core.domain.User
import me.smartstart.core.repository.PermissionRepository
import me.smartstart.core.repository.RoleRepository
import me.smartstart.core.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    PermissionRepository permissionRepository

    @Autowired
    RoleRepository roleRepository

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
    User saveUser(User user) {
        userRepository.save(user)
    }

    @Override
    @Transactional
    User updateUser(User user) {
        userRepository.save(user)
    }

    @Override
    @Transactional
    void deleteUser(long id) {
        userRepository.delete(id)
    }

    @Override
    @Transactional
    User activateUser(long id) {
        return null
    }

    @Override
    @Transactional
    User deactivateUser(long id) {
        return null
    }

    @Override
    @Transactional(readOnly = true)
    Long countUser() {
        userRepository.count()
    }

    @Override
    @Transactional(readOnly = true)
    User getUser(long id) {
        userRepository.getOne(id)
    }

    @Override
    @Transactional(readOnly = true)
    Page<User> findUsers(Specification<User> specification, Pageable pageable) {
        userRepository.findAll(specification, pageable)
    }

    @Override
    @Transactional(readOnly = true)
    long countUser(Specification<User> specification) {
        return userRepository.count(specification)
    }

    @Override
    @Transactional
    Permission savePermission(Permission permission) {
        return permissionRepository.save(permission)
    }

    @Override
    @Transactional(readOnly = true)
    Permission getPermission(long id) {
        permissionRepository.findOne(id)
    }

    @Override
    @Transactional(readOnly = true)
    Page<Permission> findPermissions(Pageable pageable) {
        permissionRepository.findAll(pageable)
    }

    @Override
    @Transactional
    Role saveRole(Role role) {
        return roleRepository.save(role)
    }

    @Override
    @Transactional(readOnly = true)
    Role getRole(long id) {
        roleRepository.findOne(id)
    }

    @Override
    @Transactional(readOnly = true)
    Role findRoleByName(String name) {
        roleRepository.findByName(name)
    }

    @Override
    @Transactional(readOnly = true)
    Page<Role> findRoles(Pageable pageable) {
        roleRepository.findAll(pageable)
    }

    @Override
    @Transactional(readOnly = true)
    List<Role> getAllRoles() {
        return roleRepository.findAll()
    }
}
