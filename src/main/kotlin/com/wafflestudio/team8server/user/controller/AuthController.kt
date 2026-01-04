package com.wafflestudio.team8server.user.controller

import com.wafflestudio.team8server.user.dto.LoginRequest
import com.wafflestudio.team8server.user.dto.LoginResponse
import com.wafflestudio.team8server.user.dto.SignupRequest
import com.wafflestudio.team8server.user.dto.SignupResponse
import com.wafflestudio.team8server.user.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED) // 201 Created
    fun signup(
        @Valid @RequestBody request: SignupRequest,
    ): SignupResponse = authService.signup(request)

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK) // 200 OK
    fun login(
        @Valid @RequestBody request: LoginRequest,
    ): LoginResponse = authService.login(request)

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content
    fun logout(
        @RequestHeader("Authorization") authorization: String,
    ) {
        // "Bearer {token}" 형식에서 토큰 추출
        val token = authorization.removePrefix("Bearer ")
        authService.logout(token)
    }
}
