package me.groot.core.domain

import javax.persistence.*

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
    int index

    @Version
    long version

    // unidirectional relation to MenuCategory
    @ManyToOne
    @JoinColumn(name = 'menu_category_id')
    MenuCategory menuCategory

}
