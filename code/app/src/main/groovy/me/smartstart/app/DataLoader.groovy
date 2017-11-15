package me.smartstart.app

import me.smartstart.core.domain.User
import me.smartstart.core.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Profile('default')
@Component
class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader)

    @Autowired
    UserRepository userRepository

    @Autowired
    PasswordEncoder passwordEncoder
    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    void run(String... args) throws Exception {

        logger.debug('Start loading some data ...')

        User user = new User(username: 'user', password: passwordEncoder.encode('user'), firstName: 'user',
        lastName: 'user', dateCreated: new Date())

        user = userRepository.save(user)
        logger.debug("saved user: ${user.password}")

        logger.debug('Done loading the data')
    }
}
