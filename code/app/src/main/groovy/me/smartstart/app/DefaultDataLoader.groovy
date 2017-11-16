package me.smartstart.app

import me.smartstart.core.domain.*
import me.smartstart.core.service.MenuService
import me.smartstart.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Profile('default')
@Component
class DefaultDataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DefaultDataLoader)

    @Autowired
    UserService userService

    @Autowired
    MenuService menuService

    @Autowired
    PasswordEncoder passwordEncoder
    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    void run(String... args) throws Exception {

        logger.debug('Start loading some data ...')

        // create menu category
        MenuCategory userManagementMenuCategory = new MenuCategory(name: 'userManagement', index: 1)
        userManagementMenuCategory = menuService.saveMenuCategory(userManagementMenuCategory)

        // create menu item
        MenuItem manageUserMenuItem = new MenuItem(name: 'manageUser', index: 11, target: 'user/index', menuCategory:
                userManagementMenuCategory)
        manageUserMenuItem = menuService.saveMenuItem(manageUserMenuItem)

        // create permission
        Permission manageUserPermission = new Permission(name: 'manageUser', active: true, menuItem: manageUserMenuItem)
        manageUserPermission = userService.savePermission(manageUserPermission)

        // create role admin
        Role adminRole = new Role(name: 'ROLE_ADMIN', description: 'System Admin', active: true,
                permissions: new HashSet<Permission>(Arrays.asList(manageUserPermission)))
        adminRole = userService.saveRole(adminRole)

        // create role user
        Role userRole = new Role(name: 'ROLE_USER', description: 'Normal User', active: true)
        userRole = userService.saveRole(userRole)

        // create user admin
        User adminUser = new User(username: 'admin', password: passwordEncoder.encode('admin'), firstName: 'Admin',
                lastName: 'Admin', dateCreated: new Date(), roles: new HashSet<Role>(Arrays.asList(adminRole)),
                active: true)
        userService.saveUser(adminUser)

        // create user user
        User userUser = new User(username: 'user', password: passwordEncoder.encode('user'), firstName: 'User',
                lastName: 'User', dateCreated: new Date(), roles: new HashSet<Role>(Arrays.asList(userRole)), active:
                true)
        userService.saveUser(userUser)

        logger.debug('Done loading the data')
    }
}
