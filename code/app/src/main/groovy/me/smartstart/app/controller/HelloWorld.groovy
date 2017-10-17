package me.smartstart.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorld {

    @RequestMapping('/')
    String home() {
        '*** Hello World ***'
    }
}
