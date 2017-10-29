package me.smartstart.app.controller.rest

import me.smartstart.app.vo.RestResponse
import me.smartstart.app.vo.UserCommand
import me.smartstart.core.domain.Role
import me.smartstart.core.domain.User
import me.smartstart.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
@RequestMapping('/rest/user')
class RestUserController {


    private static final Logger logger = LoggerFactory.getLogger(RestUserController)

    @Autowired
    UserService userService

    @Autowired
    BCryptPasswordEncoder passwordEncoder

    @PostMapping('/save')
    RestResponse save(@Valid @RequestBody UserCommand userCommand, BindingResult bindingResult) {

        RestResponse response = new RestResponse()

        if (bindingResult.hasErrors()) {
            response.status = 'failed'
            response.data = bindingResult.allErrors
            return RestResponse
        }

        def user = userService.saveUser(toUser(userCommand))
        response.status = 'successful'
        response.data = new UserCommand(user)

        response
    }

    private User toUser(UserCommand userCommand) {
        User user = new User(username: userCommand.username, firstName: userCommand.firstName,
                lastName: userCommand.lastName, description: userCommand.description)

        user.dateCreated = new Date()
        user.password = passwordEncoder.encode('password')
        user.active = true

        user.roles = new HashSet<>()
        userCommand.roles?.each {
            Role role = userService.getRole(it.id)
            user.roles.add(role)
        }

        user
    }
}
