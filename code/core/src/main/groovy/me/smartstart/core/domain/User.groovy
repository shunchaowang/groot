package me.smartstart.core.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.Lob
import javax.persistence.ManyToMany
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    @Column(nullable = false, unique = true, length = 64)
    String username

    @Column(nullable = false, length = 127)
    String password

    @Column(nullable = false, length = 32)
    String firstName

    @Column(nullable = false, length = 32)
    String lastName

    @Lob
    byte[] profilePicture

    @Column
    String description

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date dateCreated

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastUpdated

    @Column(nullable = false)
    boolean active

    @ManyToMany
    @JoinTable(name = 'user_role_mapping',
            joinColumns = @JoinColumn(name = 'user_id'),
            inverseJoinColumns = @JoinColumn(name = 'role_id')
    )
    Set<Role> roles

    @ManyToMany
    @JoinTable(name = 'user_permission_mapping',
            joinColumns = @JoinColumn(name = 'user_id'),
            inverseJoinColumns = @JoinColumn(name = 'permission_id')
    )
    Set<Permission> permissions
}
