package me.smartstart.core.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToOne

@Entity
class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    @Column(nullable = false, unique = true, length = 64)
    String name

    @Column
    String description

    @Column(nullable = false)
    boolean active

    // unidirectional relation to MenuItem
    @OneToOne
    @JoinColumn(name = 'menu_item_id')
    MenuItem menuItem

    @ManyToMany(mappedBy = 'permissions')
    Set<Role> roles

    @ManyToMany(mappedBy = 'permissions')
    Set<User> users
}
