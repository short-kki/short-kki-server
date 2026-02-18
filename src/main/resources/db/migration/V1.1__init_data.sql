-- =============================================
-- 파일: 공식 로고 이미지
-- =============================================
INSERT INTO file_metadata (id, object_key, original_name, extension, size, target_type, target_id,
                           uploader_type, uploader_id, upload_status, visibility, public_url,
                           created_at, updated_at)
VALUES (1, 'public/assets/logo.png', 'profile.png', 'png', 491363, 'MEMBER_PROFILE_IMG', NULL,
        'SYSTEM', NULL, 'UPLOADED', 'PUBLIC',
        'https://d36jasu56jneaq.cloudfront.net/assets/logo.png', NOW(6), NOW(6));

-- =============================================
-- 멤버
-- =============================================
INSERT INTO `member` (id, email, name, role, profile_img_file_id, created_at, updated_at)
VALUES (1, 'shortkki_official@shortkki.kr', '숏끼', 'USER', 1, NOW(6), NOW(6));

-- =============================================
-- 큐레이션 데이터
-- =============================================
INSERT INTO `curation` (`title`, `description`,
                        `day_types`, `time_types`,
                        `cuisine_types`, `meal_types`, `difficulties`,
                        `keywords`, `tags`, `ingredients`,
                        `is_active`, `created_at`, `updated_at`)
