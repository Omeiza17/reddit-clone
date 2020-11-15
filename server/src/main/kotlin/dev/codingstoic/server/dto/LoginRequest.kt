package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class LoginRequest @JsonCreator constructor(val username: String, val password: String)
