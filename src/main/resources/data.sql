-- 멤버
INSERT IGNORE INTO member (email, name, oauth_id, oauth_provider, role, created_at, updated_at)
VALUES ('test@example.com', '테스트유저', 'test-oauth-id-001', 'GOOGLE', 'USER', NOW(6), NOW(6));


-- 재료
INSERT IGNORE INTO ingredient (name, created_at, updated_at)
VALUES ('김치', NOW(6), NOW(6)),
       ('돼지고기 앞다리살', NOW(6), NOW(6)),
       ('두부', NOW(6), NOW(6)),
       ('양파', NOW(6), NOW(6)),
       ('대파', NOW(6), NOW(6)),
       ('마늘', NOW(6), NOW(6)),
       ('고추장', NOW(6), NOW(6)),
       ('된장', NOW(6), NOW(6)),
       ('간장', NOW(6), NOW(6)),
       ('스파게티면', NOW(6), NOW(6));

SET @ing_kimchi := (SELECT id
                    FROM ingredient
                    WHERE name = '김치'
                    LIMIT 1);
SET @ing_pork := (SELECT id
                  FROM ingredient
                  WHERE name = '돼지고기 앞다리살'
                  LIMIT 1);
SET @ing_tofu := (SELECT id
                  FROM ingredient
                  WHERE name = '두부'
                  LIMIT 1);
SET @ing_onion := (SELECT id
                   FROM ingredient
                   WHERE name = '양파'
                   LIMIT 1);
SET @ing_greenonion := (SELECT id
                        FROM ingredient
                        WHERE name = '대파'
                        LIMIT 1);
SET @ing_garlic := (SELECT id
                    FROM ingredient
                    WHERE name = '마늘'
                    LIMIT 1);
SET @ing_gochujang := (SELECT id
                       FROM ingredient
                       WHERE name = '고추장'
                       LIMIT 1);
SET @ing_doenjang := (SELECT id
                      FROM ingredient
                      WHERE name = '된장'
                      LIMIT 1);
SET @ing_soysauce := (SELECT id
                      FROM ingredient
                      WHERE name = '간장'
                      LIMIT 1);
SET @ing_pasta := (SELECT id
                   FROM ingredient
                   WHERE name = '스파게티면'
                   LIMIT 1);


-- 태그
INSERT IGNORE INTO tag (name, source_type, created_at, updated_at)
VALUES ('집밥', 'SYSTEM', NOW(6), NOW(6)),
       ('간단요리', 'SYSTEM', NOW(6), NOW(6)),
       ('혼밥', 'SYSTEM', NOW(6), NOW(6)),
       ('초보추천', 'SYSTEM', NOW(6), NOW(6)),
       ('주말요리', 'SYSTEM', NOW(6), NOW(6)),
       ('매운요리', 'SYSTEM', NOW(6), NOW(6)),
       ('찌개', 'SYSTEM', NOW(6), NOW(6)),
       ('볶음', 'SYSTEM', NOW(6), NOW(6)),
       ('밥반찬', 'SYSTEM', NOW(6), NOW(6)),
       ('고기요리', 'SYSTEM', NOW(6), NOW(6)),
       ('두부요리', 'SYSTEM', NOW(6), NOW(6)),
       ('김치요리', 'SYSTEM', NOW(6), NOW(6)),
       ('파스타', 'SYSTEM', NOW(6), NOW(6)),
       ('간장베이스', 'SYSTEM', NOW(6), NOW(6)),
       ('된장베이스', 'SYSTEM', NOW(6), NOW(6)),
       ('칼칼한맛', 'USER', NOW(6), NOW(6)),
       ('밥도둑', 'USER', NOW(6), NOW(6)),
       ('야식', 'USER', NOW(6), NOW(6));

SET @tag_home := (SELECT id
                  FROM tag
                  WHERE name = '집밥'
                  LIMIT 1);
SET @tag_easy := (SELECT id
                  FROM tag
                  WHERE name = '간단요리'
                  LIMIT 1);
SET @tag_solo := (SELECT id
                  FROM tag
                  WHERE name = '혼밥'
                  LIMIT 1);
SET @tag_beginner := (SELECT id
                      FROM tag
                      WHERE name = '초보추천'
                      LIMIT 1);
