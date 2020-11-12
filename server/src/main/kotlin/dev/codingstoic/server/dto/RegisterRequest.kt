package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class RegisterRequest @JsonCreator constructor(val email: String, val password: String, val username: String)
