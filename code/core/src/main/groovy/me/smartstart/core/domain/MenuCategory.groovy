package me.smartstart.core.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id
    String name

    static hasMany = [menuItems: MenuItem]

    // mappedBy can make menuItems to be mapped by the attribute from MenuItem if needed

    static constraints = {
        name nullable: false, blank: false, size: 4..64, unique: true
    }
}
