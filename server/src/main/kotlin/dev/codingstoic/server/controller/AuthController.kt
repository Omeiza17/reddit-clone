package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.RegisterRequest
import dev.codingstoic.server.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/auth"])
class AuthController(val authService: AuthService) {

    @PostMapping(path = ["/signup"])
    fun signup(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {
        authService.signup(registerRequest = registerRequest)
        return ResponseEntity<String>("User Registration Successful", HttpStatus.OK)
    }

    @GetMapping(path = ["/accountVerification/{token}"])
    fun verifyAccount(@PathVariable token: String): ResponseEntity<String> {
        authService.verifyAccount(token = token)
        return ResponseEntity<String>("Account Activated Successfully", HttpStatus.OK)
    }
}
