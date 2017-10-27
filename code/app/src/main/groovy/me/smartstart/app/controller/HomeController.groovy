package me.smartstart.app.controller

import me.smartstart.app.UserDetailsImpl
import me.smartstart.core.domain.User
import me.smartstart.core.service.UserService
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.core.io.FileSystemResource
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
                return '{\n' +
                        '\t"sProcessing":   "处理中...",\n' +
                        '\t"sLengthMenu":   "显示 _MENU_ 项结果",\n' +
                        '\t"sZeroRecords":  "没有匹配结果",\n' +
                        '\t"sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",\n' +
                        '\t"sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",\n' +
                        '\t"sInfoFiltered": "(由 _MAX_ 项结果过滤)",\n' +
                        '\t"sInfoPostFix":  "",\n' +
                        '\t"sSearch":       "搜索:",\n' +
                        '\t"sUrl":          "",\n' +
                        '\t"sEmptyTable":     "表中数据为空",\n' +
                        '\t"sLoadingRecords": "载入中...",\n' +
                        '\t"sInfoThousands":  ",",\n' +
                        '\t"oPaginate": {\n' +
                        '\t\t"sFirst":    "首页",\n' +
                        '\t\t"sPrevious": "上页",\n' +
                        '\t\t"sNext":     "下页",\n' +
                        '\t\t"sLast":     "末页"\n' +
                        '\t},\n' +
                        '\t"oAria": {\n' +
                        '\t\t"sSortAscending":  ": 以升序排列此列",\n' +
                        '\t\t"sSortDescending": ": 以降序排列此列"\n' +
                        '\t}\n' +
                        '}'

            case Locale.US:
                return '{\n' +
                        '\t"sEmptyTable":     "No data available in table",\n' +
                        '\t"sInfo":           "Showing _START_ to _END_ of _TOTAL_ entries",\n' +
                        '\t"sInfoEmpty":      "Showing 0 to 0 of 0 entries",\n' +
                        '\t"sInfoFiltered":   "(filtered from _MAX_ total entries)",\n' +
                        '\t"sInfoPostFix":    "",\n' +
                        '\t"sInfoThousands":  ",",\n' +
                        '\t"sLengthMenu":     "Show _MENU_ entries",\n' +
                        '\t"sLoadingRecords": "Loading...",\n' +
                        '\t"sProcessing":     "Processing...",\n' +
                        '\t"sSearch":         "Search:",\n' +
                        '\t"sZeroRecords":    "No matching records found",\n' +
                        '\t"oPaginate": {\n' +
                        '\t\t"sFirst":    "First",\n' +
                        '\t\t"sLast":     "Last",\n' +
                        '\t\t"sNext":     "Next",\n' +
                        '\t\t"sPrevious": "Previous"\n' +
                        '\t},\n' +
                        '\t"oAria": {\n' +
                        '\t\t"sSortAscending":  ": activate to sort column ascending",\n' +
                        '\t\t"sSortDescending": ": activate to sort column descending"\n' +
                        '\t}\n' +
                        '}'
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
