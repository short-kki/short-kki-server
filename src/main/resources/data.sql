-- 파일
INSERT INTO file_metadata (object_key, original_name, extension, size, target_type, target_id, uploader_type, uploader_id, upload_status, visibility, public_url, created_at, updated_at)
VALUES ('public/assets/logo.png','profile.png','png',491363,'MEMBER_PROFILE_IMG',NULL,'SYSTEM',NULL,'PENDING','PUBLIC','https://d36jasu56jneaq.cloudfront.net/assets/logo.png',NOW(6),NOW(6));

-- 멤버
INSERT IGNORE INTO `member` (id, email, name, oauth_id, oauth_provider, role, profile_img_file_id, created_at, updated_at) VALUES
(1, 'chanmi.lee@test.com', '이찬미', 'test-oauth-id-002', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(2, 'eunseo.jo@test.com', '조은서', 'test-oauth-id-003', 'KAKAO', 'USER', NULL, NOW(6), NOW(6)),
(3, 'donghun.lee@test.com', '이동훈', 'test-oauth-id-004', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(4, 'junha.park@test.com', '박준하', 'test-oauth-id-005', 'NAVER', 'USER', NULL, NOW(6), NOW(6)),
(5, 'heechan.ahn@test.com', '안희찬', 'test-oauth-id-006', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(6, 'hyunsoo.kim@test.com', '김현수', 'test-oauth-id-007', 'KAKAO', 'USER', NULL, NOW(6), NOW(6)),
(7, 'jimin.kim@test.com', '김지민', 'test-oauth-id-008', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(8, 'junyoung.jung@test.com', '정준영', 'test-oauth-id-009', 'NAVER', 'USER', NULL, NOW(6), NOW(6)),
(9, 'jiwoo.heo@test.com', '허지우', 'test-oauth-id-010', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(10, 'junhyung.park@test.com', '박준형', 'test-oauth-id-011', 'KAKAO', 'USER', NULL, NOW(6), NOW(6)),
(11, 'junhyung.lee@test.com', '이준형', 'test-oauth-id-012', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(12, 'nahyun.kim@test.com', '김나현', 'test-oauth-id-013', 'NAVER', 'USER', NULL, NOW(6), NOW(6)),
(13, 'chanyong.hong@test.com', '홍찬용', 'test-oauth-id-014', 'GOOGLE', 'USER', NULL, NOW(6), NOW(6)),
(14, 'jonggyun.park@test.com', '박종균', 'test-oauth-id-015', 'KAKAO', 'USER', NULL, NOW(6), NOW(6));

INSERT IGNORE INTO `member` (id, email, name, oauth_id, oauth_provider, role, profile_img_file_id, created_at, updated_at)
VALUES (100, 'shortkki-dev1@gmail.com', '숏끼', 'test-oauth-id-001', 'GOOGLE', 'USER', 1, NOW(6), NOW(6));

-- 원본 컨텐츠 크리에이터
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 16:41:49.593935', 4, '2026-02-05 16:41:49.593935', '1분요리왕 통키',
        'UC-T8S0Vs2lvVXSkQv2qUStw',
        'https://yt3.ggpht.com/-aX1rKVeou12-L88s_kG4cfe48xtAfN6nVqTPOreLhH68Oi7V76pJKrOCx75PV6tLPkgYAq0Iw=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 16:49:00.642576', 5, '2026-02-05 16:49:00.642576', '맛수령',
        'UC1l27Mw8GuCbmK0Z5SzIyRw',
        'https://yt3.ggpht.com/rvOTrdNHgI2wZ-Mg18gemGCGmBQcOwZ8Mg5OqtG0cs_922ikLb8phC5q5Uo6y2im2UoqNrSUTA=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 16:53:52.945417', 6, '2026-02-05 16:53:52.945417', '이연복의 복주머니',
        'UCaAka9CN4naD3FzHX6AvpqA',
        'https://yt3.ggpht.com/4CNefptBEn0CyqDgWcf3nUO6uhkvRwa_ZneC7U0M50tFTcdZ3xO-nc5cdoqbRqSOWzGNZM7xWVQ=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 16:55:33.083638', 7, '2026-02-05 16:55:33.083638', '신내린셰프',
        'UC5WiWhX8n1N-r0HS_ocSiOA',
        'https://yt3.ggpht.com/O3oIHCjXg6Hw4rg22IGLeoS6qr0ZziPixt_o7AkrrDE74xR3gVe02-DBn6o0sl8xFSj6lVEu5g=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 16:56:59.065300', 8, '2026-02-05 16:56:59.065300', '은수저',
        'UC_BVJYxQxN5jberGxAFDlyg',
        'https://yt3.ggpht.com/u1Onel0_CJcyu1tTbfTeq83oRaEp8tMNNO7dslX2nxMCOiLeBH9MenOJljVEqaIroqmzkc7R=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 16:58:57.018257', 9, '2026-02-05 16:58:57.018257', '유지만 yuziman',
        'UCslni4iVQ-e784AU83eVcnQ',
        'https://yt3.ggpht.com/h9Vt4_UB_kTv6ijmpNN2Hw-Sv-2ACsIfI1rnEXDzx_6fDBVGeX2nJcDCEBgYBBrOJbmylUR4IeI=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 17:06:56.725810', 10, '2026-02-05 17:06:56.725810', '달밤요리_초코슬',
        'UCMzUq4AGzAfX49pXBvlfOnw',
        'https://yt3.ggpht.com/cLGyWjr_SsmD2uG-YZiKIcel_T0wO_VrkL_32xaypL2AqqeBbq7USl7QKG4mwQ9sStA7j6m-sA=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 17:12:45.665938', 11, '2026-02-05 17:12:45.665938', '소마카세',
        'UCWgP-kPT09r7Bl3-lGiRgpg',
        'https://yt3.ggpht.com/SRfcuhQ-nrlHLExg-eSnkJm405ijABjqGun_mRTTn3-OSrKuzCBXidRv4nGvu4AZrRX6Kog3Ng=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 17:27:20.754386', 12, '2026-02-05 17:27:20.754386', '아끼리',
        'UC466kSsqnP8q1f53bik7dDg',
        'https://yt3.ggpht.com/AEpcOwoK1AVpl1mGjFlzUsPGT0P7ZqhmihnYQMjGGWff_kkaoi6akQ0TEfxcgnQePhnW-RA0Zw=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 17:27:45.935940', 13, '2026-02-05 17:27:45.935940', '하루식탁 : 내몸을 지켜주는 건강한 식탁',
        'UCGxsJrR9741Utg_skfwNnCQ',
        'https://yt3.ggpht.com/ndrK-DHbSRwQd-jdaeCZ6eamCcw5TZqoHvOp4GOoz_AcCzwWN6PA0yQhsM49DoFszJv_ubAvPA4=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');
INSERT IGNORE INTO `source_content_creator` (`created_at`, `id`, `updated_at`, `display_name`,
                                             `external_key`, `profile_img_url`, `platform`)
VALUES ('2026-02-05 17:29:39.649239', 14, '2026-02-05 17:29:39.649239', '델리 테이블 Deli Table',
        'UCy-dWxG-tJnZN-bW6rFTf-w',
        'https://yt3.ggpht.com/LH_qXfDC74HAD2YtulzyMjXPUFfbpWxzs-H4Br8s06rqB8kLJBMk6JBD_Zsdpt_sfjXUxmtLbac=s88-c-k-c0x00ffffff-no-rj',
        'YOUTUBE');


-- 원본 컨텐츠
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:41:49.600759', 26, 4, '2026-02-05 16:41:49.600759', 'e_AdUSt4gkE',
        'https://www.youtube.com/shorts/e_AdUSt4gkE',
        'https://i.ytimg.com/vi/e_AdUSt4gkE/hqdefault.jpg', '제발그냥데치지마세요 고사리나물 황금레시피 #shrots',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:45:12.267962', 27, 4, '2026-02-05 16:45:12.267962', 'PGUhdvta16Y',
        'https://www.youtube.com/shorts/PGUhdvta16Y',
        'https://i.ytimg.com/vi/PGUhdvta16Y/hqdefault.jpg', '한정식집 양배추 흑임자드레싱 샐러드 #shrots', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:46:18.135380', 28, 4, '2026-02-05 16:46:18.135380', '1eWh49zqJmI',
        'https://www.youtube.com/shorts/1eWh49zqJmI',
        'https://i.ytimg.com/vi/1eWh49zqJmI/hqdefault.jpg',
        '조기구이 할때 식용유 절대 쓰지마세요! #생선구이 #조기구이 #shorts', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:46:25.792252', 29, 4, '2026-02-05 16:46:25.792252', 'tcwAwQcd4oE',
        'https://www.youtube.com/shorts/tcwAwQcd4oE',
        'https://i.ytimg.com/vi/tcwAwQcd4oE/hqdefault.jpg', '유명고깃집 파절이와 육전 #shrots', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:46:37.908962', 30, 4, '2026-02-05 16:46:37.908962', 'aKdWMEY6Rdw',
        'https://www.youtube.com/shorts/aKdWMEY6Rdw',
        'https://i.ytimg.com/vi/aKdWMEY6Rdw/hqdefault.jpg', '[⭐️100만⭐️] 명절에꼭만들어야하는 나박김치 #shrots',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:46:46.921124', 31, 4, '2026-02-05 16:46:46.921124', 'dRNb-3TiewI',
        'https://www.youtube.com/shorts/dRNb-3TiewI',
        'https://i.ytimg.com/vi/dRNb-3TiewI/hqdefault.jpg',
        '[⭐️70만⭐️] 양배추를 이렇게 만들었더니 너무 맛나요!! 간단하면서 매일 먹어도 질리지 않구요!', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:46:57.154563', 32, 4, '2026-02-05 16:46:57.154563', 'mN2MrravYMA',
        'https://www.youtube.com/shorts/mN2MrravYMA',
        'https://i.ytimg.com/vi/mN2MrravYMA/hqdefault.jpg',
        '[⭐️30만⭐️] 명절에는 입이 개운한 도라지무침 #shrots #명절반찬', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:47:06.311774', 33, 4, '2026-02-05 16:47:06.311774', 'ecBoonhnbrY',
        'https://www.youtube.com/shorts/ecBoonhnbrY',
        'https://i.ytimg.com/vi/ecBoonhnbrY/hqdefault.jpg', '[⭐️190만⭐️] 80인분 버섯잡채 황금레시피 #shorts',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:47:17.308748', 34, 4, '2026-02-05 16:47:17.308748', 'VAMsgVlNj_w',
        'https://www.youtube.com/shorts/VAMsgVlNj_w',
        'https://i.ytimg.com/vi/VAMsgVlNj_w/hqdefault.jpg',
        '[⭐️50만⭐️] 선재스님 고추간장 #흑백요리사2 #선재스님 #shorts', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:47:39.068525', 35, 4, '2026-02-05 16:47:39.068525', 'R1sdQQ5v5IE',
        'https://www.youtube.com/shorts/R1sdQQ5v5IE',
        'https://i.ytimg.com/vi/R1sdQQ5v5IE/hqdefault.jpg', '진짜 김치왕만두 #shrots', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:49:07.416210', 37, 5, '2026-02-05 16:49:07.416210', 'KxINtgqMaIs',
        'https://www.youtube.com/shorts/KxINtgqMaIs',
        'https://i.ytimg.com/vi/KxINtgqMaIs/hqdefault.jpg', '카다이프 스트레스 많이 받을거야', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:50:45.188323', 38, 5, '2026-02-05 16:50:45.188323', 'ZzESeKSZgf8',
        'https://www.youtube.com/shorts/ZzESeKSZgf8',
        'https://i.ytimg.com/vi/ZzESeKSZgf8/hqdefault.jpg', '여왕님이 드시던 궁중요리', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:50:52.681664', 39, 5, '2026-02-05 16:50:52.681664', 'bX5yYtzToZA',
        'https://www.youtube.com/shorts/bX5yYtzToZA',
        'https://i.ytimg.com/vi/bX5yYtzToZA/hqdefault.jpg', '다이어트를 진심으로 대하는 자세', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:51:15.224156', 40, 5, '2026-02-05 16:51:15.224156', 'XrPRKHjNNNw',
        'https://www.youtube.com/shorts/XrPRKHjNNNw',
        'https://i.ytimg.com/vi/XrPRKHjNNNw/hqdefault.jpg', '최강록 욕망의 조림... 근데 이제 홍어애랑 트러플만 곁들인...',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:53:52.947005', 41, 6, '2026-02-05 16:53:52.947005', 'FFDDNX5XTfM',
        'https://www.youtube.com/shorts/FFDDNX5XTfM',
        'https://i.ytimg.com/vi/FFDDNX5XTfM/hqdefault.jpg', '이연복셰프의 두부계란볶음#shorts #레시피 #먹방 #이연복',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:54:01.430794', 42, 6, '2026-02-05 16:54:01.430794', 'XrjCPcaqm_8',
        'https://www.youtube.com/shorts/XrjCPcaqm_8',
        'https://i.ytimg.com/vi/XrjCPcaqm_8/hqdefault.jpg', '이연복셰프의 오이무침#shorts #레시피 #먹방 #이연복',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:54:21.768506', 43, 6, '2026-02-05 16:54:21.768506', 'YA14AeYS3vw',
        'https://www.youtube.com/shorts/YA14AeYS3vw',
        'https://i.ytimg.com/vi/YA14AeYS3vw/hqdefault.jpg', '이연복셰프의 계란덮밥 #shorts #레시피 #먹방 #이연복',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:55:33.085798', 44, 7, '2026-02-05 16:55:33.085798', 'y-13uymxwLE',
        'https://www.youtube.com/shorts/y-13uymxwLE',
        'https://i.ytimg.com/vi/y-13uymxwLE/hqdefault.jpg', '이탈리안보양식 토마토비프스튜 쉽게 만드는 방법', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:55:44.265649', 45, 7, '2026-02-05 16:55:44.265649', 'S2JFyoQzkAg',
        'https://www.youtube.com/shorts/S2JFyoQzkAg',
        'https://i.ytimg.com/vi/S2JFyoQzkAg/hqdefault.jpg', '스트레스 싹! 풀리는 쫀득~한 국물닭발🐔', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:55:53.917633', 46, 7, '2026-02-05 16:55:53.917633', 'Q2bXdS9Po5Q',
        'https://www.youtube.com/shorts/Q2bXdS9Po5Q',
        'https://i.ytimg.com/vi/Q2bXdS9Po5Q/hqdefault.jpg', '푸딩계란찜이 5분만에 된다고?!🥚', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:56:59.066055', 47, 8, '2026-02-05 16:56:59.066055', '4AzWcw_u_Zc',
        'https://www.youtube.com/shorts/4AzWcw_u_Zc',
        'https://i.ytimg.com/vi/4AzWcw_u_Zc/hqdefault.jpg', '양식 조리사 도전기 3일차, 쉬림프 카나페', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:57:29.159124', 48, 8, '2026-02-05 16:57:29.159124', 'fiRjI4zmLSs',
        'https://www.youtube.com/shorts/fiRjI4zmLSs',
        'https://i.ytimg.com/vi/fiRjI4zmLSs/hqdefault.jpg', '농심 40주년 신라면 골드가 출시했습니다 과연 그 맛은???',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:57:36.839375', 49, 8, '2026-02-05 16:57:36.839375', 'YeZTlhp00Zo',
        'https://www.youtube.com/shorts/YeZTlhp00Zo',
        'https://i.ytimg.com/vi/YeZTlhp00Zo/hqdefault.jpg',
        '용형의 짜장은 과연 맛있을까 @일하는용형', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:57:54.224004', 50, 8, '2026-02-05 16:57:54.224004', 'CunLRJHOtjs',
        'https://www.youtube.com/shorts/CunLRJHOtjs',
        'https://i.ytimg.com/vi/CunLRJHOtjs/hqdefault.jpg', '1. 토마토 파스타 쉽게 알려드립니다', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:58:13.485914', 51, 8, '2026-02-05 16:58:13.485914', 'APYw4JDlzvM',
        'https://www.youtube.com/shorts/APYw4JDlzvM',
        'https://i.ytimg.com/vi/APYw4JDlzvM/hqdefault.jpg', '단 한가지만 더 추가하면 대단해지는 옥동식 곰탕', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:58:23.977757', 52, 8, '2026-02-05 16:58:23.977757', 'fCBSBUWSwRA',
        'https://www.youtube.com/shorts/fCBSBUWSwRA',
        'https://i.ytimg.com/vi/fCBSBUWSwRA/hqdefault.jpg', '은수저의 근본 알리오 올리오', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:58:57.020138', 53, 9, '2026-02-05 16:58:57.020138', 'sn6bI4bSYGw',
        'https://www.youtube.com/shorts/sn6bI4bSYGw',
        'https://i.ytimg.com/vi/sn6bI4bSYGw/hqdefault.jpg', '그놈의 두쫀쿠가 뭐라고', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:59:14.210093', 54, 9, '2026-02-05 16:59:14.210093', 'Leiz0i5do5I',
        'https://www.youtube.com/shorts/Leiz0i5do5I',
        'https://i.ytimg.com/vi/Leiz0i5do5I/hqdefault.jpg', '새해 다이어트 결심한 분 들어오세요', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:59:21.315793', 55, 9, '2026-02-05 16:59:21.315793', 'Viq5soXmVO4',
        'https://www.youtube.com/shorts/Viq5soXmVO4',
        'https://i.ytimg.com/vi/Viq5soXmVO4/hqdefault.jpg', '일본 여행 안 가도 되는 맛', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:59:31.451103', 56, 9, '2026-02-05 16:59:31.451103', 'N_3V2x5NWSA',
        'https://www.youtube.com/shorts/N_3V2x5NWSA',
        'https://i.ytimg.com/vi/N_3V2x5NWSA/hqdefault.jpg', '바쁜 연말에 식단이 10배 쉬워지는 법', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 16:59:47.275866', 57, 9, '2026-02-05 16:59:47.275866', 'rXYAViv5_LQ',
        'https://www.youtube.com/shorts/rXYAViv5_LQ',
        'https://i.ytimg.com/vi/rXYAViv5_LQ/hqdefault.jpg', '크래미 100배 맛있게 먹는 법', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:03:13.334232', 58, 9, '2026-02-05 17:03:13.334232', 'pIqJyaKUcG4',
        'https://www.youtube.com/shorts/pIqJyaKUcG4',
        'https://i.ytimg.com/vi/pIqJyaKUcG4/hqdefault.jpg', '이것만큼은 채널 걸고 추천합니다.', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:05:16.356921', 59, 9, '2026-02-05 17:05:16.356921', 'jdNi4LLBopk',
        'https://www.youtube.com/shorts/jdNi4LLBopk',
        'https://i.ytimg.com/vi/jdNi4LLBopk/hqdefault.jpg', '딱 하나만 고르시오.(10점)', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:05:34.311435', 60, 9, '2026-02-05 17:05:34.311435', 'uxaADfZYq3A',
        'https://www.youtube.com/shorts/uxaADfZYq3A',
        'https://i.ytimg.com/vi/uxaADfZYq3A/hqdefault.jpg', '겉바속쫀의 정석', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:06:01.292304', 61, 9, '2026-02-05 17:06:01.292304', 'Mqsr5rtISd8',
        'https://www.youtube.com/shorts/Mqsr5rtISd8',
        'https://i.ytimg.com/vi/Mqsr5rtISd8/hqdefault.jpg', '만 오천 원 주고도 사 먹을 맛', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:06:33.037961', 62, 9, '2026-02-05 17:06:33.037961', '_uKOqmQSKhc',
        'https://www.youtube.com/shorts/_uKOqmQSKhc',
        'https://i.ytimg.com/vi/_uKOqmQSKhc/hqdefault.jpg', '배달 앱 삭제시킬', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:06:56.726760', 63, 10, '2026-02-05 17:06:56.726760', 'xUPLuJt-lKU',
        'https://www.youtube.com/shorts/xUPLuJt-lKU',
        'https://i.ytimg.com/vi/xUPLuJt-lKU/hqdefault.jpg',
        '영화 속 요리 줄리앤줄리아에 나온 프랑스 가정식 \'솔 뫼니에르\' French Home Cooking Sole Meuniere #프랑스요리 #고든램지 #가자미구이',
        'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:07:09.726771', 64, 10, '2026-02-05 17:07:09.726771', 'xXHusOBcseU',
        'https://www.youtube.com/shorts/xXHusOBcseU',
        'https://i.ytimg.com/vi/xXHusOBcseU/hqdefault.jpg',
        '꾸덕 꾸덕 크리미한 리얼 까르보나라 레시피 Carbonara Pasta #파스타 #cooking #자취요리', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:07:17.871680', 65, 10, '2026-02-05 17:07:17.871680', '7QP5N3aq-4c',
        'https://www.youtube.com/shorts/7QP5N3aq-4c',
        'https://i.ytimg.com/vi/7QP5N3aq-4c/hqdefault.jpg',
        '대파가 듬뿍 들어간 육즙 팡팡 고기만두 초간단 레시피 Green onion meat dumplings #대파 #kfood #명절음식', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:07:28.313839', 66, 10, '2026-02-05 17:07:28.313839', 'ZYxOyzXxilE',
        'https://www.youtube.com/shorts/ZYxOyzXxilE',
        'https://i.ytimg.com/vi/ZYxOyzXxilE/hqdefault.jpg',
        '이연복 셰프의 건강하고 살빠지는 \"알배추찜\" 레시피 Steamed cabbage #배추요리 #냉장고를부탁해 #레시피를부탁해', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:07:34.984882', 67, 10, '2026-02-05 17:07:34.984882', 'XV3jmiHlJCI',
        'https://www.youtube.com/shorts/XV3jmiHlJCI',
        'https://i.ytimg.com/vi/XV3jmiHlJCI/hqdefault.jpg',
        '육즙 가득 무수분 대파수육 Boiled Pork Belly with Green Onion #수육 #kfood #삼겹살', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:07:49.522823', 68, 10, '2026-02-05 17:07:49.522823', 'qmnYM3XnD84',
        'https://www.youtube.com/shorts/qmnYM3XnD84',
        'https://i.ytimg.com/vi/qmnYM3XnD84/hqdefault.jpg',
        '촉촉한 카스테라 프렌치 토스트 Castella French Toast #토스트 #디저트 #레시피', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:07:57.528620', 69, 10, '2026-02-05 17:07:57.528620', 'NJ_1YyS0sl0',
        'https://www.youtube.com/shorts/NJ_1YyS0sl0',
        'https://i.ytimg.com/vi/NJ_1YyS0sl0/hqdefault.jpg',
        '맛있는 스팸 더 맛있게 먹기! 초간단 무스비  레시피  #musubi #kimbap #초간단요리', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:08:14.259107', 70, 10, '2026-02-05 17:08:14.259107', '_b3usRqYtYE',
        'https://www.youtube.com/shorts/_b3usRqYtYE',
        'https://i.ytimg.com/vi/_b3usRqYtYE/hqdefault.jpg',
        '[달밤요리] 트러플향 솔솔 감칠맛 최고 오이 소금 김밥 Truffle Salt Cucumber Kimbap #김밥 #야식 #kfood', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:09:07.819198', 71, 10, '2026-02-05 17:09:07.819198', 'ZpjO8lM_1PQ',
        'https://www.youtube.com/shorts/ZpjO8lM_1PQ',
        'https://i.ytimg.com/vi/ZpjO8lM_1PQ/hqdefault.jpg',
        '[달밤요리] 허브 페스토 파스타 레시피 Herb Pesto Pasta #파스타 #흑백요리사 #트리플스타', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:09:40.680455', 72, 10, '2026-02-05 17:09:40.680455', '5VmDZ_pqfVE',
        'https://www.youtube.com/shorts/5VmDZ_pqfVE',
        'https://i.ytimg.com/vi/5VmDZ_pqfVE/hqdefault.jpg',
        '[달밤요리] 흑백요리사 트리플스타님의 허브볶음밥 #흑백요리사 #파인다이닝 #cooking', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:11:41.887124', 73, 10, '2026-02-05 17:11:41.887124', '5qBfts30QOg',
        'https://www.youtube.com/shorts/5qBfts30QOg',
        'https://i.ytimg.com/vi/5qBfts30QOg/hqdefault.jpg',
        '[달밤요리] 방울토마토 절임을 활용한 카프레제 샐러드 #간단요리 #cooking #다이어트식단 #레시피 #플레이팅', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:12:45.667515', 74, 11, '2026-02-05 17:12:45.667515', 'QhDBclLAOPQ',
        'https://www.youtube.com/shorts/QhDBclLAOPQ',
        'https://i.ytimg.com/vi/QhDBclLAOPQ/hqdefault.jpg', '2시간이나 걸리는 동파육', 'VIDEO', 'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:26:40.748541', 75, 5, '2026-02-05 17:26:40.748541', 'jN7F_muuK9s',
        'https://www.youtube.com/shorts/jN7F_muuK9s',
        'https://i.ytimg.com/vi/jN7F_muuK9s/hqdefault.jpg', '흑백요리사에 강림 한 대파 천재', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:27:20.756155', 76, 12, '2026-02-05 17:27:20.756155', 'c3GTYnc8jec',
        'https://www.youtube.com/shorts/c3GTYnc8jec',
        'https://i.ytimg.com/vi/c3GTYnc8jec/hqdefault.jpg', '(스포) 흑백요리사 에드워드리 나머지 떡볶이 ㅠㅠ', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:27:45.937309', 77, 13, '2026-02-05 17:27:45.937309', 'hhl1CnS1Y68',
        'https://www.youtube.com/shorts/hhl1CnS1Y68',
        'https://i.ytimg.com/vi/hhl1CnS1Y68/hqdefault.jpg', '흑백요리사 최강록쉐프 무스테이크 이런걸까', 'VIDEO',
        'YOUTUBE');
INSERT IGNORE INTO `source_content` (`created_at`, `id`, `source_author_id`,
                                     `updated_at`,
                                     `external_key`, `canonical_url`, `thumbnail_url`, `title`,
                                     `content_type`, `platform`)
VALUES ('2026-02-05 17:29:39.650293', 78, 14, '2026-02-05 17:29:39.650293', 'cp0u5Da75zM',
        'https://www.youtube.com/shorts/cp0u5Da75zM',
        'https://i.ytimg.com/vi/cp0u5Da75zM/hqdefault.jpg', '흑백요리사2 치킨 프리카세 만들어 봤어요🥘✨', 'VIDEO',
        'YOUTUBE');


-- 레시피
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (22,40,1,4,'2026-02-05 16:42:30.828213',31,NULL,100,26,'2026-02-05 16:42:30.828213','고사리나물','밀가루를 활용해 고사리의 쓴맛을 제거하고 부드러운 식감을 살린 고소한 고사리나물 볶음입니다.','KOREAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (885,10,1,2,'2026-02-05 16:45:46.369007',32,NULL,100,27,'2026-02-05 16:45:46.369007','흑임자 양배추 샐러드','마요네즈 대신 그릭요거트와 흑임자를 사용하여 고소하고 담백한 맛을 낸 건강한 한식 스타일 샐러드입니다.','KOREAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (654,30,1,2,'2026-02-05 16:46:50.062689',33,NULL,100,28,'2026-02-05 16:46:50.062689','조기구이','에어프라이어를 활용해 비린내를 완벽히 잡고 겉은 바삭, 속은 촉촉하게 굽는 조기구이 레시피입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (319,30,1,2,'2026-02-05 16:47:03.240151',34,NULL,100,29,'2026-02-05 16:47:03.240151','돼지고기 육전','고소한 돼지고기 육전과 매콤달콤한 파절이를 곁들여 풍미를 더한 요리입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (755,60,1,10,'2026-02-05 16:47:17.292712',35,NULL,100,30,'2026-02-05 16:47:17.292712','나박김치','명절에 어울리는 시원하고 개운한 맛의 전통 물김치입니다.','KOREAN','INTERMEDIATE','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (250,45,1,4,'2026-02-05 16:47:20.381515',36,NULL,100,31,'2026-02-05 16:47:20.381515','양배추 나물','담백하고 고소하여 샐러드나 반찬으로 먹기 좋으며 다이어트에도 효과적인 양배추 요리입니다.','KOREAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (35,50,1,2,'2026-02-05 16:47:43.826069',37,NULL,100,32,'2026-02-05 16:47:43.826069','도라지 오이무침','쓴맛을 뺀 도라지와 아삭한 오이를 새콤달콤한 양념에 무쳐 입맛을 돋우는 반찬입니다.','KOREAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (7,40,1,4,'2026-02-05 16:47:48.530047',38,NULL,100,34,'2026-02-05 16:47:48.530047','고추간장','청양고추의 알싸한 맛과 다시마 육수의 감칠맛이 어우러진 만능 양념장입니다.','KOREAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (63,150,1,80,'2026-02-05 16:47:51.607687',39,NULL,100,33,'2026-02-05 16:47:51.607687','버섯잡채','호텔 주방장의 비법을 담아 대용량으로 만들어도 면이 불지 않고 채소의 식감이 살아있는 버섯잡채입니다.','KOREAN','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (94,40,1,4,'2026-02-05 16:48:21.773751',40,NULL,100,35,'2026-02-05 16:48:21.773751','김치왕만두','밀가루 피 대신 묵은지로 만두소를 감싸 깊은 맛을 내는 이색적인 김치만두 전골 요리입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (320,20,1,2,'2026-02-05 16:49:39.702630',42,NULL,100,37,'2026-02-05 16:49:39.702630','크나페','카다이프 면과 모짜렐라 치즈를 층층이 쌓아 버터에 구운 뒤 달콤한 시럽을 곁들인 아랍식 디저트입니다.','ASIAN','BEGINNER','DESSERT','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (120,30,1,2,'2026-02-05 16:51:21.758719',43,NULL,100,38,'2026-02-05 16:51:21.758719','안심 스테이크','영국 엘리자베스 2세 여왕이 즐겨 먹던 방식의 스테이크로, 크림 소스와 버섯을 곁들인 요리입니다.','WESTERN','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (584,40,1,2,'2026-02-05 16:51:28.584032',44,NULL,100,39,'2026-02-05 16:51:28.584032','감자튀김','허브와 함께 삶아낸 감자를 세 번 튀겨내어 겉은 바삭하고 속은 포슬포슬한 식감을 극대화한 요리입니다.','WESTERN','INTERMEDIATE','SNACK','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (326,60,1,2,'2026-02-05 16:51:50.326392',45,NULL,100,40,'2026-02-05 16:51:50.326392','홍어애 트러플 덮밥','고급 식재료인 홍어애와 트러플을 일본식 조림 기법으로 조리하여 밥 위에 얹어 먹는 독특한 퓨전 요리입니다.','FUSION','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (394,15,1,2,'2026-02-05 16:54:23.282934',46,NULL,100,41,'2026-02-05 16:54:23.282934','두부 계란 볶음','두부와 계란을 활용해 부드럽고 짭조름한 맛을 낸 이연복 셰프의 간단한 볶음 요리입니다.','ASIAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (213,10,1,2,'2026-02-05 16:54:30.857809',47,NULL,100,42,'2026-02-05 16:54:30.857809','중식 오이무침','불을 사용하지 않고 두드린 오이에 양념을 버무려 만드는 새콤달콤한 중식 스타일 오이 요리입니다.','ASIAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (25,15,1,1,'2026-02-05 16:54:51.924432',48,NULL,100,43,'2026-02-05 16:54:51.924432','계란덮밥','이연복 셰프의 비법이 담긴 중식 스타일의 계란덮밥으로, 간단한 재료로 풍성한 맛을 낼 수 있는 한 그릇 요리입니다.','CHINESE','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (66,70,1,2,'2026-02-05 16:56:03.928635',49,NULL,100,44,'2026-02-05 16:56:03.928635','소고기 토마토 스튜','소고기와 채소를 토마토 홀과 함께 푹 끓여내어 깊은 맛을 내는 프랑스식 건강 스튜입니다.','WESTERN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (875,40,1,2,'2026-02-05 16:56:22.103037',50,NULL,100,45,'2026-02-05 16:56:22.103037','국물 닭발','쫀득한 무뼈 닭발과 시원한 콩나물 육수가 어우러진 매콤한 국물 요리입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1054,10,1,2,'2026-02-05 16:56:30.899229',51,NULL,100,46,'2026-02-05 16:56:30.899229','계란찜','전자레인지를 사용하여 5분 만에 완성하는 푸딩처럼 부드러운 식감의 계란찜입니다.','KOREAN','BEGINNER','SIDE_DISH','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1036,30,1,2,'2026-02-05 16:57:36.068448',52,NULL,100,47,'2026-02-05 16:57:36.068448','쉬림프 카나페','바삭하게 구운 식빵 위에 완숙 계란과 새우를 올려 한 입에 즐기는 서양식 핑거푸드입니다.','WESTERN','BEGINNER','SNACK','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1456,5,1,1,'2026-02-05 16:58:08.028567',53,NULL,100,48,'2026-02-05 16:58:08.028567','신라면 골드','강황 성분을 함유하여 더욱 깊고 칼칼한 국물 맛을 자랑하는 신라면 골드 조리법입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (133,30,1,1,'2026-02-05 16:58:38.371164',54,NULL,100,50,'2026-02-05 16:58:38.371164','토마토 파스타','신선한 방울토마토와 토마토 퓨레를 활용해 깊고 진한 풍미를 낸 정통 토마토 파스타입니다.','WESTERN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (565,100,1,2,'2026-02-05 16:58:57.824077',55,NULL,100,51,'2026-02-05 16:58:57.824077','돼지 곰탕','깊고 맑은 육수와 부드러운 돼지고기가 어우러진 깔끔한 맛의 돼지 곰탕입니다.','KOREAN','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1004,15,1,1,'2026-02-05 16:59:04.629656',56,NULL,100,52,'2026-02-05 16:59:04.629656','엔초비 알리오 올리오','엔초비 페이스트로 감칠맛을 더하고 유화 과정을 통해 촉촉함을 살린 알리오 올리오 파스타입니다.','WESTERN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (3629,60,1,4,'2026-02-05 16:59:36.021568',57,NULL,100,53,'2026-02-05 16:59:36.021568','다이어트 두쫀쿠','마시멜로와 버터 없이 만든 건강하고 바삭한 저칼로리 초코 디저트 레시피입니다.','FUSION','INTERMEDIATE','DESSERT','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (468,20,1,1,'2026-02-05 16:59:45.749970',58,NULL,100,54,'2026-02-05 16:59:45.749970','데리야끼 새우 포케','탱글한 새우와 신선한 채소를 곁들인 건강하고 든든한 다이어트용 한 그릇 요리입니다.','ASIAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (532,15,1,1,'2026-02-05 16:59:52.396722',59,NULL,100,55,'2026-02-05 16:59:52.396722','야키소바빵','양배추와 우삼겹을 데리야끼 소스에 볶아 빵 위에 얹어 먹는 일본식 샌드위치입니다.','JAPANESE','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1871,20,1,4,'2026-02-05 17:00:02.241608',60,NULL,100,56,'2026-02-05 17:00:02.241608','참치마요 밥버거','전자레인지로 간편하게 만드는 추억의 참치마요 밥버거 밀프레프 레시피입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (102,15,1,1,'2026-02-05 17:00:16.592915',61,NULL,100,57,'2026-02-05 17:00:16.592915','게살볶음밥','크래미를 활용하여 집에서도 쉽고 맛있게 만들 수 있는 간단한 볶음밥 레시피입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (355,30,1,4,'2026-02-05 17:03:56.278689',62,NULL,100,58,'2026-02-05 17:03:56.278689','샐러드 파스타','닭가슴살과 새우, 신선한 채소를 곁들여 건강하고 든든하게 즐기는 다이어트용 밀프렙 요리입니다.','FUSION','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (630,15,1,1,'2026-02-05 17:05:50.093294',63,NULL,100,59,'2026-02-05 17:05:50.093294','김치말이국수','시원하고 새콤한 김치 국물에 쌀소면을 말아 먹는 여름철 별미 요리입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (884,35,1,1,'2026-02-05 17:06:06.557890',64,NULL,100,60,'2026-02-05 17:06:06.557890','닭가슴살 감자 쿠키','감자와 닭가슴살을 잘게 썰어 오븐에 구워낸 겉바속쫀 식감의 고단백 영양 간식입니다.','FUSION','BEGINNER','SNACK','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1728,20,1,1,'2026-02-05 17:06:34.650242',65,NULL,100,61,'2026-02-05 17:06:34.650242','명란 크림 파스타','생크림 없이 우유와 치즈만으로 꾸덕한 소스를 구현한 고소하고 짭조름한 퓨전 파스타입니다.','FUSION','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (201,20,1,1,'2026-02-05 17:07:28.634192',66,NULL,100,63,'2026-02-05 17:07:28.634192','솔 뫼니에르','버터의 풍미와 가자미의 담백함이 조화를 이루는 프랑스식 생선 요리입니다.','WESTERN','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (1132,15,1,1,'2026-02-05 17:07:28.832216',67,NULL,100,62,'2026-02-05 17:07:28.832216','컵누들 짬뽕','컵누들 짬뽕맛에 신선한 채소와 해산물을 더해 중국집 짬뽕 부럽지 않은 깊은 맛을 내는 든든한 한 끼 레시피입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (67,20,1,2,'2026-02-05 17:07:35.016799',68,NULL,100,64,'2026-02-05 17:07:35.016799','까르보나라','생크림 없이 계란 노른자와 치즈로만 맛을 낸 꾸덕한 정통 이탈리아식 파스타입니다.','WESTERN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (256,20,1,2,'2026-02-05 17:08:01.386261',69,NULL,100,66,'2026-02-05 17:08:01.386261','알배추찜','이연복 셰프의 레시피를 활용한 아삭하고 건강한 배추찜 요리입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (84,50,1,2,'2026-02-05 17:08:06.729977',70,NULL,100,67,'2026-02-05 17:08:06.729977','무수분 대파수육','물을 한 방울도 넣지 않고 대파와 양파의 채수로만 익혀 육즙과 풍미가 가득한 수육 레시피입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (23,15,1,2,'2026-02-05 17:08:17.616216',71,NULL,100,68,'2026-02-05 17:08:17.616216','카스테라 프렌치 토스트','식빵 대신 카스테라를 활용해 입안에서 녹는 부드러움과 달콤함을 극대화한 프렌치 토스트입니다.','WESTERN','BEGINNER','SNACK','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (88,20,1,2,'2026-02-05 17:08:26.787620',72,NULL,100,69,'2026-02-05 17:08:26.787620','스팸 무스비','깻잎과 오이를 더해 느끼함을 잡고 아삭한 식감을 살린 고소한 스팸 무스비입니다.','KOREAN','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (102,15,1,1,'2026-02-05 17:08:44.169372',73,NULL,100,70,'2026-02-05 17:08:44.169372','오이 소금 김밥','아삭한 오이와 고소한 트러플 향이 조화로운 이색적인 한입 김밥입니다.','KOREAN','BEGINNER','SNACK','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (584,15,1,1,'2026-02-05 17:09:36.067197',74,NULL,100,71,'2026-02-05 17:09:36.067197','허브 원팬 파스타','허브 페스토를 활용하여 팬 하나로 간편하게 완성하는 향긋하고 크리미한 파스타입니다.','FUSION','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (631,20,1,1,'2026-02-05 17:10:15.931976',75,NULL,100,72,'2026-02-05 17:10:15.931976','허브 볶음밥','향긋한 허브 페스토와 씻은 김치가 조화를 이루는 흑백요리사 트리플스타 스타일의 이색 볶음밥입니다.','FUSION','BEGINNER','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (225,15,1,2,'2026-02-05 17:12:10.753049',77,NULL,100,73,'2026-02-05 17:12:10.753049','토마토 절임 카프레제','상큼한 토마토 절임과 모차렐라 치즈, 달콤한 파파할 멜론을 곁들인 고급스러운 샐러드입니다.','WESTERN','BEGINNER','SNACK','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (921,130,1,2,'2026-02-05 17:13:15.874994',78,NULL,100,74,'2026-02-05 17:13:15.874994','동파육','삼겹살을 간장 소스에 장시간 졸여 부드러운 식감과 깊은 풍미를 살린 중식 요리입니다.','CHINESE','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (25,60,1,2,'2026-02-05 17:28:03.984712',79,NULL,100,76,'2026-02-05 17:28:03.984712','떡볶이 디저트','에드워드 리 셰프의 레시피를 재현한 요리로, 떡 퓨레와 머랭을 활용해 만든 이색적인 세미 프레도 디저트입니다.','FUSION','INTERMEDIATE','DESSERT','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (33,60,1,2,'2026-02-05 17:28:24.308131',80,NULL,100,77,'2026-02-05 17:28:24.308131','무 스테이크','부드럽게 삶은 무를 들기름에 노릇하게 굽고, 소스를 곁들여 먹는 고급스러운 채소 스테이크입니다.','FUSION','INTERMEDIATE','MAIN','IMPORT');
INSERT IGNORE INTO `recipe` (`bookmark_count`,`cooking_time`,`is_active`,`serving_size`,`created_at`,`id`,`main_img_file_id`,`member_id`,`source_content_id`,`updated_at`,`title`,`description`,`cuisine_type`,`difficulty`,`meal_type`,`source_type`) VALUES (865,45,1,2,'2026-02-05 17:30:18.000545',81,NULL,100,78,'2026-02-05 17:30:18.000545','치킨 프리카세','부드러운 크림 소스에 명란의 감칠맛을 더한 프랑스식 닭고기 스튜 요리입니다.','FUSION','INTERMEDIATE','MAIN','IMPORT');

-- 태그
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 1, '2026-02-05 16:09:04.312256', '집밥', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 2, '2026-02-05 16:09:04.312256', '초간단', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 3, '2026-02-05 16:09:04.312256', '자취', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 4, '2026-02-05 16:09:04.312256', '밥도둑', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 5, '2026-02-05 16:09:04.312256', '국물요리', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 6, '2026-02-05 16:09:04.312256', '찌개', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 7, '2026-02-05 16:09:04.312256', '볶음', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 8, '2026-02-05 16:09:04.312256', '반찬', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 9, '2026-02-05 16:09:04.312256', '한그릇', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 10, '2026-02-05 16:09:04.312256', '매운맛', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 11, '2026-02-05 16:09:04.312256', '칼칼', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 12, '2026-02-05 16:09:04.312256', '담백', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 13, '2026-02-05 16:09:04.312256', '다이어트', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 14, '2026-02-05 16:09:04.312256', '단백질', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 15, '2026-02-05 16:09:04.312256', '면요리', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 16, '2026-02-05 16:09:04.312256', '파스타', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 17, '2026-02-05 16:09:04.312256', '오일파스타', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 18, '2026-02-05 16:09:04.312256', '토마토', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 19, '2026-02-05 16:09:04.312256', '치즈', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 20, '2026-02-05 16:09:04.312256', '일식', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 21, '2026-02-05 16:09:04.312256', '덮밥', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 22, '2026-02-05 16:09:04.312256', '중식', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 23, '2026-02-05 16:09:04.312256', '아시안', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 24, '2026-02-05 16:09:04.312256', '분식', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 25, '2026-02-05 16:09:04.312256', '간식', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 26, '2026-02-05 16:09:04.312256', '주말요리', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 27, '2026-02-05 16:09:04.312256', '손님상', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 28, '2026-02-05 16:09:04.312256', '도시락', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 29, '2026-02-05 16:09:04.312256', '해산물', 'SYSTEM');
INSERT IGNORE INTO tag (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:09:04.312256', 30, '2026-02-05 16:09:04.312256', '샐러드', 'SYSTEM');
INSERT IGNORE INTO `tag` (`created_at`, `id`, `updated_at`, `name`, `source_type`)
VALUES ('2026-02-05 16:42:30.852276', 31, '2026-02-05 16:42:30.852276', '한식', 'SYSTEM'),
       ('2026-02-05 16:42:30.854717', 32, '2026-02-05 16:42:30.854717', '나물', 'SYSTEM'),
       ('2026-02-05 16:42:30.856359', 33, '2026-02-05 16:42:30.856359', '밑반찬', 'SYSTEM'),
       ('2026-02-05 16:42:30.857438', 34, '2026-02-05 16:42:30.857438', '명절음식', 'SYSTEM'),
       ('2026-02-05 16:45:46.384586', 35, '2026-02-05 16:45:46.384586', '건강식', 'SYSTEM'),
       ('2026-02-05 16:45:46.387602', 36, '2026-02-05 16:45:46.387602', '홈파티', 'SYSTEM'),
       ('2026-02-05 16:46:50.072725', 37, '2026-02-05 16:46:50.072725', '생선구이', 'SYSTEM'),
       ('2026-02-05 16:46:50.073751', 38, '2026-02-05 16:46:50.073751', '에어프라이어', 'SYSTEM'),
       ('2026-02-05 16:47:03.270788', 39, '2026-02-05 16:47:03.270788', '메인요리', 'SYSTEM'),
       ('2026-02-05 16:47:03.271835', 40, '2026-02-05 16:47:03.271835', '안주', 'SYSTEM'),
       ('2026-02-05 16:47:03.273226', 41, '2026-02-05 16:47:03.273226', '전', 'SYSTEM'),
       ('2026-02-05 16:47:17.319043', 42, '2026-02-05 16:47:17.319043', '김치', 'SYSTEM'),
       ('2026-02-05 16:47:17.321376', 43, '2026-02-05 16:47:17.321376', '물김치', 'SYSTEM'),
       ('2026-02-05 16:47:20.401963', 44, '2026-02-05 16:47:20.401963', '다이어트식단', 'SYSTEM'),
       ('2026-02-05 16:47:20.404418', 45, '2026-02-05 16:47:20.404418', '채소요리', 'SYSTEM'),
       ('2026-02-05 16:47:43.842448', 46, '2026-02-05 16:47:43.842448', '무침', 'SYSTEM'),
       ('2026-02-05 16:47:48.542682', 47, '2026-02-05 16:47:48.542682', '양념장', 'SYSTEM'),
       ('2026-02-05 16:47:48.543836', 48, '2026-02-05 16:47:48.543836', '만능장', 'SYSTEM'),
       ('2026-02-05 16:47:48.544658', 49, '2026-02-05 16:47:48.544658', '비빔밥', 'SYSTEM'),
       ('2026-02-05 16:47:51.625356', 50, '2026-02-05 16:47:51.625356', '대용량', 'SYSTEM'),
       ('2026-02-05 16:48:21.801936', 51, '2026-02-05 16:48:21.801936', '만두전골', 'SYSTEM'),
       ('2026-02-05 16:48:21.803138', 52, '2026-02-05 16:48:21.803138', '이색요리', 'SYSTEM'),
       ('2026-02-05 16:49:34.119668', 53, '2026-02-05 16:49:34.119668', '볶음밥', 'SYSTEM'),
       ('2026-02-05 16:49:34.121436', 54, '2026-02-05 16:49:34.121436', '간편식', 'SYSTEM'),
       ('2026-02-05 16:49:39.712022', 55, '2026-02-05 16:49:39.712022', '이색디저트', 'SYSTEM'),
       ('2026-02-05 16:49:39.712874', 56, '2026-02-05 16:49:39.712874', '중동요리', 'SYSTEM'),
       ('2026-02-05 16:49:39.713618', 57, '2026-02-05 16:49:39.713618', '달콤한', 'SYSTEM'),
       ('2026-02-05 16:49:39.714417', 58, '2026-02-05 16:49:39.714417', '홈카페', 'SYSTEM'),
       ('2026-02-05 16:51:21.770203', 59, '2026-02-05 16:51:21.770203', '영국요리', 'SYSTEM'),
       ('2026-02-05 16:51:21.770751', 60, '2026-02-05 16:51:21.770751', '스테이크', 'SYSTEM'),
       ('2026-02-05 16:51:21.771385', 61, '2026-02-05 16:51:21.771385', '양식', 'SYSTEM'),
       ('2026-02-05 16:51:21.772019', 62, '2026-02-05 16:51:21.772019', '궁중요리', 'SYSTEM'),
       ('2026-02-05 16:51:28.601000', 63, '2026-02-05 16:51:28.601000', '튀김요리', 'SYSTEM'),
       ('2026-02-05 16:51:28.602296', 64, '2026-02-05 16:51:28.602296', '홈쿡', 'SYSTEM'),
       ('2026-02-05 16:51:50.346863', 65, '2026-02-05 16:51:50.346863', '퓨전', 'SYSTEM'),
       ('2026-02-05 16:51:50.347885', 66, '2026-02-05 16:51:50.347885', '고급요리', 'SYSTEM'),
       ('2026-02-05 16:54:23.307062', 67, '2026-02-05 16:54:23.307062', '간단요리', 'SYSTEM'),
       ('2026-02-05 16:54:23.308138', 68, '2026-02-05 16:54:23.308138', '한그릇요리', 'SYSTEM'),
       ('2026-02-05 16:54:30.874388', 69, '2026-02-05 16:54:30.874388', '오이요리', 'SYSTEM'),
       ('2026-02-05 16:54:30.876582', 70, '2026-02-05 16:54:30.876582', '간편요리', 'SYSTEM'),
       ('2026-02-05 16:56:03.941359', 71, '2026-02-05 16:56:03.941359', '프랑스요리', 'SYSTEM'),
       ('2026-02-05 16:56:03.942935', 72, '2026-02-05 16:56:03.942935', '스튜', 'SYSTEM'),
       ('2026-02-05 16:56:03.944313', 73, '2026-02-05 16:56:03.944313', '보양식', 'SYSTEM'),
       ('2026-02-05 16:56:22.127825', 74, '2026-02-05 16:56:22.127825', '야식', 'SYSTEM'),
       ('2026-02-05 16:56:22.128603', 75, '2026-02-05 16:56:22.128603', '매운요리', 'SYSTEM'),
       ('2026-02-05 16:56:22.129450', 76, '2026-02-05 16:56:22.129450', '술안주', 'SYSTEM'),
       ('2026-02-05 16:56:30.914904', 77, '2026-02-05 16:56:30.914904', '전자레인지요리', 'SYSTEM'),
       ('2026-02-05 16:57:36.081167', 78, '2026-02-05 16:57:36.081167', '카나페', 'SYSTEM'),
       ('2026-02-05 16:57:36.082549', 79, '2026-02-05 16:57:36.082549', '에피타이저', 'SYSTEM'),
       ('2026-02-05 16:57:36.083231', 80, '2026-02-05 16:57:36.083231', '핑거푸드', 'SYSTEM'),
       ('2026-02-05 16:58:08.037636', 81, '2026-02-05 16:58:08.037636', '라면', 'SYSTEM'),
       ('2026-02-05 16:58:38.396109', 82, '2026-02-05 16:58:38.396109', '토마토요리', 'SYSTEM'),
       ('2026-02-05 16:58:57.836635', 83, '2026-02-05 16:58:57.836635', '국밥', 'SYSTEM'),
       ('2026-02-05 16:58:57.838612', 84, '2026-02-05 16:58:57.838612', '든든한한끼', 'SYSTEM'),
       ('2026-02-05 16:59:04.646042', 85, '2026-02-05 16:59:04.646042', '혼밥', 'SYSTEM'),
       ('2026-02-05 16:59:36.032487', 86, '2026-02-05 16:59:36.032487', '디저트', 'SYSTEM'),
       ('2026-02-05 16:59:36.033321', 87, '2026-02-05 16:59:36.033321', '저칼로리', 'SYSTEM'),
       ('2026-02-05 16:59:36.033912', 88, '2026-02-05 16:59:36.033912', '홈메이드', 'SYSTEM'),
       ('2026-02-05 16:59:45.772271', 89, '2026-02-05 16:59:45.772271', '포케', 'SYSTEM'),
       ('2026-02-05 16:59:45.773210', 90, '2026-02-05 16:59:45.773210', '다이어트식', 'SYSTEM'),
       ('2026-02-05 16:59:52.419714', 91, '2026-02-05 16:59:52.419714', '샌드위치', 'SYSTEM'),
       ('2026-02-05 16:59:52.420982', 92, '2026-02-05 16:59:52.420982', '브런치', 'SYSTEM'),
       ('2026-02-05 17:00:02.259935', 93, '2026-02-05 17:00:02.259935', '밀프레프', 'SYSTEM'),
       ('2026-02-05 17:00:02.261334', 94, '2026-02-05 17:00:02.261334', '자취요리', 'SYSTEM'),
       ('2026-02-05 17:03:56.298423', 95, '2026-02-05 17:03:56.298423', '밀프렙', 'SYSTEM'),
       ('2026-02-05 17:03:56.299420', 96, '2026-02-05 17:03:56.299420', '식단관리', 'SYSTEM'),
       ('2026-02-05 17:05:50.105272', 97, '2026-02-05 17:05:50.105272', '여름요리', 'SYSTEM'),
       ('2026-02-05 17:06:06.578009', 98, '2026-02-05 17:06:06.578009', '단백질간식', 'SYSTEM'),
       ('2026-02-05 17:06:06.580210', 99, '2026-02-05 17:06:06.580210', '오븐요리', 'SYSTEM'),
       ('2026-02-05 17:06:06.581956', 100, '2026-02-05 17:06:06.581956', '영양간식', 'SYSTEM'),
       ('2026-02-05 17:06:34.668675', 101, '2026-02-05 17:06:34.668675', '퓨전요리', 'SYSTEM'),
       ('2026-02-05 17:06:34.670878', 102, '2026-02-05 17:06:34.670878', '홈스토랑', 'SYSTEM'),
       ('2026-02-05 17:07:28.647577', 103, '2026-02-05 17:07:28.647577', '생선스테이크', 'SYSTEM'),
       ('2026-02-05 17:07:28.844402', 104, '2026-02-05 17:07:28.844402', '해장', 'SYSTEM'),
       ('2026-02-05 17:07:35.026535', 105, '2026-02-05 17:07:35.026535', '이탈리안', 'SYSTEM'),
       ('2026-02-05 17:07:35.028260', 106, '2026-02-05 17:07:35.028260', '홈쿠킹', 'SYSTEM'),
       ('2026-02-05 17:08:01.406055', 107, '2026-02-05 17:08:01.406055', '찜요리', 'SYSTEM'),
       ('2026-02-05 17:08:06.747692', 108, '2026-02-05 17:08:06.747692', '수육', 'SYSTEM'),
       ('2026-02-05 17:08:06.748762', 109, '2026-02-05 17:08:06.748762', '무수분요리', 'SYSTEM'),
       ('2026-02-05 17:08:17.627306', 110, '2026-02-05 17:08:17.627306', '토스트', 'SYSTEM'),
       ('2026-02-05 17:08:17.628893', 111, '2026-02-05 17:08:17.628893', '홈브런치', 'SYSTEM'),
       ('2026-02-05 17:08:26.803110', 112, '2026-02-05 17:08:26.803110', '무스비', 'SYSTEM'),
       ('2026-02-05 17:08:26.804043', 113, '2026-02-05 17:08:26.804043', '김밥', 'SYSTEM'),
       ('2026-02-05 17:09:36.077881', 114, '2026-02-05 17:09:36.077881', '원팬요리', 'SYSTEM'),
       ('2026-02-05 17:12:10.769104', 115, '2026-02-05 17:12:10.769104', '애피타이저', 'SYSTEM'),
       ('2026-02-05 17:12:10.769985', 116, '2026-02-05 17:12:10.769985', '서양식', 'SYSTEM'),
       ('2026-02-05 17:13:15.892523', 117, '2026-02-05 17:13:15.892523', '일품요리', 'SYSTEM'),
       ('2026-02-05 17:13:15.893489', 118, '2026-02-05 17:13:15.893489', '손님초대요리', 'SYSTEM'),
       ('2026-02-05 17:28:04.001023', 119, '2026-02-05 17:28:04.001023', '이색메뉴', 'SYSTEM'),
       ('2026-02-05 17:28:04.002201', 120, '2026-02-05 17:28:04.002201', '고급디저트', 'SYSTEM'),
       ('2026-02-05 17:28:24.331626', 121, '2026-02-05 17:28:24.331626', '흑백요리사', 'SYSTEM'),
       ('2026-02-05 17:30:18.019161', 122, '2026-02-05 17:30:18.019161', '프렌치', 'SYSTEM');


-- 레시피 태그
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (215, 31, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (211, 31, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (212, 31, 32);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (213, 31, 33);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (214, 31, 34);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (218, 32, 13);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (216, 32, 30);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (219, 32, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (217, 32, 35);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (220, 32, 36);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (224, 33, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (223, 33, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (221, 33, 37);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (222, 33, 38);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (228, 34, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (225, 34, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (226, 34, 39);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (227, 34, 40);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (229, 34, 41);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (230, 35, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (232, 35, 34);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (231, 35, 42);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (233, 35, 43);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (236, 36, 8);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (237, 36, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (235, 36, 35);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (234, 36, 44);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (238, 36, 45);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (242, 37, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (239, 37, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (241, 37, 33);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (240, 37, 46);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (247, 38, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (243, 38, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (244, 38, 47);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (245, 38, 48);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (246, 38, 49);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (252, 39, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (249, 39, 15);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (248, 39, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (251, 39, 34);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (250, 39, 50);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (255, 40, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (253, 40, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (254, 40, 51);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (256, 40, 52);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (261, 42, 55);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (262, 42, 56);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (263, 42, 57);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (264, 42, 58);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (265, 43, 59);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (266, 43, 60);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (267, 43, 61);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (268, 43, 62);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (269, 44, 25);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (270, 44, 40);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (271, 44, 63);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (272, 44, 64);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (274, 45, 21);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (273, 45, 52);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (275, 45, 65);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (276, 45, 66);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (278, 46, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (277, 46, 22);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (279, 46, 67);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (280, 46, 68);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (281, 47, 22);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (283, 47, 33);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (282, 47, 69);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (284, 47, 70);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (288, 48, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (285, 48, 21);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (286, 48, 22);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (287, 48, 68);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (291, 49, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (293, 49, 64);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (289, 49, 71);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (290, 49, 72);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (292, 49, 73);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (294, 50, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (295, 50, 74);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (296, 50, 75);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (297, 50, 76);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (301, 51, 8);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (298, 51, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (300, 51, 70);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (299, 51, 77);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (303, 52, 61);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (302, 52, 78);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (304, 52, 79);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (305, 52, 80);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (308, 53, 10);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (309, 53, 24);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (307, 53, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (306, 53, 81);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (311, 54, 16);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (310, 54, 61);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (313, 54, 68);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (312, 54, 82);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (316, 55, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (315, 55, 73);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (314, 55, 83);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (317, 55, 84);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (318, 56, 16);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (319, 56, 61);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (321, 56, 67);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (320, 56, 85);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (322, 57, 13);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (323, 57, 86);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (324, 57, 87);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (325, 57, 88);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (328, 58, 35);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (329, 58, 68);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (326, 58, 89);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (327, 58, 90);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (330, 59, 20);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (332, 59, 67);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (331, 59, 91);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (333, 59, 92);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (335, 60, 28);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (337, 60, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (334, 60, 93);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (336, 60, 94);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (340, 61, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (339, 61, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (341, 61, 44);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (338, 61, 53);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (342, 61, 67);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (343, 62, 13);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (345, 62, 16);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (347, 62, 30);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (344, 62, 95);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (346, 62, 96);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (350, 63, 15);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (349, 63, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (351, 63, 54);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (348, 63, 97);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (355, 64, 38);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (352, 64, 90);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (353, 64, 98);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (354, 64, 99);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (356, 64, 100);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (359, 65, 15);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (358, 65, 16);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (357, 65, 101);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (360, 65, 102);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (363, 66, 36);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (364, 66, 66);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (361, 66, 71);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (362, 66, 103);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (366, 67, 2);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (365, 67, 15);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (369, 67, 74);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (368, 67, 85);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (367, 67, 104);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (371, 68, 16);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (373, 68, 61);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (372, 68, 92);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (370, 68, 105);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (374, 68, 106);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (375, 69, 13);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (376, 69, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (378, 69, 35);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (377, 69, 107);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (382, 70, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (381, 70, 31);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (383, 70, 39);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (379, 70, 108);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (380, 70, 109);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (386, 71, 25);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (385, 71, 86);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (384, 71, 110);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (387, 71, 111);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (390, 72, 28);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (391, 72, 54);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (388, 72, 112);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (389, 72, 113);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (395, 73, 67);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (394, 73, 74);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (393, 73, 101);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (392, 73, 113);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (399, 74, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (396, 74, 16);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (398, 74, 61);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (400, 74, 65);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (397, 74, 114);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (403, 75, 1);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (404, 75, 52);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (402, 75, 53);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (401, 75, 101);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (405, 77, 30);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (406, 77, 36);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (407, 77, 115);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (408, 77, 116);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (409, 78, 22);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (412, 78, 73);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (410, 78, 117);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (411, 78, 118);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (417, 79, 58);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (413, 79, 86);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (414, 79, 101);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (415, 79, 119);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (416, 79, 120);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (418, 80, 60);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (420, 80, 65);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (419, 80, 117);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (421, 80, 121);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (425, 81, 36);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (423, 81, 65);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (424, 81, 66);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (426, 81, 72);
INSERT IGNORE INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`)
VALUES (422, 81, 122);

INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,144,31,'건고사리 150g을 물에 6시간 동안 불려 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,145,31,'불린 고사리에 밀가루 1/2컵을 넣고 바락바락 주물러 쓴맛을 제거한 뒤 단단한 밑동은 잘라냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,146,31,'깨끗한 물에 2시간 정도 담가 남은 쓴맛을 우려냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,147,31,'냄비에 물과 남은 밀가루 1/2컵을 풀어 밀가루물을 만들고, 고사리를 넣어 끓기 시작하면 10분간 삶은 뒤 찬물에 헹굽니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,148,31,'팬에 고사리를 담고 다진 마늘 1.5큰술, 국간장 2큰술, 진간장 2큰술, 참기름 2큰술을 넣어 조물조물 무친 후 20분간 숙성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,149,31,'팬을 가열하여 고사리에서 나온 국물과 함께 볶아주며, 식감이 뻣뻣할 경우 물 1컵을 추가해 더 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,150,31,'다진 대파 1대, 통깨 1큰술, 들기름 2큰술, 들깨가루 1큰술을 넣고 고루 버무려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,151,32,'양배추의 겉잎을 제거하고 4등분한 뒤 채칼을 이용해 가늘게 채 썹니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,152,32,'적양배추와 당근도 채칼로 얇게 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,153,32,'검은깨를 절구에 넣고 고소한 풍미가 살도록 곱게 갈아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,154,32,'볼에 그릭요거트, 들기름, 간 검은깨, 다진 마늘, 소금, 후추, 식초, 올리고당을 넣고 골고루 섞어 드레싱을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,155,32,'접시에 준비한 채소를 담고 그 위에 흑임자 드레싱을 듬뿍 뿌려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,156,33,'칼을 이용해 꼬리에서 머리 방향으로 긁어 조기의 비늘을 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,157,33,'가위로 조기의 꼬리, 등, 가슴, 배 지느러미를 깔끔하게 잘라냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,158,33,'흐르는 물에 조기의 아가미 부분을 깨끗이 씻어냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,159,33,'천일염 1큰술과 소주 1잔을 뿌려 1분간 재운 뒤 물에 헹궈 비린내를 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,160,33,'키친타월로 조기의 물기를 닦아내고, 에어프라이어 바닥에 대파를 깔아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,161,33,'조기를 대파 위에 올리고 꼬리 부분에 잘라둔 대파를 끼워 타지 않게 보호합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,162,33,'180도 온도에서 15분간 굽고, 뒤집어서 7분 더 구워 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,163,34,'대파는 심을 제거하고 가늘게 채 썬 뒤 물에 10분간 담가 아린 맛을 빼고 물기를 제거한다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,164,34,'고춧가루, 식초, 간장, 소금, 통깨, 참기름, 설탕, 올리고당을 섞어 파절이 양념을 만든다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,165,34,'준비한 양념에 파채를 넣고 버무려 파절이를 완성한다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,166,34,'믹서에 마늘, 생강, 소금, 후추, 물, 식용유를 넣고 갈아 고기 양념을 만든다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,167,34,'돼지고기는 먹기 좋은 크기로 잘라 갈아둔 양념에 버무려 재워둔다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,168,34,'계란 8개를 곱게 풀어 계란물을 만든다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,169,34,'팬에 양념한 고기를 먼저 볶다가 부추를 뿌리고 계란물을 부어 앞뒤로 노릇하게 굽는다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,170,34,'완성된 육전을 먹기 좋게 자르고 파절이를 곁들여 마무리한다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,171,35,'무와 알배추를 2cm 크기로 나박 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,172,35,'쪽파는 6cm 길이로 썰고, 배와 고추는 적당한 크기로 썰어 고추씨를 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,173,35,'썰어둔 무에 꽃소금 7.5큰술을 넣어 버무린 후 배추를 넣고 30분간 절입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,174,35,'냄비에 다시마와 물을 넣고 끓이다가 다시마를 건져낸 후 찹쌀가루 물을 넣어 풀을 쑤어 식힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,175,35,'믹서기에 찹쌀풀, 양파, 배, 마늘, 생강, 고춧가루를 넣고 곱게 갑니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,176,35,'절여진 채소에 체망을 올리고 갈아둔 양념과 물 5L를 부어 양념을 걸러냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,177,35,'쪽파와 고추를 넣고 섞은 뒤 냉장고에서 천천히 익혀 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,178,36,'양배추 1통을 4등분하여 심지를 제거하고 가늘게 채 썹니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,179,36,'채 썬 양배추를 물에 10분간 담가 씻어준 뒤 물기를 뺍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,180,36,'양배추에 천일염, 설탕, 물엿을 각각 2큰술씩 넣고 30분간 절입니다. (중간에 한 번 뒤집어줍니다)');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,181,36,'적양파 1개와 당근 0.5개를 가늘게 채 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,182,36,'절여진 양배추는 채소 탈수기를 사용하여 물기를 최대한 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,183,36,'팬에 식용유 3큰술, 들기름 3큰술을 두르고 다진 마늘 2큰술을 볶아 마늘 기름을 냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,184,36,'굵은 고춧가루 0.5큰술, 채 썬 적양파, 국간장 2큰술을 넣고 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,185,36,'채 썬 당근과 절인 양배추를 넣고 숨이 죽을 때까지 충분히 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (9,186,36,'들깨가루 3큰술을 넣어 채소에서 나온 수분을 흡수시키며 골고루 섞어 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,187,37,'도라지에 천일염 2큰술, 원당 2큰술, 물 2컵을 넣고 바락바락 주물러 30분간 재워 쓴맛을 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,188,37,'오이는 가시를 제거한 뒤 어슷하게 썰어 고운 소금 0.5큰술을 넣고 20분간 절입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,189,37,'쓴 물이 나온 도라지는 찬물에 헹군 뒤 키친타월로 물기를 꼼꼼하게 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,190,37,'절인 오이는 씻지 않고 베보자기에 넣어 물기를 꽉 짜냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,191,37,'준비한 도라지에 고춧가루 5큰술을 먼저 넣고 버무려 붉은 색을 입힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,192,37,'다진 마늘, 진간장, 고추장, 양조식초, 매실액, 원당, 물엿을 넣고 골고루 무칩니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,193,37,'물기를 짠 오이를 넣고 함께 버무린 뒤 통깨를 뿌려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,194,38,'청양고추 30개를 다지기나 칼을 이용해 잘게 다져줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,195,38,'다시마 1조각에 뜨거운 물 1컵을 붓고 30분간 불려 다시마 물을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,196,38,'팬에 다진 고추와 참기름 5큰술을 넣고 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,197,38,'양조간장 5큰술을 넣고 간장이 고추에 배도록 함께 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,198,38,'미리 준비한 다시마 물을 붓고 센 불에서 국물이 자작해질 때까지 졸여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,199,38,'국물이 졸아들면 통깨 1큰술을 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,200,39,'당면은 찬물에 2시간 동안 불리고, 건표고버섯과 건목이버섯도 각각 물에 불려 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,201,39,'느타리버섯은 가닥가닥 찢고, 당근과 양파는 채 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,202,39,'끓는 물에 천일염을 넣고 시금치를 30초간 데친 후 찬물에 헹궈 물기를 짭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,203,39,'물 2.4L에 표고버섯 불린 물과 다시마를 넣고 끓이다가 물이 끓으면 다시마는 건져냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,204,39,'육수 위에 찜기를 올려 버섯류를 7분간 찌고, 이어 당근과 양파도 7분간 쪄서 익힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,205,39,'남은 육수에 다진 마늘, 미림, 설탕, 간장, 후추, 식용유, 참기름 0.5컵을 넣어 양념장을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,206,39,'양념장이 끓으면 불린 당면을 넣고 3분간 중간중간 뒤집어가며 조리듯 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,207,39,'익은 당면을 넓은 대야에 옮겨 담고 참기름 0.5컵을 추가해 골고루 버무려둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (9,208,39,'쪄낸 채소와 버섯에 참기름, 소금, 통깨를 넣어 가볍게 밑간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (10,209,39,'당면에 양념한 채소들을 모두 넣고 잘 버무린 후 통깨를 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,210,40,'부추, 팽이버섯, 느타리버섯, 표고버섯을 적당한 크기로 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,211,40,'두부는 면보를 사용하여 물기를 꽉 짜줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,212,40,'당면은 10분간 삶은 뒤 다짐기를 이용해 잘게 다집니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,213,40,'부추, 물기를 짠 두부, 돼지 앞다리살도 각각 다짐기로 잘게 다집니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,214,40,'다진 재료들에 생강과 소금을 넣고 잘 섞어 만두소를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,215,40,'묵은지 잎을 펼치고 만두소를 적당량 넣어 돌돌 말아 김치만두를 빚습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,216,40,'전골 냄비에 김치만두를 두르고 새우젓, 고추장, 고춧가루, 조선간장, 미림, 대파, 다진마늘을 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,217,40,'가운데에 손질한 버섯들을 올리고 김치 국물과 물을 붓습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (9,218,40,'만두가 익을 때까지 보글보글 끓인 뒤 마지막에 부추를 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,224,42,'팬에 버터를 넉넉히 넣고 완전히 녹여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,225,42,'녹은 버터 위에 카다이프 면을 골고루 깔아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,226,42,'카다이프 위에 모짜렐라 치즈와 아몬드 슬라이스를 듬뿍 올려줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,227,42,'다시 카다이프 면을 올려 덮어준 뒤, 약불에서 치즈가 녹고 바닥면이 노릇해질 때까지 굽습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,228,42,'뒤집개나 접시를 이용해 조심스럽게 뒤집어 반대편도 구워줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,229,42,'설탕과 물을 1:1 비율로 끓여 만든 시럽을 팬에 붓고 표면이 코팅되도록 졸여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,230,42,'그릇에 담고 마지막으로 아몬드 슬라이스를 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,231,43,'소 안심을 조리용 실로 묶어 고정합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,232,43,'안심 겉면에 굵게 간 후추를 듬뿍 묻히고 소금으로 간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,233,43,'달군 팬에 안심을 올려 모든 면을 노릇하게 시어링합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,234,43,'고기를 구운 팬에 다진 샬롯과 손질한 양송이버섯을 넣고 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,235,43,'브랜디를 넣어 알코올을 날린 후 생크림을 부어 졸입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,236,43,'시어링한 고기를 소스에 넣고 기호에 맞게(웰던) 익혀 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,237,44,'감자의 껍질을 벗긴 후 식감을 위해 일반적인 감자튀김보다 두툼하게 썹니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,238,44,'냄비에 물과 감자, 마늘, 로즈마리, 월계수잎, 소금을 넣고 감자가 쉽게 으스러질 정도로 삶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,239,44,'삶은 감자를 조심스럽게 건져내어 냉동고에 넣고 차갑게 식힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,240,44,'기름을 예열한 뒤 식힌 감자를 넣고, 튀기고 식히는 과정을 반복하며 총 3번 튀겨 짙은 갈색을 냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,241,44,'케첩에 파프리카 가루를 섞어 소스를 만들고 완성된 감자튀김과 곁들입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,242,45,'홍어애 겉면의 얇은 근막을 조심스럽게 제거하여 손질합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,243,45,'다시마와 가쓰오부시를 활용해 밑국물(이치방다시)을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,244,45,'밑국물 4, 청주 1, 미림 1, 진간장 1의 비율로 섞어 조림장을 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,245,45,'손질된 홍어애를 조림장에 넣고 약불에서 40~50분간 뭉근하게 졸인 후 상온에서 식힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,246,45,'트러플도 동일한 비율의 조림장에 짧게 졸여 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,247,45,'밑국물을 내고 남은 다시마를 한번 삶은 뒤 조림장에 졸여 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,248,45,'식초, 설탕, 소금을 섞어 만든 초대리로 밥에 밑간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,249,45,'계란은 스크램블보다 조금 더 단단한 식감이 나도록 볶아 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (9,250,45,'밥 위에 볶은 계란을 깔고, 먹기 좋게 썬 홍어애, 트러플, 다시마 조림을 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,251,46,'계란 3개를 잘 풀어주고, 두부는 24조각 정도로 깍둑썰기합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,252,46,'대파, 고추, 마늘 등의 야채를 잘게 다져 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,253,46,'물 200ml에 굴소스, 설탕, 미림, 간장, 전분을 섞어 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,254,46,'팬에 두부와 계란물을 넣고 스크램블하듯 가볍게 볶아낸 뒤 따로 접시에 덜어둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,255,46,'다시 팬에 기름을 두르고 다진 야채를 볶아 향을 냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,256,46,'야채 향이 올라오면 준비한 소스를 붓고 보글보글 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,257,46,'소스가 끓어오르면 덜어두었던 두부와 계란을 넣고 빠르게 버무려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,258,47,'오이 2개의 꼭지를 잘라내고 껍질을 군데군데 얼기설기 깎아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,259,47,'오이를 칼등으로 두드려 적당히 깬 뒤 먹기 좋은 크기로 썰어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,260,47,'대파와 청양고추를 잘게 송송 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,261,47,'볼에 손질한 오이, 다진 마늘, 대파, 청양고추를 모두 담습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,262,47,'두반장 1큰술, 굴소스 1큰술, 설탕 1.5큰술, 식초 3큰술, 간장 1큰술, 고추기름 2큰술을 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,263,47,'양념이 골고루 배도록 조물조물 버무려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,264,48,'계란 3개에 손질한 새우, 팽이버섯, 송송 썬 대파, 소금 한 꼬집을 넣고 잘 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,265,48,'팬에 식용유를 두르고 준비한 계란물을 부어 중불에서 앞뒤로 노릇하게 부쳐줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,266,48,'그릇에 따뜻한 밥을 담고 그 위에 부쳐낸 계란을 통째로 올립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,267,48,'팬에 물 250ml, 완두콩, 미림 2큰술, 굴소스 1큰술, 간장 1큰술, 설탕 0.5큰술을 넣고 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,268,48,'감자전분 1큰술과 물 2큰술을 섞어 만든 전분물을 끓는 소스에 저어가며 부어 걸쭉하게 농도를 맞춥니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,269,48,'완성된 소스를 밥과 계란 위에 골고루 부어 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,270,49,'소고기를 먹기 좋은 크기로 썰어 소금과 후추로 밑간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,271,49,'냄비에 버터를 녹인 후 다진 마늘과 손질한 양파를 넣어 함께 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,272,49,'밑간한 소고기와 먹기 좋게 썬 당근, 감자를 냄비에 추가합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,273,49,'물 250ml와 토마토 통조림 한 캔을 모두 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,274,49,'치킨스톡과 매콤한 맛을 위한 페퍼론치노를 넣고 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,275,49,'뚜껑을 덮고 약불에서 1시간 동안 뭉근하게 끓여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,276,49,'완성된 스튜를 그릇에 담고 파슬리 가루를 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,277,50,'냄비에 물 3컵, 소주 1컵, 통후추 한 줌을 넣고 닭발을 10분간 삶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,278,50,'삶은 닭발을 흐르는 물에 3번 씻어 뽀득뽀득하게 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,279,50,'고춧가루, 고추장, 간장, 설탕, 물엿, 다진 마늘, 다진 생강, 굴소스, 후추, 매실청을 섞어 양념장을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,280,50,'준비한 닭발에 양념장을 넣고 조물조물 버무립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,281,50,'새 냄비에 물 3컵, 콩나물, 소금을 넣고 2분간 삶은 뒤 콩나물만 따로 건져둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,282,50,'콩나물을 삶은 육수에 양념한 닭발을 넣고 국물이 진득해질 때까지 10분간 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,283,50,'송송 썬 대파와 미리 삶아둔 콩나물을 올려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,284,51,'용기에 계란 3개를 풀고 미지근한 물 2컵을 부어 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,285,51,'꽃게액 1큰술과 맛소금을 약간 넣어 간을 하고 잘 풀어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,286,51,'계란물을 체에 한 번 걸러 알끈을 제거해 매끈한 상태로 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,287,51,'전자레인지 전용 용기의 뚜껑을 살짝 열어둔 상태로 5분간 조리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,288,51,'완성된 계란찜 위에 참기름 한 바퀴, 송송 썬 대파와 통깨를 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,289,52,'냄비에 계란이 잠길 정도로 물을 붓고 소금을 약간 넣어 강불에서 15분간 삶아 완숙으로 익힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,290,52,'양파, 셀러리, 파슬리 줄기를 얇게 채 썰어 미르푸아를 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,291,52,'새우는 이쑤시개를 이용해 내장을 제거하고 손질합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,292,52,'새로 끓인 물에 레몬즙과 미르푸아를 넣고 새우를 넣어 붉은색이 날 때까지 삶은 뒤 껍질을 벗깁니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,293,52,'익은 새우는 등 쪽에서 칼집을 내어 1cm 정도 남기고 넓게 펼쳐 모양을 잡습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,294,52,'식빵은 지름 4cm 정도의 원형 틀로 찍어내거나 가위로 동그랗게 자릅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,295,52,'마른 팬에 식빵을 올려 앞뒤로 바삭하게 굽고 윗면에 버터를 얇게 바릅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,296,52,'버터를 바른 식빵 위에 슬라이스한 계란, 새우 순으로 올리고 케첩과 파슬리 잎을 얹어 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,297,53,'냄비에 물 500ml를 붓고 불을 켜서 물을 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,298,53,'물이 끓기 시작하면 면과 분말스프, 후레이크를 모두 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,299,53,'면이 골고루 익도록 약 4분 30초간 더 끓여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,300,53,'라면이 다 익으면 그릇에 옮겨 담고 얇게 썬 양송이버섯과 고추를 고명으로 얹어 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,301,54,'마늘은 칼날로 으깬 뒤 다지고, 대추 방울토마토는 반으로 썬다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,302,54,'팬에 올리브유 2큰술을 두르고 다진 마늘과 방울토마토를 넣어 볶는다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,303,54,'잘게 썬 대파를 넣고 국자로 방울토마토를 으깨며 충분히 볶아준다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,304,54,'홀토마토 통조림 200g과 토마토 퓨레 220g을 팬에 넣는다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,305,54,'물 200ml와 소금을 넣고 약불에서 15분간 뭉근하게 끓여 소스를 완성한다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,306,54,'다른 냄비에 삶아둔 숏 파스타를 소스 팬에 넣고 올리브유 1큰술을 추가해 버무린다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,307,54,'소스의 신맛을 중화시키기 위해 설탕 2꼬집을 넣고 잘 섞어준다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,308,54,'불을 끄고 얇게 채 썬 바질과 후추를 뿌려 향을 더한다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (9,309,54,'그릇에 옮겨 담은 뒤 치즈 가루를 뿌려 마무리한다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,310,55,'멀티쿠커에 핏물을 뺀 돼지고기, 껍질째 썬 우엉, 표고버섯, 무, 생강, 대파, 쪽파, 마늘 7알을 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,311,55,'재료가 잠길 정도로 물을 붓고 천일염을 넣어 간을 한 뒤 멀티쿠커에서 1시간 30분간 조리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,312,55,'육수에서 고기를 건져내어 얇게 썰고, 육수는 체에 걸러 맑은 상태로 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,313,55,'청양고추를 소금물에 절인 후 새우젓, 고춧가루, 설탕, 참기름, 마늘 4알과 함께 블렌더로 갈아 고추지를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,314,55,'그릇에 밥을 담고 뜨거운 육수를 부었다 따랐다 하는 토렴 과정을 반복하여 밥을 데웁니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,315,55,'밥 위에 육수를 붓고 썰어둔 고기와 송송 썬 쪽파를 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,316,56,'물 1L에 소금 15g을 넣고 끓인 뒤, 파스타면 120g을 넣어 7분간 삶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,317,56,'마늘 1.5개를 곱게 다져서 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,318,56,'중불로 달군 팬에 올리브 오일 2.5큰술과 다진 마늘을 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,319,56,'엔초비 페이스트와 페페론치노 1개를 부수어 넣고 함께 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,320,56,'기름이 지글거리기 시작하면 잠시 불을 꺼서 마늘과 고추의 향이 기름에 배게 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,321,56,'삶아진 면을 팬에 넣고 다시 중강불을 켠 뒤 가볍게 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,322,56,'면수나 물을 조금씩 추가하며 팬을 강하게 흔들어 소스를 걸쭉하게 유화(만테카레)시킵니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,323,56,'원하는 농도가 되면 그릇에 담고 치즈를 넉넉히 갈아 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,324,57,'피스타치오 200g을 에어프라이어 120도에서 10분간 구워줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,325,57,'구운 피스타치오를 믹서기에 넣고 중간중간 뭉친 부분을 풀어주며 꾸덕해질 때까지 갈아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,326,57,'갈린 피스타치오에 소금 1~2꼬집, 알룰로스 3스푼, 오일 5스푼을 넣고 섞어 스프레드를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,327,57,'두유면 3봉의 물기를 제거하고 넓게 펼쳐 에어프라이어 160도에서 8분씩 총 4번 뒤집어가며 바삭하게 굽습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,328,57,'바삭해진 두유면을 잘게 부숴 피스타치오 스프레드와 섞은 뒤 동그랗게 빚어 냉장 보관합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,329,57,'타피오카 전분 2, 우유 7, 알룰로스 3, 소금 약간, 코코아가루 1스푼을 섞어 전자레인지에 1분간 돌려 쫀득한 피를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,330,57,'초코 피를 냉장고에서 20분간 식힌 후, 얇게 펴서 냉장 보관했던 피스타치오 볼을 감쌉니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,331,57,'겉면에 코코아 가루를 골고루 묻혀 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,332,58,'양상추는 한입 크기로 찢고 오이는 얇게 썰어 준비하며, 양파는 슬라이스하여 물에 담가 매운기를 제거합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,333,58,'볼에 간장, 저당 굴소스, 알룰로스, 저당 케첩을 섞어 데리야끼 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,334,58,'계란 1알에 소금을 약간 넣고 잘 풀어준 뒤 약불에서 스크램블 에그를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,335,58,'팬에 올리브오일을 두르고 양파와 새우를 넣어 노릇하게 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,336,58,'볶아진 재료에 미리 만들어둔 데리야끼 소스를 붓고 약불에서 소스가 배도록 졸여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,337,58,'밥에 참기름과 소금으로 밑간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,338,58,'그릇에 양상추를 깔고 밥, 스크램블 에그, 손질한 야채, 볶은 새우를 보기 좋게 올립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,339,58,'병아리콩, 양파후레이크를 뿌리고 취향에 따라 후추와 저당 마요네즈를 곁들여 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,340,59,'간장, 굴소스, 알룰로스, 케첩을 섞어 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,341,59,'팬에 계란을 반숙으로 구워 따로 둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,342,59,'팬에 우삼겹을 핏기가 사라질 때까지 굽고 기름을 살짝 닦아냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,343,59,'채 썬 양배추와 후추를 넣고 고기와 함께 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,344,59,'양배추가 어느 정도 익으면 만들어둔 소스를 넣고 졸이듯 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,345,59,'바삭하게 구운 빵 위에 계란후라이와 볶은 재료를 차례로 올립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,346,59,'마요네즈와 가쓰오부시를 취향껏 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,347,60,'밥에 참기름, 소금, 깨, 김가루를 넣고 골고루 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,348,60,'팬에 계란 4개를 구워 계란후라이를 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,349,60,'기름을 뺀 참치에 저당 마요네즈와 다진 고추를 넣어 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,350,60,'김치를 가위로 잘게 썬 뒤 참기름 1큰술과 알룰로스 2큰술을 넣고 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,351,60,'섞은 김치를 전자레인지에 2분씩 두 번 돌려 익혀줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,352,60,'준비한 용기에 밥, 계란후라이, 참치마요, 볶음김치, 밥 순서로 차곡차곡 쌓아 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,353,61,'크래미를 포크 등을 이용하여 가늘게 찢어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,354,61,'대파는 송송 썰고 양파는 잘게 다져줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,355,61,'팬에 올리브오일을 두르고 대파와 양파를 넣어 투명해질 때까지 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,356,61,'찢어둔 크래미와 다진 마늘을 넣고 노릇하게 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,357,61,'데우지 않은 즉석 현미밥을 넣고 중불에서 수분을 날리며 함께 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,358,61,'저당 굴소스와 스리라차 소스를 넣어 간이 잘 배도록 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,359,61,'계란 1개를 넣어 스크래블을 만든 뒤 후추를 뿌려 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,360,62,'끓는 물에 소금을 넣고 통밀 푸실리 면을 17분간 충분히 삶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,361,62,'양상추는 씻어서 물기를 완전히 제거하고, 방울토마토, 오이, 양파를 먹기 좋은 크기로 손질합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,362,62,'팬에 올리브오일 3큰술을 두르고 채 썬 양파, 닭가슴살, 새우에 소금과 후추를 뿌려 노릇하게 볶아냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,363,62,'올리브오일(3), 알룰로스, 진간장, 식초, 레몬즙, 참기름, 다진 마늘, 후추를 섞어 드레싱 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,364,62,'준비된 용기에 양상추, 오이, 식힌 면, 토마토, 볶은 재료, 스위트콘 순으로 나누어 담습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,365,62,'소스는 별도의 작은 용기에 담아 보관하며, 먹기 직전에 부어 섞어 먹습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,366,63,'끓는 물에 쌀소면을 넣고 약 4분 30초간 삶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,367,63,'삶은 면을 찬물에 깨끗이 헹구어 물기를 빼둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,368,63,'볼에 물, 식초, 알룰로스, 참치액, 간장을 분량대로 섞어 육수를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,369,63,'신김치는 가위로 잘게 썰고 양파와 오이는 가늘게 채 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,370,63,'만들어진 육수에 면을 담고 김치, 양파, 오이, 김가루, 삶은 계란, 얼음을 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,371,64,'감자와 닭가슴살을 작게 깍둑썰기합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,372,64,'볼에 썬 재료와 전분가루, 소금, 후추, 물을 넣고 끈기가 생기도록 잘 반죽합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,373,64,'종이호일 위에 반죽을 한 주먹씩 올려 동그란 쿠키 모양으로 빚습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,374,64,'반죽 위에 올리브유를 골고루 뿌려줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,375,64,'200도로 예열된 오븐에서 25분간 노릇하게 굽습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,376,64,'마요네즈, 스리라차, 알룰로스를 섞어 만든 소스를 곁들여 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,377,65,'끓는 물에 소금 0.5큰술과 통밀 파스타 50g을 넣고 약 80% 정도만 익힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,378,65,'냉동 새우는 해동하고, 명란젓은 반을 갈라 껍질을 분리하며 양파는 채 썹니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,379,65,'팬에 올리브오일을 두르고 다진 마늘과 페페론치노를 넣어 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,380,65,'마늘 향이 올라오면 양파와 새우를 넣고 양파가 반 정도 익을 때까지 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,381,65,'우유 1컵, 체다치즈 1장, 준비한 명란젓의 절반을 넣고 잘 풀어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,382,65,'삶아둔 파스타 면을 팬에 옮겨 소스가 면에 잘 배도록 원하는 농도까지 졸입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,383,65,'후추를 뿌리고 부족한 간은 소금이나 면수로 맞춥니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,384,65,'그릇에 담은 뒤 송송 썬 쪽파, 가쓰오부시, 남겨둔 명란 절반을 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,385,66,'가자미의 물기를 키친타월로 제거하고 소금, 후추로 밑간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,386,66,'가자미와 얇게 썬 감자에 밀가루를 골고루 묻혀줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,387,66,'팬에 올리브오일과 버터를 넣고 녹인 뒤 감자를 먼저 노릇하게 굽습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,388,66,'가자미를 팬에 넣고 타임을 추가하여 향을 입히며 앞뒤로 노릇하게 구워줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,389,66,'구워진 가자미와 감자를 건져낸 후 팬의 기름을 살짝 버리고 화이트 와인을 부어 알코올을 날립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,390,66,'버터와 레몬을 넣고 소금, 후추로 간을 하여 소스가 약간 졸아들 때까지 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,391,66,'접시에 가자미와 감자를 담고 준비된 소스를 뿌린 뒤 레몬과 허브로 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,392,67,'양파는 채를 썰고 대파와 고추는 송송 썰어 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,393,67,'냉동새우는 해동하고 목이버섯은 한입 크기로 손질해 둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,394,67,'팬에 올리브오일과 다진마늘을 넣어 볶다가 준비한 양파, 대파, 고추를 넣고 함께 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,395,67,'야채의 숨이 죽으면 굴소스와 고춧가루를 1스푼씩 넣고 약불에서 가볍게 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,396,67,'물 2.5컵(종이컵 기준)을 붓고 새우와 버섯을 넣은 뒤 끓어오를 때까지 기다립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,397,67,'컵누들의 유성스프를 제외한 면과 분말스프, 건더기스프를 모두 넣고 면이 풀어질 때까지 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,398,67,'계란 1개를 풀어 줄을 치듯 넣고, 후추와 유성스프를 추가하여 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,399,68,'볼에 계란 노른자 4개와 치즈 가루를 듬뿍 넣고 잘 섞어 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,400,68,'끓는 물에 소금 0.5큰술을 넣고 파스타 면을 6분간 삶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,401,68,'베이컨은 먹기 좋은 크기로 썰고 마늘은 잘게 다져 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,402,68,'팬에 올리브유를 두르고 다진 마늘과 베이컨을 볶다가 취향에 따라 페페론치노를 넣습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,403,68,'볶아진 재료에 삶은 면과 면수를 약간 넣어 함께 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,404,68,'불을 끄고 만들어둔 노른자 소스를 부어 잔열로 빠르게 섞어 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,405,69,'알배추를 길게 잘라준 뒤 찜기에 넣고 12분 정도 쪄줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,406,69,'볼에 간장, 식초, 굴소스, 물을 넣고 잘 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,407,69,'다진 대파, 다진 마늘, 다진 고추를 추가합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,408,69,'고추기름과 설탕을 넣고 설탕이 녹을 때까지 잘 섞어 소스를 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,409,69,'다 쪄진 배추를 그릇에 옮겨 담고 준비한 소스를 듬뿍 부어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,410,70,'대파의 파란 부분을 가위로 길게 갈라 넓게 펼쳐줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,411,70,'펼친 대파 위에 삼겹살을 올리고 겹겹이 쌓아준 뒤 먹기 좋은 크기로 썹니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,412,70,'냄비 바닥에 채 썬 양파와 남은 대파를 넉넉히 깔아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,413,70,'양파 위에 준비한 대파 삼겹살을 빈틈없이 세워서 담아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,414,70,'소금과 후추를 뿌리고 편으로 썬 생강과 통마늘을 올립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,415,70,'소주를 부은 뒤 뚜껑을 덮고 중약불에서 40분 동안 푹 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,416,70,'다진 대파, 새우젓, 다진 양파, 청양고추, 마늘, 고춧가루, 알룰로스, 참깨, 참기름을 섞어 특제 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,417,70,'잘 익은 수육을 꺼내 특제 새우젓 소스를 곁들여 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,418,71,'그릇에 계란 2개, 우유, 소금을 약간 넣고 잘 섞어 계란물을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,419,71,'카스테라의 겉면(진한 갈색 부분)을 도려낸 후 먹기 좋은 크기로 2등분 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,420,71,'손질한 카스테라를 준비한 계란물에 푹 적셔줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,421,71,'달궈진 팬에 버터를 두르고 카스테라의 모든 면을 노릇하게 구워줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,422,71,'접시에 담은 후 설탕을 뿌리고 토치를 이용해 설탕을 녹여 캐러멜라이징 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,423,71,'마지막으로 버터 한 조각을 위에 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,424,72,'오이를 감자칼을 이용해 길고 얇게 슬라이스하여 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,425,72,'계란 5알에 소금을 넣어 풀고 팬에 지단을 부친 뒤 스팸 크기에 맞춰 자릅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,426,72,'스팸은 뜨거운 물에 살짝 데쳐 기름기를 제거한 후 팬에 노릇하게 굽습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,427,72,'김밥용 김을 반으로 자릅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,428,72,'따뜻한 밥에 소금, 참기름, 식초, 깨소금을 넣고 골고루 섞어 양념합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,429,72,'김 위에 양념한 밥을 펴 바르고 깻잎, 오이, 계란 지단, 스팸 순으로 올려 돌돌 말아 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,430,73,'오이를 얇게 슬라이스하여 소금에 절여둡니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,431,73,'절인 오이는 물기를 최대한 꽉 짜서 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,432,73,'현미밥에 물기를 짠 오이, 트러플 솔트, 트러플 페스토, 트러플 오일, 후추를 넣고 잘 비벼줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,433,73,'김 위에 양념한 밥을 얇게 펴서 돌돌 말아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,434,73,'쯔유와 유자 폰즈 소스를 섞어 찍어 먹을 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,435,74,'팬에 파스타면, 다진 마늘, 페페론치노를 넣고 올리브오일을 두릅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,436,74,'물을 부어 재료를 익히며 조리 중간에 액젓을 넣어 간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,437,74,'면이 다 익고 소스가 적절히 유화되면 불을 끕니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,438,74,'허브 페스토를 넣어 면과 소스가 잘 어우러지도록 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,439,74,'접시에 옮겨 담은 후 사워크림을 취향껏 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,440,75,'파슬리, 바질, 잣, 마늘, 파마산 치즈, 레몬즙, 피쉬소스, 알룰로스, 올리브유, 소금을 블렌더에 넣고 갈아 허브 페스토를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,441,75,'배추김치를 물에 씻어 양념을 제거한 뒤 잘게 다져줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,442,75,'볼에 계란과 소금 한 꼬집을 넣어 풀고, 데우지 않은 즉석밥을 넣어 잘 비벼줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,443,75,'팬에 버터를 두르고 다진 김치를 넣어 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,444,75,'김치가 볶아지면 계란에 비빈 밥을 넣고 고슬고슬해질 때까지 충분히 볶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,445,75,'밥이 다 볶아지면 불을 끄고 만들어둔 허브 페스토를 넣어 골고루 섞어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,446,75,'완성된 볶음밥을 접시에 담고 위에 사워크림을 얹어 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,447,77,'파파할 멜론의 껍질을 벗기고 얇게 썬 후 꽃 모양 틀로 찍어냅니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,448,77,'접시에 방울토마토 절임을 보기 좋게 담습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,449,77,'모차렐라 치즈와 준비한 멜론을 토마토 위에 토핑합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,450,77,'토마토 절임 소스를 전체적으로 고루 뿌려줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,451,77,'레몬 슬라이스와 파슬리로 장식하고 치즈를 갈아 올려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,452,78,'끓는 물에 월계수잎 2장, 팔각 2개, 삼겹살을 넣고 10분간 데칩니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,453,78,'데친 고기의 물기를 제거하고 직육면체 모양으로 썰어줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,454,78,'팬에 식용유를 두르고 고기 겉면이 노릇해질 때까지 고르게 익힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,455,78,'냄비에 물, 진간장, 맛술, 물엿, 흑설탕, 팔각 6개, 카라멜 소스, 양파, 생강, 대파를 넣고 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,456,78,'소스에 고기를 넣고 뚜껑을 닫은 뒤 중약불에서 1시간 동안 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,457,78,'고기를 뒤집어주고 40분간 추가로 더 졸여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,458,78,'청경채는 소금을 넣은 끓는 물에 30초간 데쳐 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,459,78,'접시에 청경채와 고기를 담고 소스를 부어 마무리합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,460,79,'떡을 충분히 삶은 뒤 믹서기에 물과 함께 갈아 고운 체에 걸러 떡 퓨레를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,461,79,'계란 흰자를 휘핑하여 단단한 머랭을 만든 후 생크림을 섞어 베이스를 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,462,79,'준비된 베이스에 떡 퓨레를 넣고 골고루 섞어 세미 프레도 반죽을 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,463,79,'반죽을 원통형 몰드나 짤주머니를 이용해 떡 모양으로 성형한 뒤 냉동실에서 굳힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,464,79,'팬에 설탕, 물, 고추장, 생크림을 넣고 끓여 걸쭉한 고추장 카라멜 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,465,79,'차갑게 굳은 떡 모양 디저트를 꺼내 토치로 겉면을 살짝 그을려 불향을 입힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,466,79,'접시에 고추장 카라멜 소스를 넓게 펴 바르고 그 위에 디저트를 올린 뒤 깻잎 조각으로 장식합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,467,80,'무는 껍질을 벗겨 두툼하게 원형으로 썰어 손질합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,468,80,'끓는 물에 가쓰오부시를 넣어 육수를 우려낸 뒤 국간장으로 간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,469,80,'육수에 무를 넣고 종이호일로 덮어 30분 이상 푹 삶아줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,470,80,'팬에 진간장, 미림, 설탕을 넣어 끓이다가 들기름과 굴을 추가해 빠르게 졸여줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,471,80,'물과 묵가루를 섞어 가열하여 걸쭉한 질감의 소스를 만듭니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,472,80,'팬에 들기름을 두르고 삶아진 무를 앞뒤로 노릇하게 구워줍니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,473,80,'접시에 무와 굴을 담고 묵가루 소스를 듬뿍 뿌려 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (1,474,81,'양파는 깍둑썰기하고 셀러리는 작게 자르며, 양송이버섯은 4등분하고 파슬리는 다져 준비합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (2,475,81,'닭다리살의 물기를 제거한 뒤 소금과 후추로 밑간을 합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (3,476,81,'팬에 올리브 오일을 두르고 닭다리살의 껍질이 아래로 가게 하여 노릇하게 구운 뒤 뒤집어 반대면도 익힙니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (4,477,81,'오븐 용기에 구운 닭고기, 마늘, 로즈마리, 타임을 넣고 180도 오븐에서 10분간 굽습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (5,478,81,'닭을 구웠던 팬에 양파를 볶다가 갈색빛이 돌면 셀러리와 양송이버섯을 넣고 볶습니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (6,479,81,'버터를 넣어 향을 입히며 볶은 뒤 화이트 와인을 붓고 바닥을 긁어가며 반 정도 졸입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (7,480,81,'치킨 육수와 월계수잎을 넣고 중불에서 10분 정도 끓입니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (8,481,81,'생크림과 오븐에 구운 닭고기를 넣고 약불에서 5분 더 끓여 완성합니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (9,482,81,'명란을 섞은 매쉬드 포테이토를 그릇에 깔고 그 위에 치킨과 소스를 듬뿍 올립니다.');
INSERT IGNORE INTO `recipe_step` (`step_order`,`id`,`recipe_id`,`description`) VALUES (10,483,81,'치즈, 파슬리, 명란을 약간 더하고 토치로 그을린 레몬을 곁들여 마무리합니다.');


-- 재료
INSERT IGNORE INTO `ingredient` (`name`, `created_at`, `updated_at`)
VALUES ('소금', NOW(6), NOW(6)),
       ('후추', NOW(6), NOW(6)),
       ('설탕', NOW(6), NOW(6)),
       ('식용유', NOW(6), NOW(6)),
       ('참기름', NOW(6), NOW(6)),
       ('깨', NOW(6), NOW(6)),
       ('양파', NOW(6), NOW(6)),
       ('대파', NOW(6), NOW(6)),
       ('쪽파', NOW(6), NOW(6)),
       ('마늘', NOW(6), NOW(6)),
       ('생강', NOW(6), NOW(6)),
       ('청양고추', NOW(6), NOW(6)),
       ('홍고추', NOW(6), NOW(6)),
       ('당근', NOW(6), NOW(6)),
       ('감자', NOW(6), NOW(6)),
       ('무', NOW(6), NOW(6)),
       ('애호박', NOW(6), NOW(6)),
       ('버섯', NOW(6), NOW(6)),
       ('표고버섯', NOW(6), NOW(6)),
       ('양배추', NOW(6), NOW(6)),
       ('오이', NOW(6), NOW(6)),
       ('시금치', NOW(6), NOW(6)),
       ('브로콜리', NOW(6), NOW(6)),
       ('토마토', NOW(6), NOW(6)),
       ('아보카도', NOW(6), NOW(6)),
       ('레몬', NOW(6), NOW(6)),
       ('라임', NOW(6), NOW(6)),
       ('김치', NOW(6), NOW(6)),
       ('고추장', NOW(6), NOW(6)),
       ('고춧가루', NOW(6), NOW(6)),
       ('된장', NOW(6), NOW(6)),
       ('간장', NOW(6), NOW(6)),
       ('국간장', NOW(6), NOW(6)),
       ('멸치액젓', NOW(6), NOW(6)),
       ('새우젓', NOW(6), NOW(6)),
       ('돼지고기 앞다리살', NOW(6), NOW(6)),
       ('돼지고기 삼겹살', NOW(6), NOW(6)),
       ('소고기 국거리', NOW(6), NOW(6)),
       ('소고기 등심', NOW(6), NOW(6)),
       ('닭다리살', NOW(6), NOW(6)),
       ('닭가슴살', NOW(6), NOW(6)),
       ('베이컨', NOW(6), NOW(6)),
       ('햄', NOW(6), NOW(6)),
       ('달걀', NOW(6), NOW(6)),
       ('두부', NOW(6), NOW(6)),
       ('순두부', NOW(6), NOW(6)),
       ('오징어', NOW(6), NOW(6)),
       ('새우', NOW(6), NOW(6)),
       ('바지락', NOW(6), NOW(6)),
       ('연어', NOW(6), NOW(6)),
       ('참치캔', NOW(6), NOW(6)),
       ('쌀', NOW(6), NOW(6)),
       ('밥', NOW(6), NOW(6)),
       ('떡볶이떡', NOW(6), NOW(6)),
       ('우동면', NOW(6), NOW(6)),
       ('스파게티면', NOW(6), NOW(6)),
       ('라면사리', NOW(6), NOW(6)),
       ('올리브오일', NOW(6), NOW(6)),
       ('버터', NOW(6), NOW(6)),
       ('파마산치즈', NOW(6), NOW(6)),
       ('모짜렐라치즈', NOW(6), NOW(6)),
       ('생크림', NOW(6), NOW(6)),
       ('우유', NOW(6), NOW(6)),
       ('토마토소스', NOW(6), NOW(6)),
       ('케첩', NOW(6), NOW(6)),
       ('화이트와인', NOW(6), NOW(6)),
       ('페페론치노', NOW(6), NOW(6)),
       ('파슬리', NOW(6), NOW(6)),
       ('간장(일식)', NOW(6), NOW(6)),
       ('미림', NOW(6), NOW(6)),
       ('식초', NOW(6), NOW(6)),
       ('마요네즈', NOW(6), NOW(6)),
       ('고추기름', NOW(6), NOW(6)),
       ('굴소스', NOW(6), NOW(6)),
       ('두반장', NOW(6), NOW(6)),
       ('춘장', NOW(6), NOW(6)),
       ('카레가루', NOW(6), NOW(6)),
       ('코코넛밀크', NOW(6), NOW(6)),
       ('피시소스', NOW(6), NOW(6)),
       ('라임즙', NOW(6), NOW(6)),
       ('밀가루', NOW(6), NOW(6)),
       ('베이킹파우더', NOW(6), NOW(6)),
       ('꿀', NOW(6), NOW(6)),
       ('바나나', NOW(6), NOW(6)),
       ('딸기', NOW(6), NOW(6)),
       ('요거트', NOW(6), NOW(6)),
       ('초콜릿', NOW(6), NOW(6)),
       ('고사리', NOW(6), NOW(6)),
       ('콩나물', NOW(6), NOW(6)),
       ('숙주나물', NOW(6), NOW(6)),
       ('미나리', NOW(6), NOW(6)),
       ('부추', NOW(6), NOW(6)),
       ('깻잎', NOW(6), NOW(6)),
       ('배추', NOW(6), NOW(6)),
       ('파프리카', NOW(6), NOW(6)),
       ('피망', NOW(6), NOW(6)),
       ('가지', NOW(6), NOW(6)),
       ('고구마', NOW(6), NOW(6)),
       ('옥수수', NOW(6), NOW(6)),
       ('고등어', NOW(6), NOW(6)),
       ('갈치', NOW(6), NOW(6)),
       ('삼치', NOW(6), NOW(6)),
       ('꽁치', NOW(6), NOW(6)),
       ('멸치', NOW(6), NOW(6)),
       ('북어', NOW(6), NOW(6)),
       ('명태', NOW(6), NOW(6)),
       ('대구', NOW(6), NOW(6)),
       ('굴', NOW(6), NOW(6)),
       ('전복', NOW(6), NOW(6)),
       ('게', NOW(6), NOW(6)),
       ('조개', NOW(6), NOW(6)),
       ('홍합', NOW(6), NOW(6)),
       ('낙지', NOW(6), NOW(6)),
       ('문어', NOW(6), NOW(6)),
       ('해파리', NOW(6), NOW(6)),
       ('미역', NOW(6), NOW(6)),
       ('다시마', NOW(6), NOW(6)),
       ('김', NOW(6), NOW(6)),
       ('매실', NOW(6), NOW(6)),
       ('물엿', NOW(6), NOW(6)),
       ('올리고당', NOW(6), NOW(6)),
       ('청주', NOW(6), NOW(6)),
       ('소주', NOW(6), NOW(6)),
       ('맛술', NOW(6), NOW(6)),
       ('굵은소금', NOW(6), NOW(6)),
       ('천일염', NOW(6), NOW(6)),
       ('흑후추', NOW(6), NOW(6)),
       ('백후추', NOW(6), NOW(6)),
       ('계피', NOW(6), NOW(6)),
       ('팔각', NOW(6), NOW(6)),
       ('정향', NOW(6), NOW(6)),
       ('넛맥', NOW(6), NOW(6)),
       ('카다몬', NOW(6), NOW(6)),
       ('강황', NOW(6), NOW(6)),
       ('파프리카 파우더', NOW(6), NOW(6)),
       ('칠리 파우더', NOW(6), NOW(6)),
       ('오레가노', NOW(6), NOW(6)),
       ('바질', NOW(6), NOW(6)),
       ('로즈마리', NOW(6), NOW(6)),
       ('타임', NOW(6), NOW(6)),
       ('딜', NOW(6), NOW(6)),
       ('고수', NOW(6), NOW(6)),
       ('민트', NOW(6), NOW(6)),
       ('월계수잎', NOW(6), NOW(6)),
       ('치즈 가루', NOW(6), NOW(6)),
       ('빵가루', NOW(6), NOW(6)),
       ('튀김가루', NOW(6), NOW(6)),
       ('부침가루', NOW(6), NOW(6)),
       ('전분', NOW(6), NOW(6)),
       ('녹말', NOW(6), NOW(6)),
       ('타피오카', NOW(6), NOW(6)),
       ('젤라틴', NOW(6), NOW(6)),
       ('한천', NOW(6), NOW(6)),
       ('이스트', NOW(6), NOW(6)),
       ('베이킹소다', NOW(6), NOW(6)),
       ('슬라이스 치즈', NOW(6), NOW(6)),
       ('체다 치즈', NOW(6), NOW(6)),
       ('크림치즈', NOW(6), NOW(6)),
       ('리코타 치즈', NOW(6), NOW(6)),
       ('고르곤졸라', NOW(6), NOW(6)),
       ('브리 치즈', NOW(6), NOW(6)),
       ('카망베르', NOW(6), NOW(6)),
       ('할루미 치즈', NOW(6), NOW(6)),
       ('페타 치즈', NOW(6), NOW(6)),
       ('마스카포네', NOW(6), NOW(6)),
       ('사워크림', NOW(6), NOW(6)),
       ('휘핑크림', NOW(6), NOW(6)),
       ('연유', NOW(6), NOW(6)),
       ('두유', NOW(6), NOW(6)),
       ('아몬드 밀크', NOW(6), NOW(6)),
       ('귀리 밀크', NOW(6), NOW(6)),
       ('양고기', NOW(6), NOW(6)),
       ('오리고기', NOW(6), NOW(6)),
       ('소시지', NOW(6), NOW(6)),
       ('프로슈토', NOW(6), NOW(6)),
       ('살라미', NOW(6), NOW(6)),
       ('판체타', NOW(6), NOW(6)),
       ('스팸', NOW(6), NOW(6)),
       ('어묵', NOW(6), NOW(6)),
       ('맛살', NOW(6), NOW(6)),
       ('생선살', NOW(6), NOW(6)),
       ('건새우', NOW(6), NOW(6)),
       ('마른오징어', NOW(6), NOW(6)),
       ('포도씨유', NOW(6), NOW(6)),
       ('해바라기씨유', NOW(6), NOW(6)),
       ('코코넛오일', NOW(6), NOW(6)),
       ('들기름', NOW(6), NOW(6)),
       ('현미유', NOW(6), NOW(6)),
       ('카놀라유', NOW(6), NOW(6)),
       ('아보카도오일', NOW(6), NOW(6)),
       ('트러플오일', NOW(6), NOW(6)),
       ('발사믹식초', NOW(6), NOW(6)),
       ('레드와인식초', NOW(6), NOW(6)),
       ('화이트와인식초', NOW(6), NOW(6)),
       ('사과식초', NOW(6), NOW(6)),
       ('현미식초', NOW(6), NOW(6)),
       ('간마늘', NOW(6), NOW(6)),
       ('다진마늘', NOW(6), NOW(6)),
       ('간생강', NOW(6), NOW(6)),
       ('들깨가루', NOW(6), NOW(6)),
       ('깨소금', NOW(6), NOW(6)),
       ('볶은깨', NOW(6), NOW(6)),
       ('검은깨', NOW(6), NOW(6)),
       ('흰깨', NOW(6), NOW(6)),
       ('잣', NOW(6), NOW(6)),
       ('통깨', NOW(6), NOW(6)),
       ('호두', NOW(6), NOW(6)),
       ('아몬드', NOW(6), NOW(6)),
       ('캐슈넛', NOW(6), NOW(6)),
       ('피칸', NOW(6), NOW(6)),
       ('마카다미아', NOW(6), NOW(6)),
       ('피스타치오', NOW(6), NOW(6)),
       ('땅콩', NOW(6), NOW(6)),
       ('건포도', NOW(6), NOW(6)),
       ('크랜베리', NOW(6), NOW(6)),
       ('블루베리', NOW(6), NOW(6)),
       ('라즈베리', NOW(6), NOW(6)),
       ('체리', NOW(6), NOW(6)),
       ('망고', NOW(6), NOW(6)),
       ('파인애플', NOW(6), NOW(6)),
       ('복숭아', NOW(6), NOW(6)),
       ('배', NOW(6), NOW(6)),
       ('사과', NOW(6), NOW(6)),
       ('감', NOW(6), NOW(6)),
       ('귤', NOW(6), NOW(6)),
       ('오렌지', NOW(6), NOW(6)),
       ('자몽', NOW(6), NOW(6)),
       ('키위', NOW(6), NOW(6)),
       ('수박', NOW(6), NOW(6)),
       ('참외', NOW(6), NOW(6)),
       ('멜론', NOW(6), NOW(6)),
       ('포도', NOW(6), NOW(6)),
       ('무화과', NOW(6), NOW(6)),
       ('석류', NOW(6), NOW(6)),
       ('대추', NOW(6), NOW(6)),
       ('밤', NOW(6), NOW(6)),
       ('은행', NOW(6), NOW(6)),
       ('연근', NOW(6), NOW(6)),
       ('우엉', NOW(6), NOW(6)),
       ('도라지', NOW(6), NOW(6)),
       ('더덕', NOW(6), NOW(6)),
       ('인삼', NOW(6), NOW(6)),
       ('콩', NOW(6), NOW(6)),
       ('검은콩', NOW(6), NOW(6)),
       ('팥', NOW(6), NOW(6)),
       ('녹두', NOW(6), NOW(6)),
       ('병아리콩', NOW(6), NOW(6)),
       ('렌틸콩', NOW(6), NOW(6)),
       ('조기', NOW(6), NOW(6)),
       ('광어', NOW(6), NOW(6)),
       ('도미', NOW(6), NOW(6)),
       ('농어', NOW(6), NOW(6)),
       ('우럭', NOW(6), NOW(6)),
       ('민어', NOW(6), NOW(6)),
       ('방어', NOW(6), NOW(6)),
       ('참돔', NOW(6), NOW(6)),
       ('전갱이', NOW(6), NOW(6)),
       ('청어', NOW(6), NOW(6)),
       ('정어리', NOW(6), NOW(6)),
       ('진간장', NOW(6), NOW(6)),
       ('양조간장', NOW(6), NOW(6)),
       ('저염간장', NOW(6), NOW(6)),
       ('레드페퍼', NOW(6), NOW(6)),
       ('할라피뇨', NOW(6), NOW(6)),
       ('하바네로', NOW(6), NOW(6));

-- 레시피 재료
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (150,'2026-02-05 16:42:30.839950',158,NULL,31,'2026-02-05 16:42:30.839950','건고사리','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:42:30.842923',159,81,31,'2026-02-05 16:42:30.842923','밀가루','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1.5,'2026-02-05 16:42:30.843438',160,NULL,31,'2026-02-05 16:42:30.843438','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:42:30.843827',161,33,31,'2026-02-05 16:42:30.843827','국간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:42:30.844272',162,274,31,'2026-02-05 16:42:30.844272','진간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:42:30.844671',163,5,31,'2026-02-05 16:42:30.844671','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:42:30.845070',164,8,31,'2026-02-05 16:42:30.845070','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:42:30.845528',165,217,31,'2026-02-05 16:42:30.845528','통깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:42:30.845956',166,NULL,31,'2026-02-05 16:42:30.845956','들기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:42:30.846426',167,210,31,'2026-02-05 16:42:30.846426','들깨가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 16:45:46.376337',168,20,32,'2026-02-05 16:45:46.376337','양배추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 16:45:46.377106',169,NULL,32,'2026-02-05 16:45:46.377106','적양배추','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (30,'2026-02-05 16:45:46.377627',170,14,32,'2026-02-05 16:45:46.377627','당근','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:45:46.378159',171,NULL,32,'2026-02-05 16:45:46.378159','그릭요거트','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:45:46.378547',172,NULL,32,'2026-02-05 16:45:46.378547','들기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:45:46.378985',173,NULL,32,'2026-02-05 16:45:46.378985','검은깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:45:46.379381',174,NULL,32,'2026-02-05 16:45:46.379381','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:45:46.379768',175,1,32,'2026-02-05 16:45:46.379768','소금','꼬집');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:45:46.380073',176,2,32,'2026-02-05 16:45:46.380073','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:45:46.380442',177,71,32,'2026-02-05 16:45:46.380442','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:45:46.380776',178,NULL,32,'2026-02-05 16:45:46.380776','올리고당','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:46:50.067613',179,262,33,'2026-02-05 16:46:50.067613','조기','마리');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:46:50.068290',180,NULL,33,'2026-02-05 16:46:50.068290','천일염','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:46:50.068649',181,NULL,33,'2026-02-05 16:46:50.068649','소주','잔');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:46:50.069026',182,8,33,'2026-02-05 16:46:50.069026','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:03.259681',183,8,34,'2026-02-05 16:47:03.259681','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:03.260514',184,30,34,'2026-02-05 16:47:03.260514','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:03.260916',185,71,34,'2026-02-05 16:47:03.260916','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:03.261250',186,32,34,'2026-02-05 16:47:03.261250','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:03.261610',187,1,34,'2026-02-05 16:47:03.261610','소금','꼬집');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:03.261950',188,217,34,'2026-02-05 16:47:03.261950','통깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:03.262488',189,5,34,'2026-02-05 16:47:03.262488','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:03.262817',190,3,34,'2026-02-05 16:47:03.262817','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:03.263148',191,NULL,34,'2026-02-05 16:47:03.263148','올리고당','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:47:03.263468',192,10,34,'2026-02-05 16:47:03.263468','마늘','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:47:03.263733',193,11,34,'2026-02-05 16:47:03.263733','생강','톨');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.6,'2026-02-05 16:47:03.264021',194,1,34,'2026-02-05 16:47:03.264021','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.3,'2026-02-05 16:47:03.264318',195,2,34,'2026-02-05 16:47:03.264318','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:47:03.264590',196,214,34,'2026-02-05 16:47:03.264590','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:03.264876',197,4,34,'2026-02-05 16:47:03.264876','식용유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (600,'2026-02-05 16:47:03.265144',198,36,34,'2026-02-05 16:47:03.265144','돼지고기 앞다리살','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (8,'2026-02-05 16:47:03.265434',199,205,34,'2026-02-05 16:47:03.265434','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 16:47:03.265899',200,89,34,'2026-02-05 16:47:03.265899','부추','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:17.305136',201,16,35,'2026-02-05 16:47:17.305136','무','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:17.305999',202,NULL,35,'2026-02-05 16:47:17.305999','알배추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (150,'2026-02-05 16:47:17.306664',203,9,35,'2026-02-05 16:47:17.306664','쪽파','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:17.307306',204,107,35,'2026-02-05 16:47:17.307306','배','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 16:47:17.307826',205,12,35,'2026-02-05 16:47:17.307826','청양고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:17.308342',206,13,35,'2026-02-05 16:47:17.308342','홍고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (7.5,'2026-02-05 16:47:17.308718',207,NULL,35,'2026-02-05 16:47:17.308718','꽃소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:17.309069',208,169,35,'2026-02-05 16:47:17.309069','찹쌀가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:17.309480',209,180,35,'2026-02-05 16:47:17.309480','다시마','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:17.309953',210,7,35,'2026-02-05 16:47:17.309953','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 16:47:17.310461',211,10,35,'2026-02-05 16:47:17.310461','마늘','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:17.310939',212,11,35,'2026-02-05 16:47:17.310939','생강','톨');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:47:17.312345',213,30,35,'2026-02-05 16:47:17.312345','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 16:47:17.313313',214,214,35,'2026-02-05 16:47:17.313313','물','L');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:20.392991',215,20,36,'2026-02-05 16:47:20.392991','양배추','통');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:20.393686',216,NULL,36,'2026-02-05 16:47:20.393686','천일염','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:20.394391',217,3,36,'2026-02-05 16:47:20.394391','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:20.394765',218,121,36,'2026-02-05 16:47:20.394765','물엿','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:20.395095',219,NULL,36,'2026-02-05 16:47:20.395095','적양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:47:20.395436',220,14,36,'2026-02-05 16:47:20.395436','당근','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:20.395758',221,4,36,'2026-02-05 16:47:20.395758','식용유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:20.396580',222,NULL,36,'2026-02-05 16:47:20.396580','들기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:20.396937',223,NULL,36,'2026-02-05 16:47:20.396937','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:47:20.397346',224,NULL,36,'2026-02-05 16:47:20.397346','굵은 고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:20.397746',225,33,36,'2026-02-05 16:47:20.397746','국간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:47:20.398056',226,210,36,'2026-02-05 16:47:20.398056','들깨가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 16:47:43.835586',227,NULL,37,'2026-02-05 16:47:43.835586','손질 도라지','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:43.836191',228,21,37,'2026-02-05 16:47:43.836191','오이','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:43.836680',229,NULL,37,'2026-02-05 16:47:43.836680','천일염','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:47:43.837025',230,NULL,37,'2026-02-05 16:47:43.837025','원당','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:47:43.837353',231,NULL,37,'2026-02-05 16:47:43.837353','고운 소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:47:43.837668',232,30,37,'2026-02-05 16:47:43.837668','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:43.837989',233,NULL,37,'2026-02-05 16:47:43.837989','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:43.838290',234,274,37,'2026-02-05 16:47:43.838290','진간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:43.838594',235,29,37,'2026-02-05 16:47:43.838594','고추장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:47:43.838878',236,NULL,37,'2026-02-05 16:47:43.838878','양조식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:43.839138',237,NULL,37,'2026-02-05 16:47:43.839138','매실액','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:47:43.839417',238,121,37,'2026-02-05 16:47:43.839417','물엿','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:43.839672',239,217,37,'2026-02-05 16:47:43.839672','통깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 16:47:48.536178',240,12,38,'2026-02-05 16:47:48.536178','청양고추','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:48.536786',241,180,38,'2026-02-05 16:47:48.536786','다시마','조각');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:48.537166',242,NULL,38,'2026-02-05 16:47:48.537166','뜨거운 물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:47:48.537619',243,5,38,'2026-02-05 16:47:48.537619','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:47:48.538119',244,NULL,38,'2026-02-05 16:47:48.538119','양조간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:48.538603',245,217,38,'2026-02-05 16:47:48.538603','통깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:51.616322',246,144,39,'2026-02-05 16:47:51.616322','당면','kg');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:47:51.616831',247,NULL,39,'2026-02-05 16:47:51.616831','건표고버섯','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (60,'2026-02-05 16:47:51.617142',248,NULL,39,'2026-02-05 16:47:51.617142','건목이버섯','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.617455',249,126,39,'2026-02-05 16:47:51.617455','느타리버섯','kg');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (600,'2026-02-05 16:47:51.617744',250,14,39,'2026-02-05 16:47:51.617744','당근','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:47:51.618029',251,7,39,'2026-02-05 16:47:51.618029','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1.2,'2026-02-05 16:47:51.618294',252,22,39,'2026-02-05 16:47:51.618294','시금치','kg');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2.4,'2026-02-05 16:47:51.618540',253,214,39,'2026-02-05 16:47:51.618540','물','L');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.618785',254,180,39,'2026-02-05 16:47:51.618785','다시마','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:47:51.619028',255,NULL,39,'2026-02-05 16:47:51.619028','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.619331',256,70,39,'2026-02-05 16:47:51.619331','미림','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.619628',257,3,39,'2026-02-05 16:47:51.619628','설탕','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:51.619890',258,NULL,39,'2026-02-05 16:47:51.619890','양조간장','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.620135',259,2,39,'2026-02-05 16:47:51.620135','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:47:51.620393',260,4,39,'2026-02-05 16:47:51.620393','식용유','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.620650',261,5,39,'2026-02-05 16:47:51.620650','참기름','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:47:51.620981',262,NULL,39,'2026-02-05 16:47:51.620981','천일염','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:47:51.621249',263,217,39,'2026-02-05 16:47:51.621249','통깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 16:48:21.791248',264,89,40,'2026-02-05 16:48:21.791248','부추','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 16:48:21.792044',265,116,40,'2026-02-05 16:48:21.792044','팽이버섯','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 16:48:21.792530',266,126,40,'2026-02-05 16:48:21.792530','느타리버섯','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 16:48:21.793047',267,19,40,'2026-02-05 16:48:21.793047','표고버섯','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 16:48:21.793498',268,45,40,'2026-02-05 16:48:21.793498','두부','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 16:48:21.793908',269,144,40,'2026-02-05 16:48:21.793908','당면','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 16:48:21.794252',270,NULL,40,'2026-02-05 16:48:21.794252','돼지 앞다리살','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:48:21.794617',271,11,40,'2026-02-05 16:48:21.794617','생강','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.795011',272,1,40,'2026-02-05 16:48:21.795011','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.795305',273,NULL,40,'2026-02-05 16:48:21.795305','묵은지','포기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.795638',274,35,40,'2026-02-05 16:48:21.795638','새우젓','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.796056',275,29,40,'2026-02-05 16:48:21.796056','고추장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.796420',276,30,40,'2026-02-05 16:48:21.796420','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.796740',277,NULL,40,'2026-02-05 16:48:21.796740','조선간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.797110',278,70,40,'2026-02-05 16:48:21.797110','미림','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.797437',279,8,40,'2026-02-05 16:48:21.797437','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.797768',280,229,40,'2026-02-05 16:48:21.797768','다진마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:48:21.798142',281,NULL,40,'2026-02-05 16:48:21.798142','김치 국물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:48:21.798461',282,214,40,'2026-02-05 16:48:21.798461','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (150,'2026-02-05 16:49:39.707258',285,NULL,42,'2026-02-05 16:49:39.707258','카다이프','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 16:49:39.708082',286,NULL,42,'2026-02-05 16:49:39.708082','무염버터','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 16:49:39.708388',287,NULL,42,'2026-02-05 16:49:39.708388','모짜렐라 치즈','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 16:49:39.708662',288,NULL,42,'2026-02-05 16:49:39.708662','아몬드 슬라이스','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:49:39.708974',289,3,42,'2026-02-05 16:49:39.708974','설탕','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:49:39.709334',290,214,42,'2026-02-05 16:49:39.709334','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (400,'2026-02-05 16:51:21.766082',291,NULL,43,'2026-02-05 16:51:21.766082','소 안심','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:51:21.766766',292,2,43,'2026-02-05 16:51:21.766766','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:51:21.767064',293,1,43,'2026-02-05 16:51:21.767064','소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:51:21.767330',294,NULL,43,'2026-02-05 16:51:21.767330','샬롯','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:51:21.767607',295,136,43,'2026-02-05 16:51:21.767607','양송이버섯','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 16:51:21.767923',296,NULL,43,'2026-02-05 16:51:21.767923','브랜디','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 16:51:21.768155',297,62,43,'2026-02-05 16:51:21.768155','생크림','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:51:28.593121',298,15,44,'2026-02-05 16:51:28.593121','감자','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:51:28.593884',299,10,44,'2026-02-05 16:51:28.593884','마늘','알');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:28.594556',300,NULL,44,'2026-02-05 16:51:28.594556','로즈마리','줄기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:28.594863',301,267,44,'2026-02-05 16:51:28.594863','월계수잎','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:28.595191',302,1,44,'2026-02-05 16:51:28.595191','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 16:51:28.595781',303,4,44,'2026-02-05 16:51:28.595781','식용유','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:51:28.596140',304,65,44,'2026-02-05 16:51:28.596140','케첩','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:51:28.596582',305,NULL,44,'2026-02-05 16:51:28.596582','파프리카 가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:50.332722',306,NULL,45,'2026-02-05 16:51:50.332722','홍어애','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:50.333198',307,NULL,45,'2026-02-05 16:51:50.333198','트러플','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:51:50.333579',308,180,45,'2026-02-05 16:51:50.333579','다시마','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:51:50.333890',309,NULL,45,'2026-02-05 16:51:50.333890','가쓰오부시','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:50.334247',310,275,45,'2026-02-05 16:51:50.334247','청주','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:50.334538',311,70,45,'2026-02-05 16:51:50.334538','미림','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:50.334808',312,274,45,'2026-02-05 16:51:50.334808','진간장','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:51:50.335066',313,53,45,'2026-02-05 16:51:50.335066','밥','공기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:51:50.335283',314,205,45,'2026-02-05 16:51:50.335283','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:51:50.335491',315,71,45,'2026-02-05 16:51:50.335491','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:51:50.335717',316,3,45,'2026-02-05 16:51:50.335717','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.2,'2026-02-05 16:51:50.335936',317,1,45,'2026-02-05 16:51:50.335936','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:54:23.296523',318,205,46,'2026-02-05 16:54:23.296523','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.297413',319,45,46,'2026-02-05 16:54:23.297413','두부','모');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 16:54:23.297851',320,214,46,'2026-02-05 16:54:23.297851','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:54:23.298286',321,74,46,'2026-02-05 16:54:23.298286','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.298659',322,3,46,'2026-02-05 16:54:23.298659','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.298979',323,70,46,'2026-02-05 16:54:23.298979','미림','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.299450',324,32,46,'2026-02-05 16:54:23.299450','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:54:23.299926',325,95,46,'2026-02-05 16:54:23.299926','전분','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.300399',326,NULL,46,'2026-02-05 16:54:23.300399','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:54:23.300772',327,8,46,'2026-02-05 16:54:23.300772','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.301036',328,213,46,'2026-02-05 16:54:23.301036','청고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:23.301298',329,13,46,'2026-02-05 16:54:23.301298','홍고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:54:30.869193',330,21,47,'2026-02-05 16:54:30.869193','오이','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:54:30.869657',331,NULL,47,'2026-02-05 16:54:30.869657','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:54:30.869913',332,8,47,'2026-02-05 16:54:30.869913','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:30.870144',333,12,47,'2026-02-05 16:54:30.870144','청양고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:30.870368',334,75,47,'2026-02-05 16:54:30.870368','두반장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:30.870574',335,74,47,'2026-02-05 16:54:30.870574','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1.5,'2026-02-05 16:54:30.870780',336,3,47,'2026-02-05 16:54:30.870780','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:54:30.870979',337,71,47,'2026-02-05 16:54:30.870979','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:30.871179',338,32,47,'2026-02-05 16:54:30.871179','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:54:30.871429',339,73,47,'2026-02-05 16:54:30.871429','고추기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:54:51.931064',340,205,48,'2026-02-05 16:54:51.931064','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 16:54:51.931533',341,48,48,'2026-02-05 16:54:51.931533','새우','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (30,'2026-02-05 16:54:51.931917',342,116,48,'2026-02-05 16:54:51.931917','팽이버섯','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 16:54:51.932276',343,8,48,'2026-02-05 16:54:51.932276','대파','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:51.932627',344,1,48,'2026-02-05 16:54:51.932627','소금','꼬집');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:51.932887',345,53,48,'2026-02-05 16:54:51.932887','밥','공기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (250,'2026-02-05 16:54:51.933123',346,214,48,'2026-02-05 16:54:51.933123','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:54:51.933391',347,151,48,'2026-02-05 16:54:51.933391','완두콩','알');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:54:51.933670',348,70,48,'2026-02-05 16:54:51.933670','미림','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:51.933927',349,74,48,'2026-02-05 16:54:51.933927','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:51.934546',350,32,48,'2026-02-05 16:54:51.934546','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:54:51.934814',351,3,48,'2026-02-05 16:54:51.934814','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:54:51.935041',352,NULL,48,'2026-02-05 16:54:51.935041','감자전분','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 16:56:03.935096',353,NULL,49,'2026-02-05 16:56:03.935096','소고기','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:56:03.935589',354,1,49,'2026-02-05 16:56:03.935589','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:56:03.936011',355,2,49,'2026-02-05 16:56:03.936011','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.936247',356,59,49,'2026-02-05 16:56:03.936247','버터','조각');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.936434',357,7,49,'2026-02-05 16:56:03.936434','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:56:03.936608',358,14,49,'2026-02-05 16:56:03.936608','당근','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.936814',359,15,49,'2026-02-05 16:56:03.936814','감자','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.936991',360,NULL,49,'2026-02-05 16:56:03.936991','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (250,'2026-02-05 16:56:03.937298',361,214,49,'2026-02-05 16:56:03.937298','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.937594',362,NULL,49,'2026-02-05 16:56:03.937594','토마토 통조림','캔');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.937885',363,NULL,49,'2026-02-05 16:56:03.937885','치킨스톡','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:56:03.938182',364,NULL,49,'2026-02-05 16:56:03.938182','페퍼론치노','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:03.938429',365,NULL,49,'2026-02-05 16:56:03.938429','파슬리 가루','꼬집');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 16:56:22.120027',366,NULL,50,'2026-02-05 16:56:22.120027','무뼈 닭발','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:22.120540',367,NULL,50,'2026-02-05 16:56:22.120540','소주','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 16:56:22.120905',368,214,50,'2026-02-05 16:56:22.120905','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:22.121210',369,181,50,'2026-02-05 16:56:22.121210','통후추','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:56:22.121526',370,30,50,'2026-02-05 16:56:22.121526','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:56:22.121857',371,29,50,'2026-02-05 16:56:22.121857','고추장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:56:22.122132',372,32,50,'2026-02-05 16:56:22.122132','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:56:22.122387',373,3,50,'2026-02-05 16:56:22.122387','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:56:22.122686',374,121,50,'2026-02-05 16:56:22.122686','물엿','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:22.122978',375,NULL,50,'2026-02-05 16:56:22.122978','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:56:22.123323',376,NULL,50,'2026-02-05 16:56:22.123323','다진 생강','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:22.123621',377,74,50,'2026-02-05 16:56:22.123621','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:56:22.123897',378,NULL,50,'2026-02-05 16:56:22.123897','매실청','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:56:22.124164',379,2,50,'2026-02-05 16:56:22.124164','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 16:56:22.124429',380,273,50,'2026-02-05 16:56:22.124429','콩나물','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:56:22.124693',381,1,50,'2026-02-05 16:56:22.124693','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:56:22.124954',382,8,50,'2026-02-05 16:56:22.124954','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:56:30.907697',383,205,51,'2026-02-05 16:56:30.907697','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:56:30.909468',384,214,51,'2026-02-05 16:56:30.909468','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:30.909951',385,NULL,51,'2026-02-05 16:56:30.909951','꽃게액','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.2,'2026-02-05 16:56:30.910379',386,NULL,51,'2026-02-05 16:56:30.910379','맛소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:56:30.910936',387,5,51,'2026-02-05 16:56:30.910936','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:56:30.911533',388,8,51,'2026-02-05 16:56:30.911533','대파','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:56:30.911938',389,217,51,'2026-02-05 16:56:30.911938','통깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 16:57:36.073331',390,48,52,'2026-02-05 16:57:36.073331','새우','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:57:36.073802',391,44,52,'2026-02-05 16:57:36.073802','달걀','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:57:36.074344',392,NULL,52,'2026-02-05 16:57:36.074344','식빵','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 16:57:36.074784',393,7,52,'2026-02-05 16:57:36.074784','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:57:36.075461',394,NULL,52,'2026-02-05 16:57:36.075461','셀러리','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:57:36.075995',395,68,52,'2026-02-05 16:57:36.075995','파슬리','줄기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:57:36.076341',396,163,52,'2026-02-05 16:57:36.076341','레몬즙','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:57:36.076660',397,59,52,'2026-02-05 16:57:36.076660','버터','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:57:36.077040',398,65,52,'2026-02-05 16:57:36.077040','케첩','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:08.033163',399,NULL,53,'2026-02-05 16:58:08.033163','신라면 골드','봉지');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 16:58:08.033835',400,214,53,'2026-02-05 16:58:08.033835','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:08.034317',401,136,53,'2026-02-05 16:58:08.034317','양송이버섯','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:58:08.034772',402,12,53,'2026-02-05 16:58:08.034772','청양고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:38.385227',403,NULL,54,'2026-02-05 16:58:38.385227','숏 파스타','인분');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (220,'2026-02-05 16:58:38.385841',404,NULL,54,'2026-02-05 16:58:38.385841','토마토 퓨레','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 16:58:38.387245',405,NULL,54,'2026-02-05 16:58:38.387245','홀토마토 통조림','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:58:38.387618',406,NULL,54,'2026-02-05 16:58:38.387618','대추 방울토마토','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:38.387925',407,10,54,'2026-02-05 16:58:38.387925','마늘','쪽');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:58:38.388203',408,8,54,'2026-02-05 16:58:38.388203','대파','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:58:38.388492',409,NULL,54,'2026-02-05 16:58:38.388492','올리브유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 16:58:38.388843',410,214,54,'2026-02-05 16:58:38.388843','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:58:38.389221',411,1,54,'2026-02-05 16:58:38.389221','소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:58:38.389617',412,3,54,'2026-02-05 16:58:38.389617','설탕','꼬집');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:58:38.390021',413,101,54,'2026-02-05 16:58:38.390021','바질','잎');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:58:38.390388',414,2,54,'2026-02-05 16:58:38.390388','후추','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:38.390840',415,NULL,54,'2026-02-05 16:58:38.390840','치즈 가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (600,'2026-02-05 16:58:57.831225',416,218,55,'2026-02-05 16:58:57.831225','돼지고기','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:57.831740',417,188,55,'2026-02-05 16:58:57.831740','우엉','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:58:57.832026',418,19,55,'2026-02-05 16:58:57.832026','표고버섯','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 16:58:57.832226',419,16,55,'2026-02-05 16:58:57.832226','무','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:58:57.832418',420,11,55,'2026-02-05 16:58:57.832418','생강','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:57.832588',421,8,55,'2026-02-05 16:58:57.832588','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (30,'2026-02-05 16:58:57.832754',422,9,55,'2026-02-05 16:58:57.832754','쪽파','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (11,'2026-02-05 16:58:57.832923',423,10,55,'2026-02-05 16:58:57.832923','마늘','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:57.833091',424,NULL,55,'2026-02-05 16:58:57.833091','천일염','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1.5,'2026-02-05 16:58:57.833272',425,214,55,'2026-02-05 16:58:57.833272','물','L');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:58:57.833505',426,12,55,'2026-02-05 16:58:57.833505','청양고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:57.833731',427,35,55,'2026-02-05 16:58:57.833731','새우젓','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:57.833936',428,30,55,'2026-02-05 16:58:57.833936','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:58:57.834120',429,3,55,'2026-02-05 16:58:57.834120','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:58:57.834329',430,5,55,'2026-02-05 16:58:57.834329','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (120,'2026-02-05 16:59:04.636611',431,NULL,56,'2026-02-05 16:59:04.636611','파스타면','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (15,'2026-02-05 16:59:04.637393',432,1,56,'2026-02-05 16:59:04.637393','소금','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2.5,'2026-02-05 16:59:04.638135',433,NULL,56,'2026-02-05 16:59:04.638135','올리브 오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1.5,'2026-02-05 16:59:04.638896',434,10,56,'2026-02-05 16:59:04.638896','마늘','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:04.639461',435,NULL,56,'2026-02-05 16:59:04.639461','엔초비 페이스트','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:04.640027',436,67,56,'2026-02-05 16:59:04.640027','페페론치노','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:59:04.640587',437,NULL,56,'2026-02-05 16:59:04.640587','파르미지아노 레지아노','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 16:59:36.026490',438,NULL,57,'2026-02-05 16:59:36.026490','피스타치오','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:59:36.027003',439,1,57,'2026-02-05 16:59:36.027003','소금','꼬집');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 16:59:36.027311',440,NULL,57,'2026-02-05 16:59:36.027311','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 16:59:36.027972',441,4,57,'2026-02-05 16:59:36.027972','식용유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 16:59:36.028368',442,NULL,57,'2026-02-05 16:59:36.028368','두유면','봉');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 16:59:36.028600',443,NULL,57,'2026-02-05 16:59:36.028600','타피오카 전분','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (7,'2026-02-05 16:59:36.028798',444,63,57,'2026-02-05 16:59:36.028798','우유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:36.029041',445,NULL,57,'2026-02-05 16:59:36.029041','무가당 코코아가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.761706',446,NULL,58,'2026-02-05 16:59:45.761706','양상추','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.762599',447,21,58,'2026-02-05 16:59:45.762599','오이','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:59:45.763162',448,7,58,'2026-02-05 16:59:45.763162','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.763528',449,32,58,'2026-02-05 16:59:45.763528','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:59:45.763865',450,NULL,58,'2026-02-05 16:59:45.763865','저당 굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:59:45.764232',451,NULL,58,'2026-02-05 16:59:45.764232','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.764587',452,NULL,58,'2026-02-05 16:59:45.764587','저당 케첩','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 16:59:45.764958',453,48,58,'2026-02-05 16:59:45.764958','새우','마리');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.765352',454,205,58,'2026-02-05 16:59:45.765352','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:59:45.765689',455,1,58,'2026-02-05 16:59:45.765689','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.766006',456,58,58,'2026-02-05 16:59:45.766006','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (130,'2026-02-05 16:59:45.766293',457,53,58,'2026-02-05 16:59:45.766293','밥','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.766547',458,5,58,'2026-02-05 16:59:45.766547','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.766866',459,NULL,58,'2026-02-05 16:59:45.766866','병아리콩','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.767263',460,NULL,58,'2026-02-05 16:59:45.767263','양파후레이크','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:59:45.767600',461,2,58,'2026-02-05 16:59:45.767600','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:45.768017',462,NULL,58,'2026-02-05 16:59:45.768017','저당 마요네즈','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.411770',463,32,59,'2026-02-05 16:59:52.411770','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:59:52.412503',464,74,59,'2026-02-05 16:59:52.412503','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 16:59:52.413081',465,NULL,59,'2026-02-05 16:59:52.413081','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.413507',466,65,59,'2026-02-05 16:59:52.413507','케첩','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.413782',467,205,59,'2026-02-05 16:59:52.413782','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 16:59:52.414041',468,NULL,59,'2026-02-05 16:59:52.414041','우삼겹','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.414379',469,20,59,'2026-02-05 16:59:52.414379','양배추','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 16:59:52.414703',470,2,59,'2026-02-05 16:59:52.414703','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.415024',471,NULL,59,'2026-02-05 16:59:52.415024','빵','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.415334',472,72,59,'2026-02-05 16:59:52.415334','마요네즈','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 16:59:52.415658',473,NULL,59,'2026-02-05 16:59:52.415658','가쓰오부시','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (520,'2026-02-05 17:00:02.252266',474,53,60,'2026-02-05 17:00:02.252266','밥','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:00:02.252929',475,5,60,'2026-02-05 17:00:02.252929','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:00:02.253388',476,1,60,'2026-02-05 17:00:02.253388','소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:00:02.253801',477,6,60,'2026-02-05 17:00:02.253801','깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:00:02.254124',478,NULL,60,'2026-02-05 17:00:02.254124','김가루','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:00:02.254456',479,205,60,'2026-02-05 17:00:02.254456','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (400,'2026-02-05 17:00:02.254866',480,NULL,60,'2026-02-05 17:00:02.254866','참치','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:00:02.255558',481,NULL,60,'2026-02-05 17:00:02.255558','저당 마요네즈','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:00:02.255983',482,12,60,'2026-02-05 17:00:02.255983','청양고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 17:00:02.256377',483,28,60,'2026-02-05 17:00:02.256377','김치','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:00:02.256877',484,NULL,60,'2026-02-05 17:00:02.256877','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:00:16.600719',485,NULL,61,'2026-02-05 17:00:16.600719','크래미','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:00:16.601438',486,8,61,'2026-02-05 17:00:16.601438','대파','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 17:00:16.601876',487,7,61,'2026-02-05 17:00:16.601876','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:00:16.602322',488,58,61,'2026-02-05 17:00:16.602322','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:00:16.602786',489,229,61,'2026-02-05 17:00:16.602786','다진마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (130,'2026-02-05 17:00:16.603166',490,NULL,61,'2026-02-05 17:00:16.603166','현미밥','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.8,'2026-02-05 17:00:16.603563',491,74,61,'2026-02-05 17:00:16.603563','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:00:16.604414',492,NULL,61,'2026-02-05 17:00:16.604414','스리라차소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:00:16.604915',493,205,61,'2026-02-05 17:00:16.604915','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:00:16.605653',494,2,61,'2026-02-05 17:00:16.605653','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (180,'2026-02-05 17:03:56.289604',495,NULL,62,'2026-02-05 17:03:56.289604','통밀 푸실리','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:03:56.290259',496,NULL,62,'2026-02-05 17:03:56.290259','양상추','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (8,'2026-02-05 17:03:56.290548',497,228,62,'2026-02-05 17:03:56.290548','방울토마토','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:03:56.290769',498,21,62,'2026-02-05 17:03:56.290769','오이','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:03:56.291026',499,7,62,'2026-02-05 17:03:56.291026','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 17:03:56.291234',500,41,62,'2026-02-05 17:03:56.291234','닭가슴살','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:03:56.291446',501,NULL,62,'2026-02-05 17:03:56.291446','냉동새우','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 17:03:56.291660',502,58,62,'2026-02-05 17:03:56.291660','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (7,'2026-02-05 17:03:56.291885',503,NULL,62,'2026-02-05 17:03:56.291885','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:03:56.292106',504,274,62,'2026-02-05 17:03:56.292106','진간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:03:56.292647',505,71,62,'2026-02-05 17:03:56.292647','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:03:56.293187',506,163,62,'2026-02-05 17:03:56.293187','레몬즙','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:03:56.293464',507,5,62,'2026-02-05 17:03:56.293464','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.8,'2026-02-05 17:03:56.293696',508,NULL,62,'2026-02-05 17:03:56.293696','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:03:56.293936',509,NULL,62,'2026-02-05 17:03:56.293936','스위트콘','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:03:56.294144',510,1,62,'2026-02-05 17:03:56.294144','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:03:56.294572',511,2,62,'2026-02-05 17:03:56.294572','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (80,'2026-02-05 17:05:50.099314',512,NULL,63,'2026-02-05 17:05:50.099314','쌀소면','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1.5,'2026-02-05 17:05:50.099900',513,214,63,'2026-02-05 17:05:50.099900','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:05:50.100321',514,71,63,'2026-02-05 17:05:50.100321','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:05:50.100752',515,NULL,63,'2026-02-05 17:05:50.100752','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:05:50.101020',516,NULL,63,'2026-02-05 17:05:50.101020','참치액','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:05:50.101259',517,32,63,'2026-02-05 17:05:50.101259','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 17:05:50.101502',518,NULL,63,'2026-02-05 17:05:50.101502','신김치','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 17:05:50.101841',519,7,63,'2026-02-05 17:05:50.101841','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 17:05:50.102151',520,21,63,'2026-02-05 17:05:50.102151','오이','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:05:50.102418',521,NULL,63,'2026-02-05 17:05:50.102418','김가루','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:05:50.102722',522,NULL,63,'2026-02-05 17:05:50.102722','삶은 계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 17:05:50.103084',523,NULL,63,'2026-02-05 17:05:50.103084','얼음','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:06.566990',524,15,64,'2026-02-05 17:06:06.566990','감자','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:06:06.567810',525,41,64,'2026-02-05 17:06:06.567810','닭가슴살','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:06:06.568537',526,NULL,64,'2026-02-05 17:06:06.568537','전분가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:06:06.569293',527,1,64,'2026-02-05 17:06:06.569293','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:06:06.569880',528,2,64,'2026-02-05 17:06:06.569880','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:06:06.570357',529,214,64,'2026-02-05 17:06:06.570357','물','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:06:06.570844',530,58,64,'2026-02-05 17:06:06.570844','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:06.571441',531,72,64,'2026-02-05 17:06:06.571441','마요네즈','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:06.572024',532,NULL,64,'2026-02-05 17:06:06.572024','스리라차 소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:06:06.572583',533,NULL,64,'2026-02-05 17:06:06.572583','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 17:06:34.657016',534,NULL,65,'2026-02-05 17:06:34.657016','통밀 파스타','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 17:06:34.657590',535,214,65,'2026-02-05 17:06:34.657590','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:06:34.659565',536,1,65,'2026-02-05 17:06:34.659565','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (8,'2026-02-05 17:06:34.660027',537,NULL,65,'2026-02-05 17:06:34.660027','냉동 새우','마리');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 17:06:34.660465',538,NULL,65,'2026-02-05 17:06:34.660465','명란젓','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:06:34.660909',539,7,65,'2026-02-05 17:06:34.660909','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:34.661248',540,58,65,'2026-02-05 17:06:34.661248','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:34.661611',541,NULL,65,'2026-02-05 17:06:34.661611','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:06:34.661924',542,67,65,'2026-02-05 17:06:34.661924','페페론치노','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:34.662491',543,63,65,'2026-02-05 17:06:34.662491','우유','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:34.662828',544,NULL,65,'2026-02-05 17:06:34.662828','체다치즈','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:06:34.663424',545,2,65,'2026-02-05 17:06:34.663424','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:06:34.663847',546,9,65,'2026-02-05 17:06:34.663847','쪽파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 17:06:34.664195',547,NULL,65,'2026-02-05 17:06:34.664195','가쓰오부시','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.639318',548,NULL,66,'2026-02-05 17:07:28.639318','가자미','마리');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:07:28.640138',549,1,66,'2026-02-05 17:07:28.640138','소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.2,'2026-02-05 17:07:28.640506',550,2,66,'2026-02-05 17:07:28.640506','후추','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:07:28.640855',551,81,66,'2026-02-05 17:07:28.640855','밀가루','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.641194',552,15,66,'2026-02-05 17:07:28.641194','감자','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:07:28.641702',553,58,66,'2026-02-05 17:07:28.641702','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 17:07:28.642155',554,59,66,'2026-02-05 17:07:28.642155','버터','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:07:28.642525',555,NULL,66,'2026-02-05 17:07:28.642525','타임','줄기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:07:28.642973',556,NULL,66,'2026-02-05 17:07:28.642973','화이트 와인','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.643366',557,26,66,'2026-02-05 17:07:28.643366','레몬','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.836855',558,NULL,67,'2026-02-05 17:07:28.836855','컵누들 짬뽕맛','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.25,'2026-02-05 17:07:28.837271',559,7,67,'2026-02-05 17:07:28.837271','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:07:28.837577',560,8,67,'2026-02-05 17:07:28.837577','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.837814',561,149,67,'2026-02-05 17:07:28.837814','고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (8,'2026-02-05 17:07:28.838005',562,NULL,67,'2026-02-05 17:07:28.838005','냉동새우','마리');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.838457',563,237,67,'2026-02-05 17:07:28.838457','목이버섯','줌');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.839117',564,58,67,'2026-02-05 17:07:28.839117','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.839688',565,229,67,'2026-02-05 17:07:28.839688','다진마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.840173',566,74,67,'2026-02-05 17:07:28.840173','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.840518',567,30,67,'2026-02-05 17:07:28.840518','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2.5,'2026-02-05 17:07:28.840900',568,214,67,'2026-02-05 17:07:28.840900','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:07:28.841264',569,205,67,'2026-02-05 17:07:28.841264','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:07:28.841560',570,2,67,'2026-02-05 17:07:28.841560','후추','약간');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:07:35.021850',571,NULL,68,'2026-02-05 17:07:35.021850','계란 노른자','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 17:07:35.022416',572,NULL,68,'2026-02-05 17:07:35.022416','치즈 가루','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:07:35.022741',573,1,68,'2026-02-05 17:07:35.022741','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 17:07:35.023053',574,NULL,68,'2026-02-05 17:07:35.023053','파스타 면','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:07:35.023493',575,42,68,'2026-02-05 17:07:35.023493','베이컨','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:07:35.023807',576,10,68,'2026-02-05 17:07:35.023807','마늘','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:07:35.024013',577,NULL,68,'2026-02-05 17:07:35.024013','올리브유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:07:35.024281',578,67,68,'2026-02-05 17:07:35.024281','페페론치노','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:01.398452',579,NULL,69,'2026-02-05 17:08:01.398452','알배추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.399220',580,32,69,'2026-02-05 17:08:01.399220','간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:01.399720',581,71,69,'2026-02-05 17:08:01.399720','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.400147',582,74,69,'2026-02-05 17:08:01.400147','굴소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:01.400477',583,214,69,'2026-02-05 17:08:01.400477','물','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.400799',584,8,69,'2026-02-05 17:08:01.400799','대파','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:01.401079',585,10,69,'2026-02-05 17:08:01.401079','마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.401350',586,213,69,'2026-02-05 17:08:01.401350','청고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.401628',587,13,69,'2026-02-05 17:08:01.401628','홍고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.401890',588,73,69,'2026-02-05 17:08:01.401890','고추기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:01.402196',589,3,69,'2026-02-05 17:08:01.402196','설탕','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 17:08:06.737163',590,NULL,70,'2026-02-05 17:08:06.737163','삼겹살','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:08:06.737784',591,8,70,'2026-02-05 17:08:06.737784','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.738367',592,7,70,'2026-02-05 17:08:06.738367','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:06.738875',593,1,70,'2026-02-05 17:08:06.738875','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.3,'2026-02-05 17:08:06.739471',594,2,70,'2026-02-05 17:08:06.739471','후추','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:08:06.740039',595,11,70,'2026-02-05 17:08:06.740039','생강','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 17:08:06.740613',596,10,70,'2026-02-05 17:08:06.740613','마늘','알');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:06.741083',597,NULL,70,'2026-02-05 17:08:06.741083','소주','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.741672',598,35,70,'2026-02-05 17:08:06.741672','새우젓','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.742171',599,12,70,'2026-02-05 17:08:06.742171','청양고추','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.742704',600,30,70,'2026-02-05 17:08:06.742704','고춧가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.743053',601,NULL,70,'2026-02-05 17:08:06.743053','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.743383',602,NULL,70,'2026-02-05 17:08:06.743383','참깨','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:06.743774',603,5,70,'2026-02-05 17:08:06.743774','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:17.621691',604,NULL,71,'2026-02-05 17:08:17.621691','카스테라','봉지');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:17.622267',605,205,71,'2026-02-05 17:08:17.622267','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:08:17.622709',606,63,71,'2026-02-05 17:08:17.622709','우유','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:08:17.623212',607,1,71,'2026-02-05 17:08:17.623212','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:08:17.623747',608,59,71,'2026-02-05 17:08:17.623747','버터','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:17.624189',609,3,71,'2026-02-05 17:08:17.624189','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:26.795787',610,NULL,72,'2026-02-05 17:08:26.795787','스팸','캔');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:26.796695',611,53,72,'2026-02-05 17:08:26.796695','밥','공기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:26.797242',612,21,72,'2026-02-05 17:08:26.797242','오이','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 17:08:26.797685',613,128,72,'2026-02-05 17:08:26.797685','깻잎','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 17:08:26.798080',614,205,72,'2026-02-05 17:08:26.798080','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:26.798469',615,NULL,72,'2026-02-05 17:08:26.798469','김밥용 김','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:26.798810',616,71,72,'2026-02-05 17:08:26.798810','식초','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:26.799188',617,5,72,'2026-02-05 17:08:26.799188','참기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:26.799532',618,165,72,'2026-02-05 17:08:26.799532','깨소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:26.799865',619,1,72,'2026-02-05 17:08:26.799865','소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:44.182331',620,21,73,'2026-02-05 17:08:44.182331','오이','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:44.183389',621,1,73,'2026-02-05 17:08:44.183389','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:44.184020',622,NULL,73,'2026-02-05 17:08:44.184020','현미밥','공기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:44.184449',623,NULL,73,'2026-02-05 17:08:44.184449','트러플 솔트','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:08:44.184835',624,NULL,73,'2026-02-05 17:08:44.184835','트러플 페스토','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:44.185258',625,NULL,73,'2026-02-05 17:08:44.185258','트러플 오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:08:44.185680',626,2,73,'2026-02-05 17:08:44.185680','후추','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:08:44.186177',627,112,73,'2026-02-05 17:08:44.186177','김','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:44.186516',628,NULL,73,'2026-02-05 17:08:44.186516','쯔유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:08:44.186818',629,NULL,73,'2026-02-05 17:08:44.186818','유자 폰즈','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:09:36.073732',630,NULL,74,'2026-02-05 17:09:36.073732','파스타면','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:09:36.074201',631,NULL,74,'2026-02-05 17:09:36.074201','다진 마늘','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:09:36.074479',632,67,74,'2026-02-05 17:09:36.074479','페페론치노','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:09:36.074760',633,58,74,'2026-02-05 17:09:36.074760','올리브오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 17:09:36.075096',634,214,74,'2026-02-05 17:09:36.075096','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:09:36.075351',635,NULL,74,'2026-02-05 17:09:36.075351','액젓','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:09:36.075594',636,NULL,74,'2026-02-05 17:09:36.075594','허브 페스토','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:09:36.075804',637,NULL,74,'2026-02-05 17:09:36.075804','사워크림','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (30,'2026-02-05 17:10:15.965015',638,68,75,'2026-02-05 17:10:15.965015','파슬리','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:10:15.965668',639,101,75,'2026-02-05 17:10:15.965668','바질','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:10:15.966801',640,248,75,'2026-02-05 17:10:15.966801','잣','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:10:15.967228',641,10,75,'2026-02-05 17:10:15.967228','마늘','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:10:15.967807',642,NULL,75,'2026-02-05 17:10:15.967807','파마산 치즈','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:10:15.968483',643,163,75,'2026-02-05 17:10:15.968483','레몬즙','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:10:15.968871',644,NULL,75,'2026-02-05 17:10:15.968871','피쉬소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:10:15.969284',645,NULL,75,'2026-02-05 17:10:15.969284','알룰로스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:10:15.969496',646,NULL,75,'2026-02-05 17:10:15.969496','올리브유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.1,'2026-02-05 17:10:15.969833',647,1,75,'2026-02-05 17:10:15.969833','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:10:15.970282',648,171,75,'2026-02-05 17:10:15.970282','배추김치','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:10:15.970973',649,205,75,'2026-02-05 17:10:15.970973','계란','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:10:15.971436',650,NULL,75,'2026-02-05 17:10:15.971436','즉석밥','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (15,'2026-02-05 17:10:15.973231',651,59,75,'2026-02-05 17:10:15.973231','버터','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:10:15.974333',652,NULL,75,'2026-02-05 17:10:15.974333','사워크림','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 17:12:10.761056',653,NULL,77,'2026-02-05 17:12:10.761056','방울토마토 절임','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 17:12:10.762168',654,NULL,77,'2026-02-05 17:12:10.762168','모차렐라 치즈','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:12:10.762632',655,NULL,77,'2026-02-05 17:12:10.762632','파파할 멜론','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:12:10.763151',656,NULL,77,'2026-02-05 17:12:10.763151','토마토 절임 소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:12:10.763576',657,26,77,'2026-02-05 17:12:10.763576','레몬','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:12:10.764175',658,68,77,'2026-02-05 17:12:10.764175','파슬리','줄기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 17:12:10.764644',659,NULL,77,'2026-02-05 17:12:10.764644','치즈','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (600,'2026-02-05 17:13:15.883165',660,NULL,78,'2026-02-05 17:13:15.883165','수육용 삼겹살','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:13:15.883911',661,267,78,'2026-02-05 17:13:15.883911','월계수잎','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (8,'2026-02-05 17:13:15.884328',662,NULL,78,'2026-02-05 17:13:15.884328','팔각','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:13:15.884763',663,4,78,'2026-02-05 17:13:15.884763','식용유','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:13:15.885083',664,214,78,'2026-02-05 17:13:15.885083','물','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:13:15.885389',665,274,78,'2026-02-05 17:13:15.885389','진간장','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:13:15.885795',666,NULL,78,'2026-02-05 17:13:15.885795','맛술','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:13:15.886045',667,121,78,'2026-02-05 17:13:15.886045','물엿','컵');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:13:15.886325',668,88,78,'2026-02-05 17:13:15.886325','흑설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:13:15.886601',669,NULL,78,'2026-02-05 17:13:15.886601','카라멜 소스','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:13:15.886903',670,7,78,'2026-02-05 17:13:15.886903','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:13:15.887263',671,11,78,'2026-02-05 17:13:15.887263','생강','톨');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:13:15.887593',672,8,78,'2026-02-05 17:13:15.887593','대파','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (4,'2026-02-05 17:13:15.887924',673,NULL,78,'2026-02-05 17:13:15.887924','청경채','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:13:15.888349',674,1,78,'2026-02-05 17:13:15.888349','소금','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:28:03.992819',675,NULL,79,'2026-02-05 17:28:03.992819','떡볶이 떡','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:28:03.993939',676,NULL,79,'2026-02-05 17:28:03.993939','계란 흰자','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:28:03.994366',677,3,79,'2026-02-05 17:28:03.994366','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:28:03.994799',678,62,79,'2026-02-05 17:28:03.994799','생크림','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:28:03.995191',679,29,79,'2026-02-05 17:28:03.995191','고추장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (50,'2026-02-05 17:28:03.995537',680,214,79,'2026-02-05 17:28:03.995537','물','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:28:03.996138',681,128,79,'2026-02-05 17:28:03.996138','깻잎','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:28:24.317503',682,16,80,'2026-02-05 17:28:24.317503','무','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 17:28:24.320212',683,NULL,80,'2026-02-05 17:28:24.320212','가쓰오부시','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:28:24.320968',684,33,80,'2026-02-05 17:28:24.320968','국간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:28:24.321436',685,274,80,'2026-02-05 17:28:24.321436','진간장','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:28:24.321940',686,70,80,'2026-02-05 17:28:24.321940','미림','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:28:24.322590',687,3,80,'2026-02-05 17:28:24.322590','설탕','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (150,'2026-02-05 17:28:24.323455',688,232,80,'2026-02-05 17:28:24.323455','굴','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (3,'2026-02-05 17:28:24.324215',689,NULL,80,'2026-02-05 17:28:24.324215','들기름','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:28:24.324803',690,NULL,80,'2026-02-05 17:28:24.324803','묵가루','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (500,'2026-02-05 17:30:18.011183',691,NULL,81,'2026-02-05 17:30:18.011183','닭다리살 정육','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:30:18.011762',692,7,81,'2026-02-05 17:30:18.011762','양파','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:30:18.012115',693,NULL,81,'2026-02-05 17:30:18.012115','셀러리','대');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 17:30:18.012557',694,136,81,'2026-02-05 17:30:18.012557','양송이버섯','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (6,'2026-02-05 17:30:18.012782',695,10,81,'2026-02-05 17:30:18.012782','마늘','알');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (20,'2026-02-05 17:30:18.012996',696,59,81,'2026-02-05 17:30:18.012996','버터','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (100,'2026-02-05 17:30:18.013198',697,NULL,81,'2026-02-05 17:30:18.013198','화이트 와인','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 17:30:18.013506',698,NULL,81,'2026-02-05 17:30:18.013506','치킨 육수','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (300,'2026-02-05 17:30:18.013690',699,62,81,'2026-02-05 17:30:18.013690','생크림','ml');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (200,'2026-02-05 17:30:18.013874',700,NULL,81,'2026-02-05 17:30:18.013874','매쉬드 포테이토','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (30,'2026-02-05 17:30:18.014049',701,NULL,81,'2026-02-05 17:30:18.014049','명란젓','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:30:18.014294',702,26,81,'2026-02-05 17:30:18.014294','레몬','개');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:30:18.014581',703,267,81,'2026-02-05 17:30:18.014581','월계수잎','장');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:30:18.014765',704,NULL,81,'2026-02-05 17:30:18.014765','로즈마리','줄기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (1,'2026-02-05 17:30:18.014917',705,NULL,81,'2026-02-05 17:30:18.014917','타임','줄기');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (2,'2026-02-05 17:30:18.015064',706,NULL,81,'2026-02-05 17:30:18.015064','올리브 오일','큰술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:30:18.015388',707,1,81,'2026-02-05 17:30:18.015388','소금','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (0.5,'2026-02-05 17:30:18.015641',708,2,81,'2026-02-05 17:30:18.015641','후추','작은술');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (5,'2026-02-05 17:30:18.016172',709,NULL,81,'2026-02-05 17:30:18.016172','이태리 파슬리','g');
INSERT IGNORE INTO `recipe_ingredient` (`amount`,`created_at`,`id`,`ingredient_id`,`recipe_id`,`updated_at`,`name`,`unit`) VALUES (10,'2026-02-05 17:30:18.016400',710,NULL,81,'2026-02-05 17:30:18.016400','치즈','g');

-- =============================================
-- 큐레이션 데이터
-- =============================================
INSERT IGNORE INTO `curation` (
    `title`, `description`, 
    `day_types`, `time_types`, 
    `cuisine_types`, `meal_types`, `difficulties`, 
    `keywords`, `tags`, `ingredients`, 
    `is_active`, `created_at`, `updated_at`
) VALUES
-- 1. 흑백요리사 특선 (필수 포함)
('흑백요리사에서 본 그 맛!', 'TV에서 화제가 된 셰프들의 레시피를 집에서 도전해보세요',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 '', 'MAIN', 'INTERMEDIATE',
 '흑백요리사,셰프,트리플스타,에드워드리,이연복', '홈파티,손님상', '',
 1, NOW(6), NOW(6)),

-- 2. 초간단 자취 요리
('자취생 필수! 10분 완성 레시피', '바쁜 일상 속에서도 뚝딱 만드는 간단 요리',
 'WEEKDAY,WEEKEND', 'MORNING,LUNCH,DINNER,LATE_NIGHT',
 'KOREAN', 'MAIN,SNACK', 'BEGINNER',
 '자취,초간단,간단,빠른,혼밥', '자취,초간단,한그릇', '계란,라면,참치',
 1, NOW(6), NOW(6)),

-- 3. 파스타 천국
('오늘은 파스타 어때요?', '알리오 올리오부터 크림 파스타까지, 다양한 파스타 레시피',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 'WESTERN,FUSION', 'MAIN', 'BEGINNER,INTERMEDIATE',
 '파스타,알리오올리오,크림파스타,토마토파스타,까르보나라', '파스타,면요리', '스파게티,올리브오일,마늘',
 1, NOW(6), NOW(6)),

-- 4. 건강한 다이어트 식단
('맛있게 먹으면서 건강하게!', '칼로리 걱정 없이 든든하게 즐기는 다이어트 레시피',
 'WEEKDAY,WEEKEND', 'MORNING,LUNCH,DINNER',
 'KOREAN,FUSION,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER',
 '다이어트,저칼로리,건강,샐러드,닭가슴살,포케', '다이어트,건강식,단백질', '닭가슴살,양배추,오이',
 1, NOW(6), NOW(6)),

-- 5. 이연복 셰프의 중화요리
('이연복 셰프의 비법 레시피', '집에서 만드는 정통 중화요리의 맛',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 'CHINESE,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER,INTERMEDIATE',
 '이연복,중화요리,중식,계란,두부', '중식,아시안', '계란,두부,오이',
 1, NOW(6), NOW(6)),

-- 6. 한식 밑반찬 특선
('엄마 손맛 느끼는 밑반찬', '냉장고에 있으면 든든한 한식 밑반찬 모음',
 'WEEKEND', 'LUNCH,DINNER',
 'KOREAN', 'SIDE_DISH', 'BEGINNER,INTERMEDIATE',
 '밑반찬,반찬,나물,무침,김치', '밑반찬,반찬,집밥', '고사리,양배추,오이',
 1, NOW(6), NOW(6)),

-- 7. 푸짐한 고기 요리
('육식파를 위한 고기 특선', '스테이크부터 수육까지, 육즙 가득한 고기 요리',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 'KOREAN,WESTERN', 'MAIN', 'BEGINNER,INTERMEDIATE',
 '고기,스테이크,수육,육전,삼겹살,돼지고기,소고기', '메인요리,단백질', '돼지고기,소고기',
 1, NOW(6), NOW(6)),

-- 8. 볶음밥 & 덮밥 모음
('한 그릇으로 든든하게!', '간단하지만 맛있는 볶음밥과 덮밥 레시피',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
 'KOREAN,CHINESE,FUSION', 'MAIN', 'BEGINNER',
 '볶음밥,덮밥,한그릇,간편식,게살볶음밥,계란덮밥', '한그릇,볶음,덮밥', '밥,계란,김치',
 1, NOW(6), NOW(6)),

-- 9. 달콤한 디저트 타임
('식후 달콤함을 더해줄 디저트', '집에서 만드는 특별한 디저트 레시피',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
 'FUSION,ASIAN,WESTERN', 'DESSERT,SNACK', 'BEGINNER,INTERMEDIATE',
 '디저트,달콤,프렌치토스트,쿠키,두쫀쿠', '디저트,간식', '초콜릿,버터,설탕',
 1, NOW(6), NOW(6)),

-- 10. 야식 특선
('배고픈 밤을 위한 야식 메뉴', '늦은 밤 출출할 때 딱 좋은 야식 레시피',
 'WEEKDAY,WEEKEND', 'LATE_NIGHT',
 'KOREAN', 'MAIN,SNACK', 'BEGINNER',
 '야식,라면,볶음밥,국물,닭발', '야식,한그릇,매운맛', '라면,치즈,계란',
 1, NOW(6), NOW(6)),

-- 11. 명절 음식 특선
('정성 가득 명절 요리', '설날, 추석에 만드는 전통 명절 음식',
 'WEEKEND', 'LUNCH,DINNER',
 'KOREAN', 'MAIN,SIDE_DISH', 'INTERMEDIATE',
 '명절,잡채,전,나물,김치,나박김치', '명절음식,한식,손님상', '고사리,도라지,버섯',
 1, NOW(6), NOW(6)),

-- 12. 양식 입문자를 위한 쉬운 레시피
('처음 만들어도 실패 없는 양식', '누구나 쉽게 도전할 수 있는 양식 레시피',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 'WESTERN', 'MAIN,SNACK', 'BEGINNER',
 '양식,파스타,스튜,토스트,카나페', '양식,초간단', '파스타,토마토,치즈',
 1, NOW(6), NOW(6)),

-- 13. 생선 요리 모음
('바다의 맛을 담은 생선 요리', '담백하고 건강한 생선 요리 레시피',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 'KOREAN,WESTERN', 'MAIN', 'BEGINNER,INTERMEDIATE',
 '생선,조기,가자미,생선구이,뫼니에르', '생선구이,건강식', '조기,가자미,버터',
 1, NOW(6), NOW(6)),

-- 14. 도시락 & 밀프렙
('내일 점심은 내가 싼다!', '도시락과 밀프렙에 딱 좋은 레시피 모음',
 'WEEKDAY', 'MORNING,LUNCH',
 'KOREAN,FUSION', 'MAIN,SNACK', 'BEGINNER',
 '도시락,밀프렙,밥버거,김밥,볶음밥', '도시락,간편식', '밥,참치,스팸',
 1, NOW(6), NOW(6)),

-- 15. 손님 초대 요리
('손님 오는 날 특별한 한 끼', '홈파티에 내놓아도 손색없는 요리',
 'WEEKEND', 'LUNCH,DINNER',
 'WESTERN,FUSION,KOREAN', 'MAIN', 'INTERMEDIATE',
 '홈파티,손님상,스테이크,동파육,잡채', '홈파티,손님상,메인요리', '소고기,삼겹살',
 1, NOW(6), NOW(6)),

-- 16. 매콤한 맛 특선
('스트레스 날리는 매콤한 요리', '칼칼하고 얼큰한 맛이 필요할 때',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
 'KOREAN', 'MAIN', 'BEGINNER',
 '매콤,매운,칼칼,닭발,김치,짬뽕', '매운맛,칼칼', '고춧가루,청양고추',
 1, NOW(6), NOW(6)),

-- 17. 간식 & 스낵
('출출할 때 딱! 간식 레시피', '감자튀김부터 토스트까지 맛있는 간식 모음',
 'WEEKDAY,WEEKEND', 'MORNING,LUNCH,DINNER,LATE_NIGHT',
 'WESTERN,KOREAN,FUSION', 'SNACK', 'BEGINNER',
 '간식,감자튀김,토스트,김밥,쿠키', '간식,스낵', '감자,식빵,계란',
 1, NOW(6), NOW(6)),

-- 18. 퓨전 요리 특선
('동서양의 맛을 하나로!', '창의적인 퓨전 요리 레시피',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER',
 'FUSION', 'MAIN,DESSERT', 'BEGINNER,INTERMEDIATE',
 '퓨전,명란파스타,허브볶음밥,무스테이크', '퓨전,창작요리', '명란,허브,트러플',
 1, NOW(6), NOW(6)),

-- 19. 주말 브런치
('여유로운 주말 아침', '늦잠 자고 일어나 즐기는 브런치 레시피',
 'WEEKEND', 'MORNING,LUNCH',
 'WESTERN,KOREAN', 'MAIN,SNACK', 'BEGINNER',
 '브런치,토스트,프렌치토스트,계란,샐러드', '브런치,주말요리', '계란,식빵,버터',
 1, NOW(6), NOW(6)),

-- 20. 국물 요리 특선
('따뜻한 국물이 생각날 때', '속까지 따뜻해지는 국물 요리 모음',
 'WEEKDAY,WEEKEND', 'LUNCH,DINNER,LATE_NIGHT',
 'KOREAN,WESTERN', 'MAIN', 'BEGINNER,INTERMEDIATE',
 '국물,스튜,곰탕,찌개,닭발', '국물요리,따뜻한', '무,콩나물,고기',
 1, NOW(6), NOW(6)),

-- 21. 출근 전 뚝딱 아침
('출근 전 뚝딱 아침', '바쁜 아침에도 5분이면 완성하는 간단 레시피',
 'WEEKDAY', 'MORNING',
 'KOREAN,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER',
 '아침,출근,간단,빠른,계란,스팸,두부', '아침식사,초간단', '계란,스팸,두부',
 1, NOW(6), NOW(6)),

-- 22. 가벼운 아침 채소 한 접시
('가벼운 아침 채소 한 접시', '상큼한 채소로 시작하는 건강한 아침',
 'WEEKDAY,WEEKEND', 'MORNING',
 'KOREAN', 'SIDE_DISH', 'BEGINNER',
 '채소,샐러드,나물,무침,양배추,가벼운', '아침,채소,건강', '양배추,오이,도라지',
 1, NOW(6), NOW(6)),

-- 23. 아침 단백질 충전
('아침 단백질 충전', '하루를 든든하게 시작하는 고단백 아침 메뉴',
 'WEEKDAY,WEEKEND', 'MORNING',
 'KOREAN,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER',
 '단백질,계란,두부,육전,아침,든든', '단백질,아침,든든', '계란,두부,돼지고기',
 1, NOW(6), NOW(6)),

-- 24. 점심 파스타 한 그릇
('점심 파스타 한 그릇', '점심시간에 딱 맞는 간편 파스타 모음',
 'WEEKDAY,WEEKEND', 'LUNCH',
 'WESTERN,FUSION', 'MAIN', 'BEGINNER',
 '파스타,점심,까르보나라,알리오올리오,명란,허브', '파스타,점심,면요리', '파스타,올리브오일,마늘',
 1, NOW(6), NOW(6)),

-- 25. 평일 점심 볶음밥 특선
('평일 점심 볶음밥 특선', '냉장고 재료로 뚝딱 만드는 볶음밥',
 'WEEKDAY', 'LUNCH',
 'KOREAN,FUSION', 'MAIN', 'BEGINNER',
 '볶음밥,점심,닭갈비,게살,허브,간편', '볶음밥,점심,한그릇', '밥,계란,치즈',
 1, NOW(6), NOW(6)),

-- 26. 점심 든든 한식 백반
('점심 든든 한식 백반', '집밥이 그리울 때, 정갈한 한식 한 상',
 'WEEKDAY,WEEKEND', 'LUNCH',
 'KOREAN', 'MAIN,SIDE_DISH', 'BEGINNER,INTERMEDIATE',
 '한식,백반,집밥,조기,나물,계란찜', '한식,점심,집밥', '조기,고사리,계란',
 1, NOW(6), NOW(6)),

-- 27. 아침에 좋은 따뜻한 한 그릇
('아침에 좋은 따뜻한 한 그릇', '속을 편안하게 달래주는 따뜻한 아침 메뉴',
 'WEEKDAY,WEEKEND', 'MORNING',
 'KOREAN,CHINESE', 'MAIN', 'BEGINNER,INTERMEDIATE',
 '국물,곰탕,따뜻한,아침,속편한,계란덮밥', '아침,국물,따뜻한', '돼지고기,계란,무',
 1, NOW(6), NOW(6)),

-- 28. 점심 다이어트 한 끼
('점심 다이어트 한 끼', '칼로리 걱정 없이 배부른 점심 식단',
 'WEEKDAY', 'LUNCH',
 'FUSION,ASIAN,KOREAN', 'MAIN,SIDE_DISH', 'BEGINNER',
 '다이어트,점심,저칼로리,포케,샐러드,무스테이크', '다이어트,점심,건강', '새우,양배추,무',
 1, NOW(6), NOW(6)),

-- 29. 아시안 점심 특선
('아시안 점심 특선', '색다른 아시안 풍미로 즐기는 점심',
 'WEEKDAY,WEEKEND', 'LUNCH',
 'ASIAN,JAPANESE,CHINESE', 'MAIN,SIDE_DISH', 'BEGINNER',
 '아시안,일식,중식,포케,야키소바,오이무침', '아시안,점심', '새우,계란,오이',
 1, NOW(6), NOW(6)),

-- 30. 주중 간편 김밥 & 밥버거
('주중 간편 김밥 & 밥버거', '손에 들고 먹기 좋은 한 끼 메뉴',
 'WEEKDAY', 'MORNING,LUNCH',
 'KOREAN', 'MAIN,SNACK', 'BEGINNER',
 '김밥,밥버거,무스비,간편,한손,출근', '간편식,도시락,한손', '밥,스팸,참치',
 1, NOW(6), NOW(6)),

-- 31. 퇴근 후 혼밥 저녁
('퇴근 후 혼밥 저녁', '지친 하루 끝, 나를 위한 간단 저녁 한 끼',
 'WEEKDAY', 'DINNER',
 'KOREAN,CHINESE', 'MAIN', 'BEGINNER',
 '혼밥,퇴근,저녁,간단,계란덮밥,김치말이국수', '혼밥,저녁,간편식', '계란,김치,라면',
 1, NOW(6), NOW(6)),

-- 32. 저녁 스테이크 & 양식 코스
('저녁 스테이크 & 양식 코스', '특별한 저녁을 위한 근사한 양식 요리',
 'WEEKEND', 'DINNER',
 'WESTERN,FUSION', 'MAIN', 'INTERMEDIATE',
 '스테이크,양식,뫼니에르,스튜,프리카세', '양식,특별한저녁', '소고기,버터,올리브오일',
 1, NOW(6), NOW(6)),

-- 33. 밤에 즐기는 매운맛 도전
('밤에 즐기는 매운맛 도전', '스트레스는 매운맛으로 풀자',
 'WEEKDAY,WEEKEND', 'LATE_NIGHT',
 'KOREAN', 'MAIN', 'BEGINNER',
 '매운,매콤,닭발,짬뽕,김치,만두', '매운맛,늦은밤', '고춧가루,청양고추,김치',
 1, NOW(6), NOW(6)),

-- 34. 저녁 찜 & 수육 한 상
('저녁 찜 & 수육 한 상', '푸짐한 찜요리와 수육으로 차리는 저녁',
 'WEEKEND', 'DINNER',
 'KOREAN,CHINESE', 'MAIN', 'BEGINNER,INTERMEDIATE',
 '찜,수육,대파수육,동파육,알배추찜', '찜요리,수육,푸짐한', '돼지고기,대파,배추',
 1, NOW(6), NOW(6)),

-- 35. 늦은 밤 한 입 간식
('늦은 밤 한 입 간식', '출출한 밤, 간단히 손으로 집어 먹는 한 입 메뉴',
 'WEEKDAY,WEEKEND', 'LATE_NIGHT',
 'KOREAN,WESTERN', 'SNACK', 'BEGINNER',
 '간식,한입,무스비,김밥,감자튀김,카나페', '간식,한입,늦은밤', '스팸,감자,오이',
 1, NOW(6), NOW(6)),

-- 36. 저녁 건강식 한 끼
('저녁 건강식 한 끼', '가볍지만 든든한 저녁 다이어트 메뉴',
 'WEEKDAY', 'DINNER',
 'FUSION,ASIAN,KOREAN', 'MAIN,SIDE_DISH', 'BEGINNER',
 '건강,다이어트,저녁,포케,샐러드,두부', '건강식,저녁,가벼운', '새우,두부,양배추',
 1, NOW(6), NOW(6)),

-- 37. 주말 저녁 홈파티
('주말 저녁 홈파티', '집에서 즐기는 근사한 홈파티 메뉴',
 'WEEKEND', 'DINNER',
 'WESTERN,FUSION', 'MAIN,SNACK', 'BEGINNER,INTERMEDIATE',
 '홈파티,카나페,스테이크,카프레제,파티', '홈파티,주말,특별한', '소고기,새우,토마토',
 1, NOW(6), NOW(6)),

-- 38. 늦은 밤 간편 면요리
('늦은 밤 간편 면요리', '배고픈 밤, 후루룩 면 한 그릇',
 'WEEKDAY,WEEKEND', 'LATE_NIGHT',
 'KOREAN,WESTERN', 'MAIN', 'BEGINNER',
 '면요리,라면,파스타,국수,짬뽕,까르보나라', '면요리,늦은밤,한그릇', '라면,파스타,김치',
 1, NOW(6), NOW(6)),

-- 39. 저녁 중화요리 한 상
('저녁 중화요리 한 상', '집에서 만드는 중화풍 저녁 식탁',
 'WEEKDAY,WEEKEND', 'DINNER',
 'CHINESE,ASIAN', 'MAIN,SIDE_DISH', 'BEGINNER,INTERMEDIATE',
 '중화요리,동파육,계란덮밥,오이무침,중식', '중식,저녁', '계란,두부,오이',
 1, NOW(6), NOW(6)),

-- 40. 늦은 밤 감성 안주
('늦은 밤 감성 안주', '맥주 한 잔과 함께하는 안주 모음',
 'WEEKDAY,WEEKEND', 'LATE_NIGHT',
 'KOREAN,WESTERN', 'MAIN,SNACK,SIDE_FOR_DRINK', 'BEGINNER,INTERMEDIATE',
 '안주,맥주,감자튀김,육전,닭발,토스트', '안주,늦은밤,감성', '감자,돼지고기,계란',
 1, NOW(6), NOW(6));


-- 레시피북: 기본
INSERT IGNORE INTO recipe_book (id, member_id, title, is_default, sort_order, created_at, updated_at)
VALUES (1, 1, '내 레시피북', true, 1, NOW(6), NOW(6));

-- 레시피북: 다이어트 식단
INSERT IGNORE INTO recipe_book (id, member_id, title, is_default, sort_order, created_at, updated_at)
VALUES (2, 1, '다이어트 식단', false, 2, NOW(6), NOW(6));

-- 레시피북: 반찬거리
INSERT IGNORE INTO recipe_book (id, member_id, title, is_default, sort_order, created_at, updated_at)
VALUES (3, 1, '반찬거리', false, 3, NOW(6), NOW(6));

-- 다이어트 식단 레시피 (book_id=2)
INSERT IGNORE INTO recipe_book_item (id, book_id, recipe_id, created_at, updated_at) VALUES
(1, 2, 46, NOW(6), NOW(6)),   -- 두부 계란 볶음
(2, 2, 32, NOW(6), NOW(6)),   -- 흑임자 양배추 샐러드
(3, 2, 75, NOW(6), NOW(6)),   -- 허브 볶음밥
(4, 2, 57, NOW(6), NOW(6)),   -- 다이어트 두쫀쿠
(5, 2, 80, NOW(6), NOW(6)),   -- 무 스테이크
(6, 2, 77, NOW(6), NOW(6)),   -- 토마토 절임 카프레제
(7, 2, 69, NOW(6), NOW(6)),   -- 알배추찜
(8, 2, 62, NOW(6), NOW(6)),   -- 샐러드 파스타
(9, 2, 64, NOW(6), NOW(6)),   -- 닭가슴살 감자 쿠키
(10, 2, 58, NOW(6), NOW(6));  -- 데리야끼 새우 포케

-- 반찬거리 레시피 (book_id=3)
INSERT IGNORE INTO recipe_book_item (id, book_id, recipe_id, created_at, updated_at) VALUES
(11, 3, 48, NOW(6), NOW(6)), -- 계란덮밥
(12, 3, 34, NOW(6), NOW(6)), -- 돼지고기 육전
(13, 3, 47, NOW(6), NOW(6)), -- 중식 오이무침
(14, 3, 39, NOW(6), NOW(6)), -- 버섯잡채
(15, 3, 38, NOW(6), NOW(6)), -- 고추간장
(16, 3, 36, NOW(6), NOW(6)), -- 양배추 나물
(17, 3, 37, NOW(6), NOW(6)), -- 도라지 오이무침
(18, 3, 31, NOW(6), NOW(6)), -- 고사리나물
(19, 3, 35, NOW(6), NOW(6)), -- 나박김치
(20, 3, 51, NOW(6), NOW(6)); -- 계란찜

-- 레시피북: 술안주
INSERT IGNORE INTO recipe_book (id, member_id, title, is_default, sort_order, created_at, updated_at)
VALUES (4, 1, '술안주', false, 4, NOW(6), NOW(6));

-- 술안주 레시피 (book_id=4)
INSERT IGNORE INTO recipe_book_item (id, book_id, recipe_id, created_at, updated_at) VALUES
(21, 4, 42, NOW(6), NOW(6)), -- 크나페
(22, 4, 77, NOW(6), NOW(6)), -- 토마토 절임 카프레제
(23, 4, 70, NOW(6), NOW(6)), -- 무수분 대파수육
(24, 4, 52, NOW(6), NOW(6)), -- 쉬림프 카나페
(25, 4, 50, NOW(6), NOW(6)), -- 국물 닭발
(26, 4, 47, NOW(6), NOW(6)), -- 중식 오이무침
(27, 4, 40, NOW(6), NOW(6)), -- 김치왕만두
(28, 4, 34, NOW(6), NOW(6)), -- 돼지고기 육전
(29, 4, 44, NOW(6), NOW(6)), -- 감자튀김
(30, 4, 78, NOW(6), NOW(6)); -- 동파육