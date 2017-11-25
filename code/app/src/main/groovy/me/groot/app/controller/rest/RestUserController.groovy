package me.groot.app.controller.rest

import me.groot.app.vo.RestResponse
import me.groot.app.vo.UserCommand
import me.groot.core.domain.Role
import me.groot.core.domain.User
import me.groot.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PreAuthorize("hasPermission('', 'manageUser')")
    @PutMapping('/update')
    RestResponse update(@Valid @RequestBody UserCommand userCommand, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new RestResponse(status: 'error', data: bindingResult.allErrors)
        }

        def user = userService.saveUser(toUser(userCommand))

        new RestResponse(status: 'success', data: new UserCommand(user))
    }


    @PreAuthorize("hasPermission('', 'manageUser')")
    @PostMapping('/create')
    RestResponse create(@Valid @RequestBody UserCommand userCommand, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new RestResponse(status: 'error', data: bindingResult.allErrors)
        }

        def user = userService.saveUser(toUser(userCommand))
        new RestResponse(status: 'success', data: new UserCommand(user))
    }

    @PreAuthorize("hasPermission('', 'manageUser')")
    @DeleteMapping('/delete/{id}')
    RestResponse delete(@PathVariable long id) {
        User user = userService.getUser(id)
        userService.deleteUser(id)
        new RestResponse(status: 'success', data: user.username)
    }

    private User toUser(UserCommand userCommand) {

        User user

        if (userCommand.id) { // updating
            user = userService.getUser(userCommand.id)
            user.lastUpdated = new Date()
            user.roles.clear()

        } else { //creating

            user = new User()
            user.roles = new HashSet<>()
            user.dateCreated = new Date()
            user.password = passwordEncoder.encode('password')
            user.active = true
        }

        user.with {
            username = userCommand.username
            firstName = userCommand.firstName
            lastName = userCommand.lastName
            description = userCommand.description
        }

        userCommand.roles?.each {
            Role role = userService.getRole(it.id)
            user.roles.add(role)
        }

        user
    }
}
