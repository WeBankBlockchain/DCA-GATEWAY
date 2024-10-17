CREATE TABLE `wm_app_info` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`app_id` VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'app id'  ,
	`app_name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '应用名称'  ,
	`description` TEXT NOT NULL COMMENT '描述'  ,
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `index_app_id` (`app_id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wm_compute_task` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`app_id` VARCHAR(128) NULL DEFAULT NULL ,
    `unique_id` VARCHAR(128) NOT NULL comment '唯一ID',
    `watermark_file_hash` VARCHAR(128) NOT NULL comment '水印文件hash',
    `algorithm_type` int(4) NULL DEFAULT 0 comment '算法类型,0-vgg',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `app_id_unique_id_algorithm_type` (`app_id`,`unique_id`,`algorithm_type`),
	UNIQUE INDEX `app_id_watermark_file_hash` (`app_id`,`watermark_file_hash`)

)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wm_file_vector_0` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`app_id` VARCHAR(128) NULL DEFAULT NULL  ,
	`unique_id` VARCHAR(128) NOT NULL comment '唯一ID',
    `unique_hash` VARCHAR(128) NOT NULL comment 'appId@uniqueId的哈希',
	`feature` LONGTEXT NULL DEFAULT NULL comment '特征向量',
	`algorithm_type` int(4) NULL DEFAULT 0 comment '算法类型,0-vgg',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `app_id_unique_id_algorithm_type` (`app_id`,`unique_id`,`algorithm_type`),
    UNIQUE INDEX `unique_hash_algorithm_type` (`unique_hash`,`algorithm_type`),
    KEY `unique_hash` (`unique_hash`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wm_file_vector_1` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`app_id` VARCHAR(128) NULL DEFAULT NULL  ,
	`unique_id` VARCHAR(128) NOT NULL comment '唯一ID',
    `unique_hash` VARCHAR(128) NOT NULL comment 'appId@uniqueId的哈希',
	`feature` LONGTEXT NULL DEFAULT NULL COMMENT '特征向量',
	`algorithm_type` int(4) NULL DEFAULT 0 COMMENT '算法类型,0-vgg',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `app_id_unique_id_algorithm_type` (`app_id`,`unique_id`,`algorithm_type`),
    UNIQUE INDEX `unique_hash_algorithm_type` (`unique_hash`,`algorithm_type`),
    KEY `unique_hash` (`unique_hash`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wm_file_vector_2` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`app_id` VARCHAR(128) NULL DEFAULT NULL  ,
	`unique_id` VARCHAR(128) NOT NULL comment '唯一ID',
    `unique_hash` VARCHAR(128) NOT NULL comment 'appId@uniqueId的哈希',
	`feature` LONGTEXT NULL DEFAULT NULL COMMENT '特征向量',
	`algorithm_type` int(4) NULL DEFAULT 0 COMMENT '算法类型,0-vgg',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `app_id_unique_id_algorithm_type` (`app_id`,`unique_id`,`algorithm_type`),
    UNIQUE INDEX `unique_hash_algorithm_type` (`unique_hash`,`algorithm_type`),
    KEY `unique_hash` (`unique_hash`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wm_file_vector_3` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`app_id` VARCHAR(128) NULL DEFAULT NULL ,
	`unique_id` VARCHAR(128) NOT NULL comment '唯一ID',
    `unique_hash` VARCHAR(128) NOT NULL comment 'appId@uniqueId的哈希',
	`feature` LONGTEXT NULL DEFAULT NULL  COMMENT '特征向量',
	`algorithm_type` int(4) NULL DEFAULT 0 COMMENT '算法类型,0-vgg',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `app_id_unique_id_algorithm_type` (`app_id`,`unique_id`,`algorithm_type`),
    UNIQUE INDEX `unique_hash_algorithm_type` (`unique_hash`,`algorithm_type`),
    KEY `unique_hash` (`unique_hash`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
