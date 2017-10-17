package me.smartstart.core.service

import me.smartstart.core.domain.User

interface UserService {

    User findByUsername(String username)
}