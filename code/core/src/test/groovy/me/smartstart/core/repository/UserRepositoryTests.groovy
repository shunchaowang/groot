package me.smartstart.core.repository

import me.smartstart.core.domain.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

import java.text.DateFormat
import java.text.SimpleDateFormat

import static org.assertj.core.api.Assertions.assertThat

@DataJpaTest
@RunWith(SpringRunner)
class UserRepositoryTests {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    UserRepository repository

    @Test
    void testFindByUsername() {

        // given
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        Date date = new Date()
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        def user = new User(username: 'john', password: 'john password',
                firstName: 'john', lastName: 'tom', dateCreated: new Date())
        entityManager.persist(user)
        entityManager.flush()

        // when
        User found = repository.findByUsername('john')

        // then
        assertThat found.firstName == 'john'
    }
}
