package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator
import java.time.Instant


data class AuthenticationResponse @JsonCreator constructor(
    val authenticationToken: String, val username: String,
    val refreshToken: String, val expireAt: Instant,
)
