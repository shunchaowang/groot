package me.smartstart.app.controller

import me.smartstart.core.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {

    @Autowired
    UserService userService

    @RequestMapping('/home')
    @ResponseBody
    String home() {
        '*** Hello Home ***'
    }

    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_ADMIN')")
    @RequestMapping('/')
    String hello() {
        def userCount = userService.countAllUser()
        println("uuuuser service: ${userCount}")
        return 'hello/hello'
    }

    // login form
    @RequestMapping(value = '/login', method = RequestMethod.GET)
    String login() {
        return 'login'
    }

    // login form with error
    @RequestMapping(value = '/login-error', method = RequestMethod.GET)
    String loginError(Model model) {
        model.addAttribute('loginError', true)
        return 'login'
    }
}
