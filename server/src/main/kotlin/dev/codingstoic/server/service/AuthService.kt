package dev.codingstoic.server.service

import dev.codingstoic.server.dto.RegisterRequest
import dev.codingstoic.server.entity.User
import dev.codingstoic.server.entity.VerificationToken
import dev.codingstoic.server.repository.UserRepository
import dev.codingstoic.server.repository.VerificationTokenRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthService(
        val passwordEncoder: PasswordEncoder, val userRepository: UserRepository,
        val verificationTokenRepository: VerificationTokenRepository,
) {
    @Transactional
    fun signup(registerRequest: RegisterRequest) {
        val user = User()
        user.email = registerRequest.email
        user.password = passwordEncoder.encode(registerRequest.password)
        user.userName = registerRequest.username
        user.createdDate = Instant.now()
        user.isEnabled = false
        userRepository.save(user)

        val token = generateVerificationTokenToken(user)
    }

    private fun generateVerificationTokenToken(user: User): String {
        val token = UUID.randomUUID().toString()
        val verificationToken = VerificationToken()
        verificationToken.token = token
        verificationToken.user = user
        verificationTokenRepository.save(verificationToken);
        return token
    }
}
