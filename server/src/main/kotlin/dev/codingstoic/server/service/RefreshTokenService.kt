package dev.codingstoic.server.service

import dev.codingstoic.server.entity.RefreshToken
import dev.codingstoic.server.execption.SpringRedditException
import dev.codingstoic.server.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
@Transactional
class RefreshTokenService(val refreshTokenRepository: RefreshTokenRepository) {
    fun generateRefreshToken(): RefreshToken {
        val refreshToken = RefreshToken()
        refreshToken.token = UUID.randomUUID().toString()
        refreshToken.createdDate = Instant.now()

        return refreshTokenRepository.save(refreshToken)
    }

    @Throws(SpringRedditException::class)
    fun validateRefreshToken(token: String) {
        refreshTokenRepository.findByToken(token).orElseThrow { SpringRedditException("Invalid refresh Token") }
    }

    fun deleteRefreshToken(token: String) {
        refreshTokenRepository.deleteByToken(token = token)
    }
}
