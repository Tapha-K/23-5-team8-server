package com.wafflestudio.team8server.user.dto

import com.wafflestudio.team8server.user.model.User
import java.time.LocalDateTime

// JSON 변환 예시:
// {
//   "id": 1,
//   "email": "test@example.com",
//   "nickname": "테스터",
//   "profileImageUrl": null,
//   "createdAt": "2026-01-03T22:30:00"
// }
data class SignupResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(
            user: User,
            email: String,
        ): SignupResponse =
            SignupResponse(
                id = user.id,
                email = email, // User에는 없으므로 별도 전달하는 로직 필요
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl,
                createdAt = user.createdAt,
            )
    }
}
