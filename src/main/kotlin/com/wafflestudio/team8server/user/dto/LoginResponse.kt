package com.wafflestudio.team8server.user.dto

data class LoginResponse(
    val user: UserDto,
    val accessToken: String,
)
