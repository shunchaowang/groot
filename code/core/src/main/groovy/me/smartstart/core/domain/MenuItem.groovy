package me.smartstart.core.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    @Column(nullable = false, length = 64)
    String name

    @Column(nullable = false, length = 127)
    String target

    @Column(nullable = false)
    long index

    // unidirectional relation to MenuCategory
    @ManyToOne
    @JoinColumn(name = 'menu_category_id')
    MenuCategory menuCategory

}
