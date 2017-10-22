package me.smartstart.app.controller

import me.smartstart.core.domain.User
import me.smartstart.core.service.UserService
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotNull
import java.security.Principal

@Controller
class HomeController {

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
    @RequestMapping(['/home', '/index', '/'])
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

    /**
     * Principal and Authentication can both be auto binded by spring context.
     * A principal can call name to get logged user's name,
     * An authentication can be casted to UserDetails.
     * @param principal
     * @return
     */
    @GetMapping('/home/profile')
    String profile(Principal principal, Model model) {
        String username = principal.name
        User user = userService.findUserByUsername(username)
        model.addAttribute('userCommand', new UserCommand(user))
        return 'profile'
    }

    @PostMapping('/home/profile')
    String updateProfile(@Valid final UserCommand userCommand, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return 'profile'
        }

        User user = userService.getUser(userCommand.id)
        user.with {
            (username, firstName, lastName, description) = [userCommand.username, userCommand.firstName, userCommand.lastName, userCommand.description]
        }
        userService.saveUser(user)
        return 'redirect:/home'
    }

    @GetMapping('/403')
    String error403() {
        return '403'
    }
}

class UserCommand {

    long id
    @NotEmpty
    String username
    @NotEmpty
    String firstName
    @NotEmpty
    String lastName
    String description

    UserCommand() {}

    UserCommand(User user) {

        (id, username, firstName, lastName, description) = [user.id, user.username, user.firstName, user.lastName, user.description]
    }
}

