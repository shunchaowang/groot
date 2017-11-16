package me.smartstart.core.service

import me.smartstart.core.domain.MenuCategory
import me.smartstart.core.domain.MenuItem

interface MenuService {

    // Menu Category apis
    MenuCategory saveMenuCategory(MenuCategory menuCategory)

    MenuCategory getMenuCategory(long id)

    // Menu Item apis
    MenuItem saveMenuItem(MenuItem menuItem)

    MenuItem getMenuItem(long id)
}
