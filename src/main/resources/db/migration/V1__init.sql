-- =============================================
-- Flyway V1 - Initial DDL
-- =============================================

-- --------------------------------------------
-- file_metadata
-- --------------------------------------------
CREATE TABLE file_metadata (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    object_key      VARCHAR(100)    NOT NULL,
    original_name   VARCHAR(255)    NOT NULL,
    extension       VARCHAR(255)    NOT NULL,
    size            BIGINT          NOT NULL,
    target_type     VARCHAR(255),
    target_id       BIGINT,
    uploader_type   VARCHAR(255),
    uploader_id     BIGINT,
    upload_status   VARCHAR(255)    NOT NULL,
    visibility      VARCHAR(255)    NOT NULL,
    public_url      TEXT,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- member
-- --------------------------------------------
CREATE TABLE member (
    id                    BIGINT          NOT NULL AUTO_INCREMENT,
    email                 VARCHAR(255)    NOT NULL,
    name                  VARCHAR(255)    NOT NULL,
    oauth_id              VARCHAR(255),
    oauth_provider        VARCHAR(255),
    role                  VARCHAR(255)    NOT NULL,
    profile_img_file_id   BIGINT,
    created_at            DATETIME(6),
    updated_at            DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_member_email UNIQUE (email),
    CONSTRAINT fk_member_profile_img FOREIGN KEY (profile_img_file_id) REFERENCES file_metadata (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- member_group
-- --------------------------------------------
CREATE TABLE member_group (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    name                VARCHAR(255)    NOT NULL,
    description         TEXT,
    thumbnail_img_url   VARCHAR(255),
    group_type          VARCHAR(255),
    created_at          DATETIME(6),
    updated_at          DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- group_member
-- --------------------------------------------
CREATE TABLE group_member (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    role        VARCHAR(255)    NOT NULL,
    member_id   BIGINT          NOT NULL,
    group_id    BIGINT          NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_group_member UNIQUE (member_id, group_id),
    CONSTRAINT fk_group_member_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_group_member_group FOREIGN KEY (group_id) REFERENCES member_group (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- invite_link
-- --------------------------------------------
CREATE TABLE invite_link (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    code        VARCHAR(10)     NOT NULL,
    expires_at  DATETIME(6)     NOT NULL,
    group_id    BIGINT          NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_invite_link_code UNIQUE (code),
    CONSTRAINT fk_invite_link_group FOREIGN KEY (group_id) REFERENCES member_group (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- ingredient
-- --------------------------------------------
CREATE TABLE ingredient (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)     NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_ingredient_name UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- tag
-- --------------------------------------------
CREATE TABLE tag (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)    NOT NULL,
    source_type VARCHAR(255)    NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_tag_name UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- source_content_creator
-- --------------------------------------------
CREATE TABLE source_content_creator (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    external_key    VARCHAR(100)    NOT NULL,
    platform        VARCHAR(30)     NOT NULL,
    display_name    VARCHAR(100)    NOT NULL,
    profile_img_url VARCHAR(500),
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_source_creator_platform_key UNIQUE (platform, external_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- source_content
-- --------------------------------------------
CREATE TABLE source_content (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    source_author_id    BIGINT          NOT NULL,
    external_key        VARCHAR(100)    NOT NULL,
    platform            VARCHAR(30)     NOT NULL,
    title               VARCHAR(255)    NOT NULL,
    canonical_url       VARCHAR(500)    NOT NULL,
    thumbnail_url       VARCHAR(500),
    content_type        VARCHAR(20)     NOT NULL,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          DATETIME(6),
    updated_at          DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_source_content_canonical_url UNIQUE (canonical_url),
    CONSTRAINT uk_source_content_thumbnail_url UNIQUE (thumbnail_url),
    CONSTRAINT uk_source_content_platform_key UNIQUE (platform, external_key),
    CONSTRAINT fk_source_content_creator FOREIGN KEY (source_author_id) REFERENCES source_content_creator (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe
-- --------------------------------------------
CREATE TABLE recipe (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    member_id           BIGINT          NOT NULL,
    source_content_id   BIGINT,
    title               VARCHAR(100)    NOT NULL,
    description         VARCHAR(500),
    serving_size        INT             NOT NULL,
    cooking_time        INT             NOT NULL,
    cuisine_type        VARCHAR(50)     NOT NULL,
    meal_type           VARCHAR(50)     NOT NULL,
    difficulty          VARCHAR(50)     NOT NULL,
    source_type         VARCHAR(50),
    bookmark_count      INT             NOT NULL DEFAULT 0,
    main_img_file_id    BIGINT,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          DATETIME(6),
    updated_at          DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_recipe_source_content UNIQUE (source_content_id),
    CONSTRAINT fk_recipe_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_recipe_source_content FOREIGN KEY (source_content_id) REFERENCES source_content (id),
    CONSTRAINT fk_recipe_main_img FOREIGN KEY (main_img_file_id) REFERENCES file_metadata (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_ingredient
-- --------------------------------------------
CREATE TABLE recipe_ingredient (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    ingredient_id   BIGINT,
    recipe_id       BIGINT          NOT NULL,
    name            VARCHAR(50)     NOT NULL,
    amount          DOUBLE,
    unit            VARCHAR(50)     NOT NULL,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id),
    CONSTRAINT fk_recipe_ingredient_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_step
-- --------------------------------------------
CREATE TABLE recipe_step (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    recipe_id   BIGINT,
    step_order  INT,
    description VARCHAR(200),
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_step_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_tag
-- --------------------------------------------
CREATE TABLE recipe_tag (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    recipe_id   BIGINT      NOT NULL,
    tag_id      BIGINT      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_recipe_tag UNIQUE (recipe_id, tag_id),
    CONSTRAINT fk_recipe_tag_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id),
    CONSTRAINT fk_recipe_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_book
-- --------------------------------------------
CREATE TABLE recipe_book (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    member_id   BIGINT,
    group_id    BIGINT,
    title       VARCHAR(200)    NOT NULL,
    is_default  BOOLEAN         NOT NULL,
    sort_order  INT             NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_book_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_recipe_book_group FOREIGN KEY (group_id) REFERENCES member_group (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_book_item
-- --------------------------------------------
CREATE TABLE recipe_book_item (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    book_id     BIGINT,
    recipe_id   BIGINT,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_recipe_book_item UNIQUE (book_id, recipe_id),
    CONSTRAINT fk_recipe_book_item_book FOREIGN KEY (book_id) REFERENCES recipe_book (id),
    CONSTRAINT fk_recipe_book_item_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- shopping_list
-- --------------------------------------------
CREATE TABLE shopping_list (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    name            VARCHAR(255)    NOT NULL,
    ingredient_id   BIGINT,
    group_id        BIGINT          NOT NULL,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_shopping_list_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id),
    CONSTRAINT fk_shopping_list_group FOREIGN KEY (group_id) REFERENCES member_group (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- feed
-- --------------------------------------------
CREATE TABLE feed (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    content     TEXT,
    feed_type   VARCHAR(255)    NOT NULL,
    likes       BIGINT          DEFAULT 0,
    group_id    BIGINT          NOT NULL,
    member_id   BIGINT          NOT NULL,
    recipe_id   BIGINT,
    image_id    BIGINT,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_feed_group FOREIGN KEY (group_id) REFERENCES member_group (id),
    CONSTRAINT fk_feed_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_feed_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id),
    CONSTRAINT fk_feed_image FOREIGN KEY (image_id) REFERENCES file_metadata (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- feed_like
-- --------------------------------------------
CREATE TABLE feed_like (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    feed_id     BIGINT      NOT NULL,
    member_id   BIGINT      NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_feed_like UNIQUE (feed_id, member_id),
    CONSTRAINT fk_feed_like_feed FOREIGN KEY (feed_id) REFERENCES feed (id),
    CONSTRAINT fk_feed_like_member FOREIGN KEY (member_id) REFERENCES member (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_calendar
-- --------------------------------------------
CREATE TABLE recipe_calendar (
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    recipe_id       BIGINT      NOT NULL,
    member_id       BIGINT,
    group_id        BIGINT,
    scheduled_date  DATE        NOT NULL,
    sort_order      INT         NOT NULL,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_calendar_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id),
    CONSTRAINT fk_recipe_calendar_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_recipe_calendar_group FOREIGN KEY (group_id) REFERENCES member_group (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- recipe_queue
-- --------------------------------------------
CREATE TABLE recipe_queue (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    recipe_id   BIGINT      NOT NULL,
    member_id   BIGINT      NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_queue_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id),
    CONSTRAINT fk_recipe_queue_member FOREIGN KEY (member_id) REFERENCES member (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- curation
-- --------------------------------------------
CREATE TABLE curation (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    title           VARCHAR(100)    NOT NULL,
    description     VARCHAR(500),
    day_types       VARCHAR(500)    NOT NULL,
    time_types      VARCHAR(500)    NOT NULL,
    cuisine_types   VARCHAR(1000),
    meal_types      VARCHAR(1000),
    difficulties    VARCHAR(1000),
    keywords        VARCHAR(2000),
    tags            VARCHAR(2000),
    ingredients     VARCHAR(2000),
    is_active       BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_curation_title UNIQUE (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- source_import_history
-- --------------------------------------------
CREATE TABLE source_import_history (
    id                      BIGINT          NOT NULL AUTO_INCREMENT,
    member_id               BIGINT          NOT NULL,
    source_content_id       BIGINT          NOT NULL,
    recipe_id               BIGINT,
    requested_source_url    TEXT            NOT NULL,
    platform                VARCHAR(30)     NOT NULL,
    raw_response            TEXT,
    parsed_content          TEXT,
    error_message           TEXT,
    status                  VARCHAR(20)     NOT NULL,
    created_at              DATETIME(6),
    updated_at              DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