SET @tag_weekend := (SELECT id
                     FROM tag
                     WHERE name = '주말요리'
                     LIMIT 1);
SET @tag_spicy := (SELECT id
                   FROM tag
                   WHERE name = '매운요리'
                   LIMIT 1);
SET @tag_stew := (SELECT id
                  FROM tag
                  WHERE name = '찌개'
                  LIMIT 1);
SET @tag_stirfry := (SELECT id
                     FROM tag
                     WHERE name = '볶음'
                     LIMIT 1);
SET @tag_side := (SELECT id
                  FROM tag
                  WHERE name = '밥반찬'
                  LIMIT 1);
SET @tag_meat := (SELECT id
                  FROM tag
                  WHERE name = '고기요리'
                  LIMIT 1);
SET @tag_tofu := (SELECT id
                  FROM tag
                  WHERE name = '두부요리'
                  LIMIT 1);
SET @tag_kimchiTag := (SELECT id
                       FROM tag
                       WHERE name = '김치요리'
                       LIMIT 1);
SET @tag_pastaTag := (SELECT id
                      FROM tag
                      WHERE name = '파스타'
                      LIMIT 1);
SET @tag_soyBase := (SELECT id
                     FROM tag
                     WHERE name = '간장베이스'
                     LIMIT 1);
SET @tag_doenBase := (SELECT id
                      FROM tag
                      WHERE name = '된장베이스'
                      LIMIT 1);
SET @tag_kal := (SELECT id
                 FROM tag
                 WHERE name = '칼칼한맛'
                 LIMIT 1);
SET @tag_riceThief := (SELECT id
                       FROM tag
                       WHERE name = '밥도둑'
                       LIMIT 1);
SET @tag_latenight := (SELECT id
                       FROM tag
                       WHERE name = '야식'
                       LIMIT 1);


-- 레시피
INSERT IGNORE INTO recipe (title, description, cooking_time, serving_size, difficulty,
                           cuisine_type, meal_type, source_type, is_deleted, bookmark_count,
                           created_at, updated_at)
VALUES ('돼지고기 김치찌개', '돼지고기와 김치를 볶아 깊은 맛을 낸 얼큰한 김치찌개', 35, 2, 'INTERMEDIATE', 'KOREAN', 'MAIN',
        'USER', false, 0, NOW(6), NOW(6)),
       ('두부 된장찌개', '된장 베이스에 두부와 채소를 넣어 구수하게 끓인 찌개', 25, 2, 'BEGINNER', 'KOREAN', 'MAIN', 'USER',
        false, 0, NOW(6), NOW(6)),
       ('간장 불고기', '간장 양념에 재워 달달하게 볶아내는 불고기', 30, 3, 'INTERMEDIATE', 'KOREAN', 'MAIN', 'USER', false,
        0, NOW(6), NOW(6)),
       ('매콤 제육볶음', '고추장 양념으로 빠르게 볶아내는 제육볶음', 25, 3, 'INTERMEDIATE', 'KOREAN', 'MAIN', 'USER', false,
        0, NOW(6), NOW(6)),
       ('두부조림', '간장 양념으로 졸여 밥반찬으로 좋은 두부조림', 20, 2, 'BEGINNER', 'KOREAN', 'SIDE_DISH', 'USER', false,
        0, NOW(6), NOW(6)),
       ('대파 돼지고기 볶음', '대파 향을 살려 담백하게 볶아내는 돼지고기 볶음', 20, 2, 'BEGINNER', 'KOREAN', 'MAIN', 'USER',
        false, 0, NOW(6), NOW(6)),
       ('김치볶음', '김치를 달달하게 볶아 다양한 반찬에 활용하기 좋은 김치볶음', 15, 2, 'BEGINNER', 'KOREAN', 'SIDE_DISH',
        'USER', false, 0, NOW(6), NOW(6)),
       ('된장국', '된장으로 간을 맞춰 가볍게 끓인 기본 국', 15, 2, 'BEGINNER', 'KOREAN', 'MAIN', 'USER', false, 0,
        NOW(6), NOW(6)),
       ('마늘 간장 파스타', '마늘과 간장으로 감칠맛을 살린 간단 파스타', 15, 1, 'BEGINNER', 'WESTERN', 'MAIN', 'USER', false,
        0, NOW(6), NOW(6)),
       ('대파 간장 파스타', '대파 향과 간장 풍미로 만드는 부담 없는 파스타', 18, 1, 'BEGINNER', 'WESTERN', 'MAIN', 'USER',
        false, 0, NOW(6), NOW(6));

