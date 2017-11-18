package me.groot.core.domain

import javax.persistence.*

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
    byte[] profileImage

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

    @Version
    long version

    // JoinTable makes User is the owner of the relationship to Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = 'user_role_mapping',
            joinColumns = @JoinColumn(name = 'user_id'),
            inverseJoinColumns = @JoinColumn(name = 'role_id')
    )
    Set<Role> roles

    // JoinTable makes User is the owner of the relationship to Permission
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = 'user_permission_mapping',
            joinColumns = @JoinColumn(name = 'user_id'),
            inverseJoinColumns = @JoinColumn(name = 'permission_id')
    )
    Set<Permission> permissions
}
