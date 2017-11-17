package me.groot.core.repository

import me.groot.core.domain.MenuCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}