-- recipe id 변수 바인딩
SET @r_kimchi_stew := (SELECT id
                       FROM recipe
                       WHERE title = '돼지고기 김치찌개'
                       LIMIT 1);
SET @r_doenjang_stew := (SELECT id
                         FROM recipe
                         WHERE title = '두부 된장찌개'
                         LIMIT 1);
SET @r_bulgogi := (SELECT id
                   FROM recipe
                   WHERE title = '간장 불고기'
                   LIMIT 1);
SET @r_jeyuk := (SELECT id
                 FROM recipe
                 WHERE title = '매콤 제육볶음'
                 LIMIT 1);
SET @r_tofu_braise := (SELECT id
                       FROM recipe
                       WHERE title = '두부조림'
                       LIMIT 1);
SET @r_pork_scallion := (SELECT id
                         FROM recipe
                         WHERE title = '대파 돼지고기 볶음'
                         LIMIT 1);
SET @r_kimchi_stir := (SELECT id
                       FROM recipe
                       WHERE title = '김치볶음'
                       LIMIT 1);
SET @r_doenjang_soup := (SELECT id
                         FROM recipe
                         WHERE title = '된장국'
                         LIMIT 1);
SET @r_garlic_pasta := (SELECT id
                        FROM recipe
                        WHERE title = '마늘 간장 파스타'
                        LIMIT 1);
SET @r_scallion_pasta := (SELECT id
                          FROM recipe
                          WHERE title = '대파 간장 파스타'
                          LIMIT 1);


