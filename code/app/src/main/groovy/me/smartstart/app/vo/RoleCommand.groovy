package me.smartstart.app.vo

import me.smartstart.core.domain.Role

class RoleCommand {
    long id
    String name

    RoleCommand() {}

    RoleCommand(Role role) {

        id = role.id
        name = role.name
    }
}
