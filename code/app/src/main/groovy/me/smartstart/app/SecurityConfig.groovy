package me.smartstart.app

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig)

    @Autowired
    UserDetailsService userDetailsService

    /**
     * Used by the default implementation of {@link #authenticationManager()} to attempt
     * to obtain an {@link AuthenticationManager}. If overridden, the
     * {@link AuthenticationManagerBuilder} should be used to specify the
     * {@link AuthenticationManager}.
     *
     * <p>
     * The {@link #authenticationManagerBean()} method can be used to expose the resulting
     * {@link AuthenticationManager} as a Bean. The {@link #userDetailsServiceBean()} can
     * be used to expose the last populated {@link UserDetailsService} that is created
     * with the {@link AuthenticationManagerBuilder} as a Bean. The
     * {@link UserDetailsService} will also automatically be populated on
     * {@link HttpSecurity#getSharedObject(Class)} for use with other
     * {@link SecurityContextConfigurer} (i.e. RememberMeConfigurer )
     * </p>
     *
     * <p>
     * For example, the following configuration could be used to register in memory
     * authentication that exposes an in memory {@link UserDetailsService}:
     * </p>
     *
     * <pre>
     * &#064;Override
     * protected void configure(AuthenticationManagerBuilder auth) {* 	auth
     * 	// enable in memory based authentication with a user named
     * 	// &quot;user&quot; and &quot;admin&quot;
     * 	.inMemoryAuthentication().withUser(&quot;user&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;).and()
     * 			.withUser(&quot;admin&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;, &quot;ADMIN&quot;);
     *}*
     * // Expose the UserDetailsService as a Bean
     * &#064;Bean
     * &#064;Override
     * public UserDetailsService userDetailsServiceBean() throws Exception {* 	return super.userDetailsServiceBean();
     *}*
     * </pre>
     *
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     */
    @Override
    void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", '/webjars/**', '/js/**', '/css/**', '/images/**', '/vendors/**')
        // allow webjars and static go through
    }

/**
 * Override this method to configure the {@link HttpSecurity}. Typically subclasses
 * should not invoke this method by calling super as it may override their
 * configuration. The default configuration is:
 *
 * <pre>
 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
 * </pre>
 *
 * @param http the {@link HttpSecurity} to modify
 * @throws Exception if an error occurs
 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ROLEs must not have ROLE prefix
        http
        //.csrf().disable() // enable csrf protection, logout can only be post
                .authorizeRequests()
                .antMatchers("/register", "/about").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
        //.failureUrl("/login-error") // default is login?error
                .permitAll()
                .and()
                .logout()
        //.logoutSuccessUrl("/login") // default is login?logout
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403") // #4 denied, mapped in Controller
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        new BCryptPasswordEncoder()
    }

}
