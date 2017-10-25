package me.smartstart.app.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordEncoderGenerator {

    static void main(String[] args) {

        def password = 'admin'

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder()

        System.out.println("${password} is salted to ${encoder.encode(password)}")
    }
}
