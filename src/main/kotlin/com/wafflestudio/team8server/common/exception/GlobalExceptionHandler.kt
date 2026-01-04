package com.wafflestudio.team8server.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

data class ErrorResponse(
    // 에러 발생 시각
    val timestamp: LocalDateTime = LocalDateTime.now(),
    // HTTP 상태 코드 (400, 409 등)
    val status: Int,
    // HTTP 상태 이름 ("Bad Request", "Conflict")
    val error: String,
    // 사용자에게 표시할 에러 메시지
    val message: String,
    // 에러 종류 식별자
    val errorCode: String? = null,
    // 필드별 에러
    val validationErrors: Map<String, String?>? = null,
)

@RestControllerAdvice
class GlobalExceptionHandler {
    // 이메일 중복 예외 처리 → 409 CONFLICT
    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmail(e: DuplicateEmailException): ResponseEntity<ErrorResponse> {
        val response =
            ErrorResponse(
                status = HttpStatus.CONFLICT.value(), // 409
                error = "Conflict",
                message = e.message, // "이미 사용 중.."
                errorCode = e.errorCode, // "DUPLICATE_EMAIL"
            )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    // 유효성 검증 실패 예외 처리 → 400 BAD_REQUEST
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        // 모든 검증 에러를 Map으로 변환 (필드명 → 에러 메시지)
        val errors =
            e.bindingResult.allErrors.associate { error ->
                val field = (error as FieldError).field // 필드명 (email, password 등)
                val message = error.defaultMessage // 에러 메시지 (@NotBlank의 message)
                field to message // map pair 생성
            }

        val response =
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(), // 400
                error = "Bad Request",
                message = "입력 값이 유효하지 않습니다",
                errorCode = "VALIDATION_FAILED",
                validationErrors = errors, // {"email": "이메일은 필수입니다", ...}
            )
        return ResponseEntity.badRequest().body(response)
    }
}
