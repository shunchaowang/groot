package me.groot.app

import me.groot.core.CoreApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(CoreApplication.class)
class AppApplication {

    static void main(String[] args) {
        SpringApplication.run AppApplication, args
    }
}
