package me.groot.core.service

import me.groot.core.domain.User
import me.groot.core.repository.PermissionRepository
import me.groot.core.repository.RoleRepository
import me.groot.core.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner)
@ContextConfiguration
class UserServiceTests {

    @Configuration
    static class UserServiceTestContextConfiguration {

        @Bean
        UserService userService() {
            new UserServiceImpl()
        }

        @Bean
        UserRepository userRepository() {
            Mockito.mock(UserRepository)
        }

        @Bean
        RoleRepository roleRepository() {
            Mockito.mock(RoleRepository)
        }

        @Bean
        PermissionRepository permissionRepository() {
            Mockito.mock(PermissionRepository)
        }
    }

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    @Before
    void setup() {
        def user = new User(username: 'john', password: 'john password',
                firstName: 'john', lastName: 'tom', dateCreated: new Date())
        Mockito.when(userRepository.findByUsername('john')).thenReturn(user)
    }


    @Test
    void 'test find user by username'() {

        // given
        // when
        def found = userService.findUserByUsername('john')

        // then
        assertThat found.firstName == 'john'
    }
}
