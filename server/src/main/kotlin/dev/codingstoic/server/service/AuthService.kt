package dev.codingstoic.server.service

import dev.codingstoic.server.dto.AuthenticationResponse
import dev.codingstoic.server.dto.LoginRequest
import dev.codingstoic.server.dto.RefreshTokenRequest
import dev.codingstoic.server.dto.RegisterRequest
import dev.codingstoic.server.entity.NotificationEmail
import dev.codingstoic.server.entity.User
import dev.codingstoic.server.entity.VerificationToken
import dev.codingstoic.server.execption.SpringRedditException
import dev.codingstoic.server.repository.UserRepository
import dev.codingstoic.server.repository.VerificationTokenRepository
import dev.codingstoic.server.security.JwtProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*


@Service
@Transactional
class AuthService(
    val passwordEncoder: PasswordEncoder, val userRepository: UserRepository,
    val verificationTokenRepository: VerificationTokenRepository,
    val mailService: MailService,
    val authenticationManager: AuthenticationManager,
    val jwtProvider: JwtProvider,
    val refreshTokenService: RefreshTokenService,
) {
    fun signup(registerRequest: RegisterRequest) {
        val user = User()
        user.email = registerRequest.email
        user.password = passwordEncoder.encode(registerRequest.password)
        user.userName = registerRequest.username
        user.createdDate = Instant.now()
        user.isEnabled = false
        userRepository.save(user)

        val token = generateVerificationTokenToken(user)
        mailService.sendMail(
            NotificationEmail(
                "Thank you for Signing up to Reddit Clone. Please click on the " +
                        "following url to activate your account: http://localhost:8080/api/auth/accountVerification/$token",
                "Please activate your account", user.email!!
            )
        )
    }

    private fun generateVerificationTokenToken(user: User): String {
        val token = UUID.randomUUID().toString()
        val verificationToken = VerificationToken()
        verificationToken.token = token
        verificationToken.user = user
        verificationTokenRepository.save(verificationToken)
        return token
    }

    @Throws(SpringRedditException::class)
    fun verifyAccount(token: String) {
        val verificationToken = verificationTokenRepository.findByToken(token)
        verificationToken.orElseThrow { SpringRedditException("Invalid Token") }
        fetchAndEnableUser(verificationToken.get())
    }


    private fun fetchAndEnableUser(verificationToken: VerificationToken) {
        val userName = verificationToken.user?.userName
        val user: User = userRepository.findByUserName(userName!!).orElseThrow {
            SpringRedditException("User not found with name - $userName")
        }
        user.isEnabled = true
        userRepository.save(user)
    }

    fun login(loginRequest: LoginRequest): AuthenticationResponse {
        val authenticate: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authenticate
        val token = jwtProvider.generateToken(authenticate)
        return AuthenticationResponse(
            authenticationToken = token!!, username = loginRequest.username,
            expireAt = Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()!!),
            refreshToken = refreshTokenService.generateRefreshToken().token!!,
        )
    }

    @Throws(UsernameNotFoundException::class)
    @Transactional(readOnly = true)
    fun getCurrentUser(): User? {
        val principal =
            SecurityContextHolder.getContext().authentication.principal as org.springframework.security.core.userdetails.User
        return userRepository.findByUserName(principal.username)
            .orElseThrow { UsernameNotFoundException("User name not found - ${principal.username}") }
    }

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): AuthenticationResponse {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.refreshToken)
        val token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.username)
        return AuthenticationResponse(
            authenticationToken = token!!, refreshToken = refreshTokenRequest.refreshToken,
            expireAt = Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()!!),
            username = refreshTokenRequest.username,
        )
    }

}
