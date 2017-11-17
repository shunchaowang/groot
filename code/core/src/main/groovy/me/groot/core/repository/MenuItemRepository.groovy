package me.groot.core.repository

import me.groot.core.domain.MenuItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}