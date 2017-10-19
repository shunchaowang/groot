package me.smartstart.core.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    @Column(nullable = false, length = 64)
    String name

    @Column(nullable = false)
    int index

    @OneToMany(mappedBy = 'menuCategory')
    Set<MenuItem> menuItems
}