-- 레시피 재료
INSERT IGNORE INTO recipe_ingredient (recipe_id, ingredient_id, amount, created_at, updated_at)
VALUES (@r_kimchi_stew, @ing_kimchi, 250, NOW(6), NOW(6)),
       (@r_kimchi_stew, @ing_pork, 180, NOW(6), NOW(6)),
       (@r_kimchi_stew, @ing_tofu, 120, NOW(6), NOW(6)),
       (@r_kimchi_stew, @ing_onion, 60, NOW(6), NOW(6)),
       (@r_kimchi_stew, @ing_greenonion, 40, NOW(6), NOW(6)),
       (@r_kimchi_stew, @ing_garlic, 10, NOW(6), NOW(6)),
       (@r_kimchi_stew, @ing_soysauce, 15, NOW(6), NOW(6)),

       (@r_doenjang_stew, @ing_doenjang, 35, NOW(6), NOW(6)),
       (@r_doenjang_stew, @ing_tofu, 180, NOW(6), NOW(6)),
       (@r_doenjang_stew, @ing_onion, 60, NOW(6), NOW(6)),
       (@r_doenjang_stew, @ing_greenonion, 30, NOW(6), NOW(6)),
       (@r_doenjang_stew, @ing_garlic, 10, NOW(6), NOW(6)),
       (@r_doenjang_stew, @ing_soysauce, 10, NOW(6), NOW(6)),

       (@r_bulgogi, @ing_pork, 320, NOW(6), NOW(6)),
       (@r_bulgogi, @ing_onion, 90, NOW(6), NOW(6)),
       (@r_bulgogi, @ing_greenonion, 50, NOW(6), NOW(6)),
       (@r_bulgogi, @ing_garlic, 12, NOW(6), NOW(6)),
       (@r_bulgogi, @ing_soysauce, 35, NOW(6), NOW(6)),
       (@r_bulgogi, @ing_kimchi, 60, NOW(6), NOW(6)),

       (@r_jeyuk, @ing_pork, 330, NOW(6), NOW(6)),
       (@r_jeyuk, @ing_gochujang, 40, NOW(6), NOW(6)),
       (@r_jeyuk, @ing_onion, 90, NOW(6), NOW(6)),
       (@r_jeyuk, @ing_greenonion, 60, NOW(6), NOW(6)),
       (@r_jeyuk, @ing_garlic, 12, NOW(6), NOW(6)),
       (@r_jeyuk, @ing_soysauce, 15, NOW(6), NOW(6)),
       (@r_jeyuk, @ing_kimchi, 80, NOW(6), NOW(6)),

       (@r_tofu_braise, @ing_tofu, 280, NOW(6), NOW(6)),
       (@r_tofu_braise, @ing_soysauce, 25, NOW(6), NOW(6)),
       (@r_tofu_braise, @ing_onion, 70, NOW(6), NOW(6)),
       (@r_tofu_braise, @ing_greenonion, 50, NOW(6), NOW(6)),
       (@r_tofu_braise, @ing_garlic, 10, NOW(6), NOW(6)),
       (@r_tofu_braise, @ing_gochujang, 10, NOW(6), NOW(6)),

       (@r_pork_scallion, @ing_pork, 260, NOW(6), NOW(6)),
       (@r_pork_scallion, @ing_greenonion, 120, NOW(6), NOW(6)),
       (@r_pork_scallion, @ing_onion, 60, NOW(6), NOW(6)),
       (@r_pork_scallion, @ing_garlic, 10, NOW(6), NOW(6)),
       (@r_pork_scallion, @ing_soysauce, 20, NOW(6), NOW(6)),
       (@r_pork_scallion, @ing_gochujang, 8, NOW(6), NOW(6)),

       (@r_kimchi_stir, @ing_kimchi, 300, NOW(6), NOW(6)),
       (@r_kimchi_stir, @ing_onion, 60, NOW(6), NOW(6)),
       (@r_kimchi_stir, @ing_greenonion, 50, NOW(6), NOW(6)),
       (@r_kimchi_stir, @ing_garlic, 10, NOW(6), NOW(6)),
       (@r_kimchi_stir, @ing_soysauce, 10, NOW(6), NOW(6)),

       (@r_doenjang_soup, @ing_doenjang, 30, NOW(6), NOW(6)),
       (@r_doenjang_soup, @ing_onion, 70, NOW(6), NOW(6)),
       (@r_doenjang_soup, @ing_greenonion, 40, NOW(6), NOW(6)),
       (@r_doenjang_soup, @ing_garlic, 8, NOW(6), NOW(6)),
       (@r_doenjang_soup, @ing_tofu, 120, NOW(6), NOW(6)),

       (@r_garlic_pasta, @ing_pasta, 110, NOW(6), NOW(6)),
       (@r_garlic_pasta, @ing_garlic, 14, NOW(6), NOW(6)),
       (@r_garlic_pasta, @ing_onion, 40, NOW(6), NOW(6)),
       (@r_garlic_pasta, @ing_soysauce, 18, NOW(6), NOW(6)),
       (@r_garlic_pasta, @ing_greenonion, 20, NOW(6), NOW(6)),
       (@r_garlic_pasta, @ing_kimchi, 30, NOW(6), NOW(6)),

       (@r_scallion_pasta, @ing_pasta, 110, NOW(6), NOW(6)),
       (@r_scallion_pasta, @ing_greenonion, 90, NOW(6), NOW(6)),
       (@r_scallion_pasta, @ing_garlic, 12, NOW(6), NOW(6)),
       (@r_scallion_pasta, @ing_onion, 40, NOW(6), NOW(6)),
       (@r_scallion_pasta, @ing_soysauce, 20, NOW(6), NOW(6)),
       (@r_scallion_pasta, @ing_pork, 80, NOW(6), NOW(6));


