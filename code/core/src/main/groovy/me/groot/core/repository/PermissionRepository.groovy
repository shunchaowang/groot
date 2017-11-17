package me.groot.core.repository

import me.groot.core.domain.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByName(String name)
}