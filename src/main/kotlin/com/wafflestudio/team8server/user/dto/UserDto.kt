package com.wafflestudio.team8server.user.dto

import com.wafflestudio.team8server.user.model.User
import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(user: User): UserDto =
            UserDto(
                id = user.id,
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl,
                createdAt = user.createdAt,
            )
    }
}
