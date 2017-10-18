package me.smartstart.core.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

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
