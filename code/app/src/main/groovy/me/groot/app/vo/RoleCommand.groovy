package me.groot.app.vo

import me.groot.core.domain.Role
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
