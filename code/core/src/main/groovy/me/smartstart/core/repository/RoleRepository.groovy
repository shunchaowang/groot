package me.smartstart.core.repository

import me.smartstart.core.domain.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name)
}