VALUES ('흑백요리사에서 본 그 맛!', 'TV에서 화제가 된 셰프들의 레시피를 집에서 도전해보세요',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
        '', 'MAIN', 'INTERMEDIATE',
        '흑백요리사,셰프,에드워드리,최강록,손종원,안성재,나폴리맛피아,정은영,김호영,최현석,고급요리', '흑백요리사', '',
        1, NOW(6), NOW(6)),

       ('자취생 필수! 10분 완성 레시피', '바쁜 일상 속에서도 뚝딱 만드는 간단 요리',
        'WEEKDAY,WEEKEND', 'MORNING,LUNCH,DINNER,LATE_NIGHT',
        'KOREAN', 'MAIN,SNACK', 'BEGINNER',
        '자취,초간단,간단,빠른,혼밥', '자취,초간단,한그릇', '라면,참치',
        1, NOW(6), NOW(6)),

       ('오늘은 파스타 어때요?', '알리오 올리오부터 크림 파스타까지, 다양한 파스타 레시피',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
        'WESTERN,FUSION', 'MAIN', 'BEGINNER,INTERMEDIATE',
        '파스타,알리오올리오,크림파스타,토마토파스타,까르보나라', '파스타,면요리', '스파게티,올리브오일,마늘',
        1, NOW(6), NOW(6)),

       ('맛있게 먹으면서 건강하게!', '칼로리 걱정 없이 든든하게 즐기는 다이어트 레시피',
        'WEEKDAY,WEEKEND', 'MORNING,LUNCH,DINNER',
        'KOREAN,FUSION,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER',
        '다이어트,저칼로리,건강,샐러드,닭가슴살,포케', '다이어트,건강식,단백질', '닭가슴살,양배추,오이',
        1, NOW(6), NOW(6)),

       ('엄마 손맛 느끼는 밑반찬', '냉장고에 있으면 든든한 한식 밑반찬 모음',
        'WEEKEND', 'LUNCH,DINNER',
        'KOREAN', 'SIDE_DISH', 'BEGINNER,INTERMEDIATE',
        '밑반찬,반찬,나물,무침,조림,볶음', '밑반찬,반찬,집밥', '고사리,시금치,콩나물',
        1, NOW(6), NOW(6)),

       ('육식파를 위한 고기 특선', '스테이크부터 수육까지, 육즙 가득한 고기 요리',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
        'KOREAN,WESTERN', 'MAIN', 'BEGINNER,INTERMEDIATE',
        '고기,스테이크,수육,육전,삼겹살,갈비,구이', '메인요리,단백질', '돼지고기,소고기',
        1, NOW(6), NOW(6)),

       ('한 그릇으로 든든하게!', '간단하지만 맛있는 볶음밥과 덮밥 레시피',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
        'KOREAN,CHINESE,FUSION', 'MAIN', 'BEGINNER',
        '볶음밥,덮밥,한그릇,간편식,게살볶음밥,계란덮밥', '한그릇,볶음,덮밥', '',
        1, NOW(6), NOW(6)),

       ('식후 달콤함을 더해줄 디저트', '집에서 만드는 특별한 디저트 레시피',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
        'FUSION,ASIAN,WESTERN', 'DESSERT,SNACK', 'BEGINNER,INTERMEDIATE',
        '디저트,달콤,프렌치토스트,쿠키,케이크,와플', '디저트,간식', '초콜릿,생크림',
        1, NOW(6), NOW(6)),

       ('배고픈 밤을 위한 야식 메뉴', '늦은 밤 출출할 때 딱 좋은 야식 레시피',
        'WEEKDAY,WEEKEND', 'LATE_NIGHT',
        'KOREAN', 'MAIN,SNACK', 'BEGINNER',
        '야식,라면,볶음밥,닭발,떡볶이', '야식,한그릇,매운맛', '라면,치즈',
        1, NOW(6), NOW(6)),

       ('정성 가득 명절 요리', '설날, 추석에 만드는 전통 명절 음식',
        'WEEKEND', 'LUNCH,DINNER',
        'KOREAN', 'MAIN,SIDE_DISH', 'INTERMEDIATE',
        '명절,잡채,전,나물,떡국,떡만두국', '명절음식,한식,손님상', '고사리,도라지,버섯',
        1, NOW(6), NOW(6)),

       ('처음 만들어도 실패 없는 양식', '누구나 쉽게 도전할 수 있는 양식 레시피',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
        'WESTERN', 'MAIN,SNACK', 'BEGINNER',
        '양식,파스타,스튜,토스트,카나페', '양식,초간단', '파스타면,토마토,치즈',
        1, NOW(6), NOW(6)),

       ('바다의 맛을 담은 생선 요리', '담백하고 건강한 생선 요리 레시피',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
        'KOREAN,WESTERN', 'MAIN', 'BEGINNER,INTERMEDIATE',
        '생선,조기,가자미,고등어,연어,생선구이', '생선요리,건강식', '조기,가자미,고등어',
        1, NOW(6), NOW(6)),

       ('내일 점심은 내가 싼다!', '도시락과 밀프렙에 딱 좋은 레시피 모음',
        'WEEKDAY', 'MORNING,LUNCH',
        'KOREAN,FUSION', 'MAIN,SNACK', 'BEGINNER',
        '도시락,밀프렙,밥버거,김밥,볶음밥,주먹밥', '도시락,간편식', '참치,스팸',
        1, NOW(6), NOW(6)),

       ('손님 오는 날 특별한 한 끼', '홈파티에 내놓아도 손색없는 요리',
        'WEEKEND', 'LUNCH,DINNER',
        'WESTERN,FUSION,KOREAN', 'MAIN', 'INTERMEDIATE',
        '홈파티,손님상,스테이크,동파육,잡채', '홈파티,손님상,메인요리', '소고기,삼겹살',
        1, NOW(6), NOW(6)),

       ('스트레스 날리는 매콤한 요리', '칼칼하고 얼큰한 맛이 필요할 때',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
        'KOREAN', 'MAIN', 'BEGINNER',
        '매콤,매운,칼칼,닭발,떡볶이,짬뽕', '매운맛,칼칼', '고춧가루,청양고추',
        1, NOW(6), NOW(6)),

       ('출출할 때 딱! 간식 레시피', '감자튀김부터 토스트까지 맛있는 간식 모음',
        'WEEKDAY,WEEKEND', 'MORNING,LUNCH,DINNER,LATE_NIGHT',
        'WESTERN,KOREAN,FUSION', 'SNACK', 'BEGINNER',
        '간식,감자튀김,토스트,김밥,쿠키', '간식,스낵', '감자,식빵',
        1, NOW(6), NOW(6)),

       ('동서양의 맛을 하나로!', '창의적인 퓨전 요리 레시피',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
        'FUSION', 'MAIN,DESSERT', 'BEGINNER,INTERMEDIATE',
        '퓨전,명란파스타,허브볶음밥,무스테이크', '퓨전,창작요리', '명란,허브,치즈',
        1, NOW(6), NOW(6)),

       ('여유로운 주말 아침', '늦잠 자고 일어나 즐기는 브런치 레시피',
        'WEEKEND', 'MORNING,LUNCH',
        'WESTERN,KOREAN', 'MAIN,SNACK', 'BEGINNER',
        '브런치,토스트,프렌치토스트,팬케이크,샐러드', '브런치,주말요리', '식빵,버터',
        1, NOW(6), NOW(6)),

       ('따뜻한 국물이 생각날 때', '속까지 따뜻해지는 국물 요리 모음',
        'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
        'KOREAN,WESTERN', 'MAIN', 'BEGINNER,INTERMEDIATE',
        '국물,스튜,곰탕,찌개,된장찌개,감자탕', '국물요리,따뜻한', '무,콩나물,두부',
        1, NOW(6), NOW(6)),

       ('출근 전 뚝딱 아침', '바쁜 아침에도 5분이면 완성하는 간단 레시피',
        'WEEKDAY', 'MORNING',
        'KOREAN,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER',
        '아침,출근,간단,빠른,토스트', '아침식사,초간단', '스팸,두부',
        1, NOW(6), NOW(6)),

       ('가벼운 아침 채소 한 접시', '상큼한 채소로 시작하는 건강한 아침',
        'WEEKDAY,WEEKEND', 'MORNING',
        'KOREAN', 'SIDE_DISH', 'BEGINNER',
        '채소,샐러드,나물,무침,양배추,가벼운', '아침,채소,건강', '양배추,오이,도라지',
        1, NOW(6), NOW(6)),

       ('아침 단백질 충전', '하루를 든든하게 시작하는 고단백 아침 메뉴',
        'WEEKDAY,WEEKEND', 'MORNING',
        'KOREAN,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER',
        '단백질,두부,육전,아침,든든,오믈렛', '단백질,아침,든든', '두부,돼지고기',
        1, NOW(6), NOW(6)),

       ('점심 파스타 한 그릇', '점심시간에 딱 맞는 간편 파스타 모음',
        'WEEKDAY,WEEKEND', 'LUNCH',
        'WESTERN,FUSION', 'MAIN', 'BEGINNER',
        '파스타,점심,까르보나라,알리오올리오,명란,허브', '파스타,점심,면요리', '파스타면,올리브오일,마늘',
        1, NOW(6), NOW(6)),

       ('평일 점심 볶음밥 특선', '냉장고 재료로 뚝딱 만드는 볶음밥',
        'WEEKDAY', 'LUNCH',
        'KOREAN,FUSION', 'MAIN', 'BEGINNER',
        '볶음밥,점심,김치볶음밥,새우볶음밥,참치볶음밥,간편', '볶음밥,점심,한그릇', '',
        1, NOW(6), NOW(6)),

       ('점심 든든 한식 백반', '집밥이 그리울 때, 정갈한 한식 한 상',
        'WEEKDAY,WEEKEND', 'LUNCH',
        'KOREAN', 'MAIN,SIDE_DISH', 'BEGINNER,INTERMEDIATE',
        '한식,백반,집밥,조기,나물,계란찜', '한식,점심,집밥', '조기,고사리',
        1, NOW(6), NOW(6)),

       ('아침에 좋은 따뜻한 한 그릇', '속을 편안하게 달래주는 따뜻한 아침 메뉴',
        'WEEKDAY,WEEKEND', 'MORNING',
        'KOREAN,CHINESE', 'MAIN', 'BEGINNER,INTERMEDIATE',
        '국물,곰탕,따뜻한,아침,속편한,죽', '아침,국물,따뜻한', '무',
        1, NOW(6), NOW(6)),

       ('점심 다이어트 한 끼', '칼로리 걱정 없이 배부른 점심 식단',
        'WEEKDAY', 'LUNCH',
        'FUSION,ASIAN,KOREAN', 'MAIN,SIDE_DISH', 'BEGINNER',
        '다이어트,점심,저칼로리,포케,샐러드,무스테이크', '다이어트,점심,건강', '새우,양배추',
        1, NOW(6), NOW(6)),

       ('아시안 점심 특선', '색다른 아시안 풍미로 즐기는 점심',
        'WEEKDAY,WEEKEND', 'LUNCH',
        'ASIAN,JAPANESE,CHINESE', 'MAIN,SIDE_DISH', 'BEGINNER',
        '아시안,일식,중식,포케,야키소바,볶음면', '아시안,점심', '새우,두부',
        1, NOW(6), NOW(6)),

       ('주중 간편 김밥 & 밥버거', '손에 들고 먹기 좋은 한 끼 메뉴',
        'WEEKDAY', 'MORNING,LUNCH',
        'KOREAN', 'MAIN,SNACK', 'BEGINNER',
        '김밥,밥버거,무스비,간편,한손,출근', '간편식,도시락,한손', '스팸,참치',
        1, NOW(6), NOW(6)),

       ('퇴근 후 혼밥 저녁', '지친 하루 끝, 나를 위한 간단 저녁 한 끼',
        'WEEKDAY', 'DINNER',
        'KOREAN,CHINESE', 'MAIN', 'BEGINNER',
        '혼밥,퇴근,저녁,간단,계란덮밥,김치말이국수', '혼밥,저녁,간편식', '김치,라면',
        1, NOW(6), NOW(6)),

       ('저녁 스테이크 & 양식 코스', '특별한 저녁을 위한 근사한 양식 요리',
        'WEEKEND', 'DINNER',
        'WESTERN,FUSION', 'MAIN', 'INTERMEDIATE',
        '스테이크,양식,뫼니에르,스튜,프리카세', '양식,특별한저녁', '소고기,버터,올리브오일',
        1, NOW(6), NOW(6)),

       ('밤에 즐기는 매운맛 도전', '스트레스는 매운맛으로 풀자',
        'WEEKDAY,WEEKEND', 'LATE_NIGHT',
        'KOREAN', 'MAIN', 'BEGINNER',
        '매운,매콤,닭발,짬뽕,떡볶이,불닭', '매운맛,늦은밤', '고춧가루,청양고추',
        1, NOW(6), NOW(6)),

       ('저녁 찜 & 수육 한 상', '푸짐한 찜요리와 수육으로 차리는 저녁',
        'WEEKEND', 'DINNER',
        'KOREAN,CHINESE', 'MAIN', 'BEGINNER,INTERMEDIATE',
        '찜,수육,대파수육,동파육,알배추찜', '찜요리,수육,푸짐한', '돼지고기,대파,배추',
        1, NOW(6), NOW(6)),

       ('늦은 밤 한 입 간식', '출출한 밤, 간단히 손으로 집어 먹는 한 입 메뉴',
        'WEEKDAY,WEEKEND', 'LATE_NIGHT',
        'KOREAN,WESTERN', 'SNACK', 'BEGINNER',
        '간식,한입,무스비,김밥,감자튀김,카나페', '간식,한입,늦은밤', '스팸,감자',
        1, NOW(6), NOW(6)),

       ('저녁 건강식 한 끼', '가볍지만 든든한 저녁 다이어트 메뉴',
        'WEEKDAY', 'DINNER',
        'FUSION,ASIAN,KOREAN', 'MAIN,SIDE_DISH', 'BEGINNER',
        '건강,다이어트,저녁,포케,샐러드', '건강식,저녁,가벼운', '새우,두부,양배추',
        1, NOW(6), NOW(6)),

       ('주말 저녁 홈파티', '집에서 즐기는 근사한 홈파티 메뉴',
        'WEEKEND', 'DINNER',
        'WESTERN,FUSION', 'MAIN,SNACK', 'BEGINNER,INTERMEDIATE',
        '홈파티,카나페,스테이크,카프레제,파티', '홈파티,주말,특별한', '소고기,새우,토마토',
        1, NOW(6), NOW(6)),

       ('늦은 밤 간편 면요리', '배고픈 밤, 후루룩 면 한 그릇',
        'WEEKDAY,WEEKEND', 'LATE_NIGHT',
        'KOREAN,WESTERN', 'MAIN', 'BEGINNER',
        '면요리,라면,파스타,국수,짬뽕,까르보나라', '면요리,늦은밤,한그릇', '라면,파스타면',
        1, NOW(6), NOW(6)),

       ('저녁 중화요리 한 상', '집에서 만드는 중화풍 저녁 식탁',
        'WEEKDAY,WEEKEND', 'DINNER',
        'CHINESE,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER,INTERMEDIATE',
        '중화요리,동파육,계란덮밥,마파두부,중식', '중식,저녁', '두부,돼지고기',
        1, NOW(6), NOW(6)),

       ('늦은 밤 감성 안주', '맥주 한 잔과 함께하는 안주 모음',
        'WEEKDAY,WEEKEND', 'LATE_NIGHT',
        'KOREAN,WESTERN', 'SNACK,SIDE_FOR_DRINK', 'BEGINNER,INTERMEDIATE',
        '안주,맥주,감자튀김,육전,닭발', '안주,늦은밤,감성', '',
        1, NOW(6), NOW(6));