package me.smartstart.core.domain

import javax.persistence.*

@Entity
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    @Column(nullable = false, unique = true, length = 64)
    String name

    @Column
    String description

    @Column(nullable = false)
    boolean active

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = 'role_permission_mapping',
            joinColumns = @JoinColumn(name = 'role_id'),
            inverseJoinColumns = @JoinColumn(name = 'permission_id')
    )
    Set<Permission> permissions

    @ManyToMany
    Set<User> user
}
