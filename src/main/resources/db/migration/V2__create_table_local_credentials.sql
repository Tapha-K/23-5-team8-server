CREATE TABLE IF NOT EXISTS local_credentials (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT       NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 이메일은 전체 시스템에서 유일해야 함 (중복 가입 방지)
    UNIQUE KEY uk_local_email (email),

    -- 한 사용자는 하나의 로컬 인증 정보만 가질 수 있음
    UNIQUE KEY uk_local_user_id (user_id),

    -- 사용자 삭제 시 인증 정보도 함께 삭제
    CONSTRAINT fk_local_credentials_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='로컬 로그인 정보';