-- 조리 순서
INSERT IGNORE INTO recipe_step (recipe_id, step_order, description)
VALUES (@r_kimchi_stew, 1, '냄비에 돼지고기를 넣고 중불에서 겉면이 익을 때까지 볶는다'),
       (@r_kimchi_stew, 2, '김치를 넣고 3~4분 더 볶아 신맛을 날린다'),
       (@r_kimchi_stew, 3, '양파와 마늘을 넣고 한 번 섞어 향을 올린다'),
       (@r_kimchi_stew, 4, '물을 붓고 끓기 시작하면 약불로 줄여 15~20분 끓인다'),
       (@r_kimchi_stew, 5, '두부를 넣고 5분 더 끓인 뒤 대파를 올려 마무리한다'),

       (@r_doenjang_stew, 1, '냄비에 물을 올리고 된장을 풀어 끓인다'),
       (@r_doenjang_stew, 2, '양파와 마늘을 넣고 5분 정도 끓인다'),
       (@r_doenjang_stew, 3, '두부를 넣고 7~8분 더 끓인다'),
       (@r_doenjang_stew, 4, '간으로 간장을 소량 더하고 대파를 넣어 마무리한다'),

       (@r_bulgogi, 1, '돼지고기에 간장과 마늘을 넣고 10분 정도 재운다'),
       (@r_bulgogi, 2, '달군 팬에 고기를 넣고 중불에서 볶기 시작한다'),
       (@r_bulgogi, 3, '고기가 반쯤 익으면 양파를 넣고 함께 볶는다'),
       (@r_bulgogi, 4, '수분이 자작해지면 대파를 넣고 1~2분 더 볶아 마무리한다'),

       (@r_jeyuk, 1, '돼지고기에 고추장, 간장, 마늘을 넣고 고루 섞어 10분 재운다'),
       (@r_jeyuk, 2, '팬을 달군 뒤 고기를 넣고 중불에서 볶는다'),
       (@r_jeyuk, 3, '고기가 익기 시작하면 양파를 넣고 함께 볶는다'),
       (@r_jeyuk, 4, '양념이 졸아들면 김치를 넣고 2~3분 더 볶는다'),
       (@r_jeyuk, 5, '마지막에 대파를 넣고 불을 끈 뒤 한 번 더 섞는다'),

       (@r_tofu_braise, 1, '두부를 두툼하게 썰어 키친타월로 물기를 닦는다'),
       (@r_tofu_braise, 2, '팬에 두부를 올려 앞뒤로 노릇하게 굽는다'),
       (@r_tofu_braise, 3, '간장과 마늘, 양파를 넣고 약불로 졸이기 시작한다'),
       (@r_tofu_braise, 4, '양념이 스며들면 고추장 소량으로 칼칼함을 더한다'),
       (@r_tofu_braise, 5, '대파를 올리고 1분 더 졸여 마무리한다'),

       (@r_pork_scallion, 1, '대파와 양파를 먹기 좋게 썬다'),
       (@r_pork_scallion, 2, '팬에 돼지고기를 넣고 중불에서 볶아 기름을 낸다'),
       (@r_pork_scallion, 3, '마늘을 넣고 30초 정도 볶아 향을 올린다'),
       (@r_pork_scallion, 4, '대파와 양파를 넣고 숨이 죽을 때까지 볶는다'),
       (@r_pork_scallion, 5, '간장으로 간을 맞추고 고추장 소량으로 풍미를 정리한다'),

       (@r_kimchi_stir, 1, '김치는 물기를 살짝 짜고 먹기 좋은 크기로 썬다'),
       (@r_kimchi_stir, 2, '팬에 김치와 양파를 넣고 중불에서 볶기 시작한다'),
       (@r_kimchi_stir, 3, '마늘을 넣고 향이 올라오면 간장으로 간을 잡는다'),
       (@r_kimchi_stir, 4, '마지막에 대파를 넣고 1분 더 볶아 마무리한다'),

       (@r_doenjang_soup, 1, '냄비에 물을 올리고 된장을 풀어 끓인다'),
       (@r_doenjang_soup, 2, '양파와 마늘을 넣고 5분 정도 끓인다'),
       (@r_doenjang_soup, 3, '두부를 넣고 5분 더 끓인다'),
       (@r_doenjang_soup, 4, '대파를 넣고 한소끔 끓인 뒤 불을 끈다'),

       (@r_garlic_pasta, 1, '스파게티면을 소금물에 알맞게 삶는다'),
       (@r_garlic_pasta, 2, '팬에 마늘을 볶아 향을 올리고 양파를 넣어 투명해질 때까지 볶는다'),
       (@r_garlic_pasta, 3, '삶은 면을 넣고 간장을 둘러 빠르게 섞는다'),
       (@r_garlic_pasta, 4, '대파를 넣고 30초 정도 더 볶아 향을 정리한다'),

       (@r_scallion_pasta, 1, '스파게티면을 소금물에 삶아 물기를 뺀다'),
       (@r_scallion_pasta, 2, '팬에 마늘을 먼저 볶고 대파를 넣어 향을 충분히 낸다'),
       (@r_scallion_pasta, 3, '돼지고기를 넣고 익을 때까지 볶는다'),
       (@r_scallion_pasta, 4, '양파를 넣고 숨이 죽으면 면을 넣는다'),
       (@r_scallion_pasta, 5, '간장을 둘러 간을 맞추고 빠르게 섞어 마무리한다');


