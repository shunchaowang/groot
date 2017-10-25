package me.smartstart.app.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping('/user')
class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController)

    // index view
    // Role and Authority are the same, hasRole and hasAuthority are the same as well.
    // hasAuthority('ROLE_ADMIN') has same result with hasRole('ROLE_ADMIN')
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_ADMIN')")
    @GetMapping('/index')
    String index() {
       return 'index'
    }

}