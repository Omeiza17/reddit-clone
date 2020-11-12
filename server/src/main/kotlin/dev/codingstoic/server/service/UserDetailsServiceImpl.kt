package dev.codingstoic.server.service

import dev.codingstoic.server.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails? {
        val userOptional: Optional<dev.codingstoic.server.entity.User> = userRepository.findByUserName(username)
        val user: dev.codingstoic.server.entity.User = userOptional
                .orElseThrow {
                    UsernameNotFoundException("No user " +
                            "Found with username : " + username)
                }
        return org.springframework.security.core.userdetails.User(user.userName, user.password,
                user.isEnabled!!, true, true,
                true, getAuthorities("USER"))
    }

    private fun getAuthorities(role: String): Collection<GrantedAuthority?>? {
        return listOf(SimpleGrantedAuthority(role))
    }
}
