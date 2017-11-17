package me.groot.app.controller

import me.groot.app.UserDetailsImpl
import me.groot.app.util.Constant
import me.groot.core.domain.User
import me.groot.core.service.UserService
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.LocaleResolver

import javax.validation.Valid
import javax.validation.constraints.AssertTrue
import java.security.Principal

@Controller
class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController)

    @Autowired
    UserService userService

    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    LocaleResolver localeResolver

    @Autowired
    MessageSource messageSource

    //@PreAuthorize("isAuthenticated() and hasAuthority('ROLE_ADMIN')")
    @RequestMapping(['/home', '/index', '/'])
    String home() {
        def userCount = userService.countUser()
        println("uuuuser service: ${userCount}")
        return 'home/index'
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
        UserDetailsImpl userDetails = (principal as Authentication).principal as UserDetailsImpl
        User user = userService.findUserByUsername(userDetails.username)

        model.addAttribute('profileCommand', new ProfileCommand(user))
        return 'home/profile'
    }

    @PostMapping('/home/profile')
    String saveProfile(@Valid final ProfileCommand profileCommand, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return 'home/profile'
        }

        User user = userService.getUser(profileCommand.id)
        user.with {
            (username, firstName, lastName, description) = [profileCommand.username, profileCommand.firstName, profileCommand.lastName, profileCommand.description]
        }
        userService.saveUser(user)
        return 'redirect:/home'
    }

    @GetMapping('/home/password')
    String password(Principal principal, Model model) {
        String username = principal.name
        User user = userService.findUserByUsername(username)
        model.addAttribute('passwordCommand', new PasswordCommand(user))
        return 'home/password'
    }

    @PostMapping('/home/password')
    String savePassword(@Valid final PasswordCommand passwordCommand, BindingResult bindingResult) {

        User user = userService.getUser(passwordCommand.id)

        if (!passwordEncoder.matches(passwordCommand.currentPassword, user.password)) {
            Locale locale = LocaleContextHolder.locale
            String error = messageSource.getMessage('password.not.correct.message', null, locale)
            bindingResult.rejectValue('currentPassword', 'user.password', error)
        }
        if (bindingResult.hasErrors()) {
            return 'home/password'
        }

        user.password = passwordEncoder.encode(passwordCommand.password)
        userService.saveUser(user)

        return 'redirect:/home'
    }

    @GetMapping(value = '/table/lang', produces = 'application/json;charset=UTF-8')
    @ResponseBody
    String dataTableLang() {

        Locale locale = LocaleContextHolder.locale

        switch (locale) {
            case Locale.CHINA:
                return Constant.dataTableChineseLanguage

            case Locale.ENGLISH:
                return Constant.dataTableEnglishLanguage

            default:
                return Constant.dataTableChineseLanguage
        }


    }

    @GetMapping('/403')
    String error403() {
        return '403'
    }
}

class ProfileCommand {

    long id
    @NotEmpty
    @Email
    String username
    @NotEmpty
    String firstName
    @NotEmpty
    String lastName
    String description

    ProfileCommand() {}

    ProfileCommand(User user) {

        (id, username, firstName, lastName, description) = [user.id, user.username, user.firstName, user.lastName, user.description]
    }
}

class PasswordCommand {

    long id
    @NotEmpty
    String currentPassword
    @NotEmpty
    String password
    @NotEmpty
    String confirmPassword

    @AssertTrue(message = "{PasswordCommand.isPasswordConfirmed.message}")
    boolean isPasswordConfirmed

    PasswordCommand() {}

    PasswordCommand(User user) {
        id = user.id
    }

    // this property makes we can use the AssertTrue message from the field
    boolean isPasswordConfirmed() {
        return confirmPassword == password
    }
}
