package me.groot.core.domain

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

    @Version
    long version

    // JoinTable makes Role is the owner of the relationship to Permission
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = 'role_permission_mapping',
            joinColumns = @JoinColumn(name = 'role_id'),
            inverseJoinColumns = @JoinColumn(name = 'permission_id')
    )
    Set<Permission> permissions

    @ManyToMany(mappedBy = 'roles')
    Set<User> user
}
