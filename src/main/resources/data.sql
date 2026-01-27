INSERT IGNORE INTO member (email, name, oauth_id, oauth_provider, role, created_at, updated_at)
VALUES ('test@example.com', '테스트유저', 'test-oauth-id-001', 'GOOGLE', 'USER', NOW(6), NOW(6));

INSERT INTO recipe (title, description, cooking_time, serving_size, difficulty, cuisine_type, meal_type, source_type, is_deleted, bookmark_count, created_at, updated_at)
VALUES
('김치찌개', '얼큰하고 맛있는 김치찌개', 30, 2, 'INTERMEDIATE', 'KOREAN', 'MAIN', 'USER_CREATED', false, 0, NOW(6), NOW(6)),
('김치찌개', '얼큰하고 맛있는 김치찌개', 30, 2, 'INTERMEDIATE', 'KOREAN', 'MAIN', 'USER_CREATED', false, 0, NOW(6), NOW(6)),
('된장찌개', '구수한 된장찌개', 25, 2, 'BEGINNER', 'KOREAN', 'MAIN', 'USER_CREATED', false, 0, NOW(6), NOW(6)),
('불고기', '달콤한 한우 불고기', 40, 4, 'INTERMEDIATE', 'KOREAN', 'MAIN', 'USER_CREATED', false, 0, NOW(6), NOW(6)),
('파스타', '크리미한 까르보나라', 20, 1, 'BEGINNER', 'WESTERN', 'MAIN', 'USER_CREATED', false, 0, NOW(6), NOW(6)),
('떡볶이', '매콤달콤 떡볶이', 15, 2, 'BEGINNER', 'KOREAN', 'SNACK', 'USER_CREATED', false, 0, NOW(6), NOW(6));


INSERT INTO recipe_book (member_id, title, is_default, sort_order, created_at, updated_at)
VALUES
(1, '기본 레시피북', true, 1, NOW(6), NOW(6)),
(1, '찜해둔 요리', false, 2, NOW(6), NOW(6)),
(1, '다이어트 식단', false, 3, NOW(6), NOW(6));


INSERT INTO book_item (book_id, recipe_id, created_at, updated_at)
VALUES
(1, 1, NOW(6), NOW(6)),
(1, 2, NOW(6), NOW(6));

