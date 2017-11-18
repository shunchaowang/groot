package me.groot.core.domain

import javax.persistence.*

@Entity
class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    @Column(nullable = false, length = 64)
    String name

    @Column(nullable = false)
    int index

    @Version
    long version

    @OneToMany(mappedBy = 'menuCategory')
    Set<MenuItem> menuItems
}
