package dev.codingstoic.server.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.parser
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date.from


@Service
class JwtProvider {

    @Value("\${jwt.expiration.time}")
    private val jwtExpirationInMillis: Long? = null

    @Value("\${jwt.secret}")
    private val secretKey: String? = null

    fun generateToken(authentication: Authentication): String? {
        val principal = authentication.principal as User
        return Jwts.builder()
                .setSubject(principal.username)
                .setIssuedAt(from(Instant.now()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis!!)))
                .compact()
    }

    fun generateTokenWithUserName(username: String?): String? {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis!!)))
                .compact()
    }

    fun validateToken(jwt: String?): Boolean {
        parser().setSigningKey(secretKey).parseClaimsJws(jwt)
        return true
    }

    fun getUsernameFromJwt(token: String?): String? {
        val claims: Claims = parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        return claims.subject
    }

    fun getJwtExpirationInMillis(): Long? {
        return jwtExpirationInMillis
    }
}
