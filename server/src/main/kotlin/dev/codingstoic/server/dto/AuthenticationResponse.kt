package dev.codingstoic.server.dto

import com.fasterxml.jackson.annotation.JsonCreator


data class AuthenticationResponse @JsonCreator constructor(val authenticationToken: String, val username: String)
