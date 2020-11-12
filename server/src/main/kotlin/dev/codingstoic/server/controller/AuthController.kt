package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.RegisterRequest
import dev.codingstoic.server.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/auth"])
class AuthController(val authService: AuthService) {

    @PostMapping(path = ["/signup"])
    fun signup(@RequestBody registerRequest: RegisterRequest): Unit {
        
    }
}
