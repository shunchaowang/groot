package me.smartstart.core.service

import me.smartstart.core.domain.User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService {

    @Override
    User findByUsername(String username) {
        return null
    }
}
