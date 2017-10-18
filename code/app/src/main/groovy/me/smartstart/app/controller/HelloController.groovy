package me.smartstart.app.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Controller
class HelloController {

    @RequestMapping('/home')
    @ResponseBody
    String home() {
        '*** Hello Home ***'
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @RequestMapping('/')
    String hello() {
        return 'hello/hello'
    }
}
