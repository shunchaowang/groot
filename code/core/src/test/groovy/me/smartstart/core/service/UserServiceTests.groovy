package me.smartstart.core.service

import me.smartstart.core.domain.User
import me.smartstart.core.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat

@DataJpaTest
@RunWith(SpringRunner)
class UserServiceTests {

    @Autowired
    UserRepository userRepository

    @Test
    void whenFindByUsername_thenReturnUser() {

        // given
        def user = new User(username: 'john', password: 'john password',
                firstName: 'john', lastName: 'tom', dateCreated: new Date())
        userRepository.save(user)

        // when
        def found = userRepository.findByUsername('john')

        // then
        assertThat found.firstName == 'john'
    }
}
