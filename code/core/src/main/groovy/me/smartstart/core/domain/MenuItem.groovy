package me.smartstart.core.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id
    String name
    String target

    // unidirectional relation to MenuCategory
//    MenuCategory menuCategory

    static belongsTo = [menuCategory: MenuCategory]

    static constraints = {
        name nullable: false, blank: false, size: 4..64, unique: true
        target nullable: false, blank: false, size: 4..128
    }
}
