package me.groot.core.service

import me.groot.core.domain.MenuCategory
import me.groot.core.domain.MenuItem

interface MenuService {

    // Menu Category apis
    MenuCategory saveMenuCategory(MenuCategory menuCategory)

    MenuCategory getMenuCategory(long id)

    // Menu Item apis
    MenuItem saveMenuItem(MenuItem menuItem)

    MenuItem getMenuItem(long id)
}
