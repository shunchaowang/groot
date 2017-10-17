package me.smartstart.core.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id
    String name
    String description
    boolean active

    // unidirectional relation to MenuItem
    MenuItem menuItem

    // bidirectional with Role
    //static hasMany = [roles: Role, users: User]

    // make Role as the owner of the relationship
    // Role().addToPermissions().save() will also update the relationship table
    static belongsTo = [roles: Role, uses: User] // make Role and User as the owner of the
    // many-to-many
    // relationship

    static constraints = {
        name nullable: false, blank: false, size: 4..64, unique: true
        description nullable: true, blank: false, size: 4..128
        active nullable: false
        menuItem nullable: true
    }
}
