package me.smartstart.core.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id
    String name
    String description
    boolean active

    static constraints = {
        name nullable: false, blank: false, size: 4..64, unique: true
        description nullable: true, blank: false
        active nullable: false
    }

    // bidirectional relation with Permission and User
    static hasMany = [permissions: Permission, users: User]

    static belongsTo = User
}
