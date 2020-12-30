package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator
import javax.validation.constraints.NotBlank

data class RefreshTokenRequest @JsonCreator constructor(@NotBlank val refreshToken: String, var username: String)
