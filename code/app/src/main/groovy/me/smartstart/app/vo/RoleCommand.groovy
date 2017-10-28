package me.smartstart.app.vo

import me.smartstart.core.domain.Role
import org.hibernate.validator.constraints.NotEmpty

class RoleCommand {

    long id
    @NotEmpty
    String name

    RoleCommand() {}

    RoleCommand(Role role) {

        id = role.id
        name = role.name
    }
}
