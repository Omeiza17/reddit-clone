package dev.codingstoic.server.controller

import dev.codingstoic.server.dto.AuthenticationResponse
import dev.codingstoic.server.dto.LoginRequest
import dev.codingstoic.server.dto.RefreshTokenRequest
import dev.codingstoic.server.dto.RegisterRequest
import dev.codingstoic.server.service.AuthService
import dev.codingstoic.server.service.RefreshTokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/api/auth"])
class AuthController(val authService: AuthService, val refreshTokenService: RefreshTokenService) {

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

    @PostMapping(path = ["/login"])
    fun login(@RequestBody loginRequest: LoginRequest): AuthenticationResponse {
        return authService.login(loginRequest = loginRequest)
    }

    @PostMapping(path = ["/refresh/token"])
    fun tokenRefresh(@Valid @RequestBody refreshTokenRequest: RefreshTokenRequest): AuthenticationResponse {
        return authService.refreshToken(refreshTokenRequest)
    }

    @PostMapping(path = ["/logout"])
    fun logout(@Valid @RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<String> {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.refreshToken)
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully")
    }
}
