package me.smartstart.core.domain

import javax.persistence.*

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
