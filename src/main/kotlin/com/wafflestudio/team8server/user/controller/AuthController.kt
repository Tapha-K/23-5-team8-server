package com.wafflestudio.team8server.user.controller

import com.wafflestudio.team8server.user.dto.SignupRequest
import com.wafflestudio.team8server.user.dto.SignupResponse
import com.wafflestudio.team8server.user.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signup") // 회원가입: POST /api/auth/signup
    @ResponseStatus(HttpStatus.CREATED) // 201 반환
    fun signup(
        @Valid @RequestBody request: SignupRequest,
    ): SignupResponse = authService.signup(request)
}
