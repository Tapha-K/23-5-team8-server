package com.wafflestudio.team8server.user.service

import com.wafflestudio.team8server.common.exception.DuplicateEmailException
import com.wafflestudio.team8server.user.dto.SignupRequest
import com.wafflestudio.team8server.user.dto.SignupResponse
import com.wafflestudio.team8server.user.model.LocalCredential
import com.wafflestudio.team8server.user.model.User
import com.wafflestudio.team8server.user.repository.LocalCredentialRepository
import com.wafflestudio.team8server.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val localCredentialRepository: LocalCredentialRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun signup(request: SignupRequest): SignupResponse {
        // 1. 이메일 중복 체크
        if (localCredentialRepository.existsByEmail(request.email)) {
            throw DuplicateEmailException(request.email) // 409 CONFLICT
        }

        // 2. User 엔티티 생성 및 저장
        val user =
            User(
                nickname = request.nickname,
                profileImageUrl = request.profileImageUrl,
            )
        val savedUser = userRepository.save(user) // id가 0 → DB가 부여한 실제 ID로 변경됨

        // 3. 비밀번호 해싱
        val hashedPassword: String = passwordEncoder.encode(request.password)!!

        // 4. LocalCredential 엔티티 생성 및 저장
        val credential =
            LocalCredential(
                user = savedUser,
                email = request.email,
                passwordHash = hashedPassword,
            )
        localCredentialRepository.save(credential)

        // 5. 응답 DTO 반환
        return SignupResponse.from(savedUser, request.email)
    }
}
