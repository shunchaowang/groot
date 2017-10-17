package me.smartstart.core.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id
    String username
    String password
    String firstName
    String lastName
    byte[] profilePicture
    String description

    Date dateCreated
    Date lastUpdated

    boolean active

    static hasMany = [roles: Role, permissions: Permission]

    static constraints = {
        username nullable: false, blank: false, size: 4..64, unique: true, email: true
        password nullable: false, blank: false, size: 8..128
        firstName nullable: false, blank: false, size: 4..64
        lastName nullable: false, blank: false, size: 4..64
        description nullable: true, blank: true
        active nullable: false
        profilePicture nullable: true, maxSize: 10 * 1024 * 1024 // limit file size to 10MB
    }
}
