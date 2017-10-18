package me.smartstart.core.repository

import me.smartstart.core.domain.MenuItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}