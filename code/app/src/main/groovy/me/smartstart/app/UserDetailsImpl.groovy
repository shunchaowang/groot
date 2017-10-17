package me.smartstart.app

import me.smartstart.core.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * The SecurityUser implements spring security UserDetails to support authentication.
 */
class UserDetailsImpl extends User implements UserDetails {

    UserDetailsImpl(User user) {
        this.with {
            id = user?.id
            username = user?.username
            password = user?.password
            firstName = user?.password
            lastName = user?.password
            active = user?.active

            roles = user?.roles
            permissions = user?.permissions

            roles.each {
                permissions.addAll(it.permissions)
            }
        }
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities
        this.roles.each {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(it.name)
            authorities.add(authority)
        }
        return authorities
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    boolean isAccountNonExpired() {
        return true
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    boolean isAccountNonLocked() {
        return true
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    boolean isEnabled() {
        return this.active
    }
}
