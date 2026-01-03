CREATE TABLE IF NOT EXISTS social_credentials (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    provider   VARCHAR(50)  NOT NULL,
    social_id  VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 같은 소셜 서비스에서 동일한 사용자가 중복 가입하는 것 방지
    UNIQUE KEY uk_social_provider_social_id (provider, social_id),

    -- 사용자 삭제 시 인증 정보도 함께 삭제
    CONSTRAINT fk_social_credentials_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,

    -- provider 종류 제약, 추후 변경 가능
    CHECK (provider IN ('kakao', 'google'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='소셜 로그인 정보';

-- 인덱스
-- user_id 기반 조회 성능 향상 (로그인 후 사용자 정보 조회 시 사용)
CREATE INDEX idx_social_credentials_user_id ON social_credentials(user_id);