package me.groot.app.vo

import me.groot.core.domain.User
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.context.i18n.LocaleContextHolder

import java.text.DateFormat

class UserCommand {
    long id
    @NotEmpty
    @Email
    String username
    @NotEmpty
    String firstName
    @NotEmpty
    String lastName
    String description
    String dateCreated
    String lastUpdated

    List<RoleCommand> roles

    UserCommand() {}

    UserCommand(User user) {
        id = user.id
        username = user.username
        firstName = user.firstName
        lastName = user.lastName
        description = user.description
        Locale locale = LocaleContextHolder.locale
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
        dateCreated = dateFormat.format(user.dateCreated)
        if (user.lastUpdated) {
            lastUpdated = dateFormat.format(user.lastUpdated)
        }

        roles = new ArrayList<>()
        user.roles?.each {
            RoleCommand role = new RoleCommand(it)
            roles.add(role)
        }
    }
}
