package dev.codingstoic.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll().anyRequest().authenticated()
    }

    @Autowired
    fun configureGlobal(authenticationManagerBuilder: AuthenticationManagerBuilder): Unit {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