-- 레시피 태그
INSERT IGNORE INTO recipe_tag (recipe_id, tag_id)
VALUES (@r_kimchi_stew, @tag_home),
       (@r_kimchi_stew, @tag_stew),
       (@r_kimchi_stew, @tag_kimchiTag),
       (@r_kimchi_stew, @tag_meat),
       (@r_kimchi_stew, @tag_kal),

       (@r_doenjang_stew, @tag_home),
       (@r_doenjang_stew, @tag_stew),
       (@r_doenjang_stew, @tag_doenBase),
       (@r_doenjang_stew, @tag_tofu),
       (@r_doenjang_stew, @tag_beginner),

       (@r_bulgogi, @tag_home),
       (@r_bulgogi, @tag_meat),
       (@r_bulgogi, @tag_soyBase),
       (@r_bulgogi, @tag_weekend),

       (@r_jeyuk, @tag_spicy),
       (@r_jeyuk, @tag_meat),
       (@r_jeyuk, @tag_stirfry),
       (@r_jeyuk, @tag_latenight),

       (@r_tofu_braise, @tag_side),
       (@r_tofu_braise, @tag_tofu),
       (@r_tofu_braise, @tag_soyBase),
       (@r_tofu_braise, @tag_riceThief),
       (@r_tofu_braise, @tag_easy),

       (@r_pork_scallion, @tag_stirfry),
       (@r_pork_scallion, @tag_meat),
       (@r_pork_scallion, @tag_easy),
       (@r_pork_scallion, @tag_home),

       (@r_kimchi_stir, @tag_side),
       (@r_kimchi_stir, @tag_kimchiTag),
       (@r_kimchi_stir, @tag_stirfry),
       (@r_kimchi_stir, @tag_easy),

       (@r_doenjang_soup, @tag_home),
       (@r_doenjang_soup, @tag_doenBase),
       (@r_doenjang_soup, @tag_beginner),

       (@r_garlic_pasta, @tag_pastaTag),
       (@r_garlic_pasta, @tag_soyBase),
       (@r_garlic_pasta, @tag_easy),
       (@r_garlic_pasta, @tag_solo),

       (@r_scallion_pasta, @tag_pastaTag),
       (@r_scallion_pasta, @tag_soyBase),
       (@r_scallion_pasta, @tag_solo),
       (@r_scallion_pasta, @tag_weekend);


-- 큐레이션
INSERT IGNORE INTO curation (title, description, day_types, time_types, cuisine_types, meal_types,
                      difficulties, keywords, tags, ingredients, is_active, created_at, updated_at)
VALUES ('자취요리 특선',
        '혼자서 간단하게 만들 수 있는 자취생 맞춤 요리 모음',
        'WEEKDAY,WEEKEND',
        'LUNCH,DINNER,LATE_NIGHT',
        'KOREAN',
        'MAIN,SIDE_DISH',
        'BEGINNER,INTERMEDIATE',
        '자취,간단',
        '자취,초간단',
        '계란,김치,라면,밥,햄,두부,돼지고기,된장,스팸,고추장,간장',
        true,
        NOW(6),
        NOW(6));


-- 레시피 북
INSERT IGNORE INTO recipe_book (id, member_id, title, is_default, sort_order, created_at, updated_at)
VALUES
(1, 1, '기본 레시피북', true, 1, NOW(6), NOW(6)),
(2, 1, '찜해둔 요리', false, 2, NOW(6), NOW(6)),
(3, 1, '다이어트 식단', false, 3, NOW(6), NOW(6));


INSERT IGNORE INTO recipe_book_item (book_id, recipe_id, created_at, updated_at)
VALUES
(1, 1, NOW(6), NOW(6)),
(1, 2, NOW(6), NOW(6));
