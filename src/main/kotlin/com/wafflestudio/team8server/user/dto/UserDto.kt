package com.wafflestudio.team8server.user.dto

import com.wafflestudio.team8server.user.model.User
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "사용자 정보")
data class UserDto(
    @Schema(description = "사용자 ID", example = "1")
    val id: Long,
    @Schema(description = "사용자 닉네임", example = "홍길동")
    val nickname: String,
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg", nullable = true)
    val profileImageUrl: String?,
    @Schema(description = "계정 생성 일시", example = "2026-01-06T12:00:00")
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
