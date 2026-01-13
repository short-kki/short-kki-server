CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    oauth_id VARCHAR(255) NOT NULL,
    oauth_provider VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    INDEX idx_member_email (email),
    INDEX idx_member_oauth (oauth_id, oauth_provider)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
