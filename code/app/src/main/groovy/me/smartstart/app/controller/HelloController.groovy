package me.smartstart.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class HelloController {

    @RequestMapping('/')
    String home() {
        '*** Hello Home ***'
    }

    @RequestMapping('/hello')
    String hello() {
        return 'hello/hello'
    }
}
