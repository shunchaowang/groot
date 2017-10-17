package me.smartstart.app.controller

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

    @RequestMapping('/')
    String hello() {
        return 'hello/hello'
    }
}
