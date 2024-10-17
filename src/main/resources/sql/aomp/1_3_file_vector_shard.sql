CREATE TABLE file_vector_0(
                               pk_id BIGINT(20) NOT NULL AUTO_INCREMENT,
                               file_id VARCHAR(256) NOT NULL,
                               feature LONGTEXT,
                           ALGORITHM VARCHAR(32) DEFAULT NULL,
                               file_hash VARCHAR(256) NOT NULL,
                               app_id VARCHAR(128) DEFAULT NULL,
                               collection_id VARCHAR(128) DEFAULT NULL,
                               create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                               update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON
                           UPDATE
                               CURRENT_TIMESTAMP COMMENT '更新时间' ,
                           PRIMARY KEY (pk_id),
                           UNIQUE KEY file_id (file_id(128)),
                           UNIQUE KEY file_hash (file_hash(128)),
                           KEY create_time (create_time),
                           KEY app_id (app_id)
                          )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE file_vector_1(
                               pk_id BIGINT(20) NOT NULL AUTO_INCREMENT,
                               file_id VARCHAR(256) NOT NULL,
                               feature LONGTEXT,
                           ALGORITHM VARCHAR(32) DEFAULT NULL,
                               file_hash VARCHAR(256) NOT NULL,
                               app_id VARCHAR(128) DEFAULT NULL,
                               collection_id VARCHAR(128) DEFAULT NULL,
                               create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                               update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON
                           UPDATE
                               CURRENT_TIMESTAMP COMMENT '更新时间' ,
                           PRIMARY KEY (pk_id),
                           UNIQUE KEY file_id (file_id(128)),
                           UNIQUE KEY file_hash (file_hash(128)),
                           KEY create_time (create_time),
                           KEY app_id (app_id)
                          )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE file_vector_2(
                               pk_id BIGINT(20) NOT NULL AUTO_INCREMENT,
                               file_id VARCHAR(256) NOT NULL,
                               feature LONGTEXT,
                           ALGORITHM VARCHAR(32) DEFAULT NULL,
                               file_hash VARCHAR(256) NOT NULL,
                               app_id VARCHAR(128) DEFAULT NULL,
                               collection_id VARCHAR(128) DEFAULT NULL,
                               create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                               update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON
                           UPDATE
                               CURRENT_TIMESTAMP COMMENT '更新时间' ,
                           PRIMARY KEY (pk_id),
                           UNIQUE KEY file_id (file_id(128)),
                           UNIQUE KEY file_hash (file_hash(128)),
                           KEY create_time (create_time),
                           KEY app_id (app_id)
                          )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE file_vector_3(
                               pk_id BIGINT(20) NOT NULL AUTO_INCREMENT,
                               file_id VARCHAR(256) NOT NULL,
                               feature LONGTEXT,
                           ALGORITHM VARCHAR(32) DEFAULT NULL,
                               file_hash VARCHAR(256) NOT NULL,
                               app_id VARCHAR(128) DEFAULT NULL,
                               collection_id VARCHAR(128) DEFAULT NULL,
                               create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                               update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON
                           UPDATE
                               CURRENT_TIMESTAMP COMMENT '更新时间' ,
                           PRIMARY KEY (pk_id),
                           UNIQUE KEY file_id (file_id(128)),
                           UNIQUE KEY file_hash (file_hash(128)),
                           KEY create_time (create_time),
                           KEY app_id (app_id)
                          )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;