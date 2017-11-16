package me.smartstart.core.service

import me.smartstart.core.domain.MenuCategory
import me.smartstart.core.domain.MenuItem
import me.smartstart.core.repository.MenuCategoryRepository
import me.smartstart.core.repository.MenuItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuServiceImpl implements MenuService {

    @Autowired
    MenuCategoryRepository menuCategoryRepository

    @Autowired
    MenuItemRepository menuItemRepository

    @Override
    @Transactional
    MenuCategory saveMenuCategory(MenuCategory menuCategory) {
        return menuCategoryRepository.save(menuCategory)
    }

    @Override
    @Transactional(readOnly = true)
    MenuCategory getMenuCategory(long id) {
        return menuCategoryRepository.getOne(id)
    }

    @Override
    @Transactional
    MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem)
    }

    @Override
    @Transactional(readOnly = true)
    MenuItem getMenuItem(long id) {
        return menuItemRepository.getOne(id)
    }
}
