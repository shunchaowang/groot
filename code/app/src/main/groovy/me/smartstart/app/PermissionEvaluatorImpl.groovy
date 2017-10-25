package me.smartstart.app

import me.smartstart.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class PermissionEvaluatorImpl implements PermissionEvaluator {

    @Autowired
    UserService userService

    private static final Logger logger = LoggerFactory.getLogger(PermissionEvaluatorImpl)

    /**
     *
     * @param authentication represents the user in question. Should not be null.
     * @param targetDomainObject the domain object for which permissions should be
     * checked. May be null in which case implementations should return false, as the null
     * condition can be checked explicitly in the expression.
     * @param permission a representation of the permission object as supplied by the
     * expression system. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        logger.debug("Evaluate permission ${permission}")
        UserDetailsImpl userDetails = authentication.principal as UserDetailsImpl
        logger.debug("Evaluate permission for user ${userDetails.username}")
        return userDetails?.permissions?.contains(permission as String)
    }

    /**
     * Alternative method for evaluating a permission where only the identifier of the
     * target object is available, rather than the target instance itself.
     *
     * @param authentication represents the user in question. Should not be null.
     * @param targetId the identifier for the object instance (usually a Long)
     * @param targetType a String representing the target's type (usually a Java
     * classname). Not null.
     * @param permission a representation of the permission object as supplied by the
     * expression system. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false
    }
}
