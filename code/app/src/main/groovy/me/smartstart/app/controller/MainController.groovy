package me.smartstart.app.controller

import me.smartstart.core.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.LocaleResolver

import javax.servlet.http.HttpServletRequest

@Controller
class MainController {

    @Autowired
    UserService userService

    @Autowired
    LocaleResolver localeResolver

    @RequestMapping('/hello')
    //@ResponseBody
    String hello() {
        return 'hello/hello'
    }

    //@PreAuthorize("isAuthenticated() and hasAuthority('ROLE_ADMIN')")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping('/')
    String home() {
        def userCount = userService.countAllUser()
        println("uuuuser service: ${userCount}")
        return 'home'
    }

    // login form
    //@RequestMapping(value = '/login', method = RequestMethod.GET)
    @GetMapping('/login')
    String login() {
        return 'login'
    }

    // login form with error
    @RequestMapping(value = '/login-error', method = RequestMethod.GET)
    String loginError(Model model) {
        model.addAttribute('loginError', true)
        return 'login'
    }

    @GetMapping('/403')
    String error403() {
        return '403'
    }
}
