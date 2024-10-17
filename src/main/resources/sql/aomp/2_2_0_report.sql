CREATE TABLE `wm_add_delete_record_info` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`tx_type` int(4) NULL DEFAULT 0 comment '请求类型,0-add;3-del',
	`algorithm` int(4) NULL DEFAULT 0 comment '算法类型',
	`seq_no` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '应用名称'  ,
	`app_id` VARCHAR(128) NULL DEFAULT NULL ,
    `unique_id` VARCHAR(128) NOT NULL comment '唯一ID',
	`response_code` VARCHAR(32) NOT NULL COMMENT '返回状态码'  ,
	`use_time` int(32) NOT NULL COMMENT '耗时统计'  ,
    `start_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	KEY `index_app_id_tx_type_response_code` (`app_id`,`tx_type`,`response_code`),
	KEY `index_seq_no` (`seq_no`),
	KEY `index_unique_id` (`unique_id`),
    KEY `index_use_time` (`use_time`),
    KEY `index_start_time` (`start_time`),
    KEY `index_end_time` (`end_time`),
	KEY `index_create_time` (`create_time`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `wm_auth_record_info` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `seq_no` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '应用名称'  ,
	`app_id` VARCHAR(128) NULL DEFAULT NULL ,
	`algorithm` int(4) NOT NULL DEFAULT 0 comment '算法类型',
	`threshold` float(10,8) NOT NULL DEFAULT 0 comment '阈值设置',
	`file_size` float(10,2) NOT NULL DEFAULT 0 COMMENT '鉴真图片大小，单位KB',
	`file_type` int(4)  NOT NULL DEFAULT 0 COMMENT '文件类型'  ,
	`file_hash` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '鉴真图片hash'  ,
	`file_meta` text NULL DEFAULT NULL COMMENT '鉴真图片解析元信息'  ,
    `positive` TINYINT(1) NOT NULL comment '鉴真结果，0-失败，1-成功',
	`similarity` float(10,8)  NOT NULL DEFAULT 0 COMMENT '相似度'  ,
	`return_unique_id` VARCHAR(128) NULL DEFAULT NULL COMMENT '返回唯一id'  ,
	`fail_reason` int(4) NOT NULL DEFAULT 0 COMMENT '0-成功；1-文件不存在 2-相似度未超过阈值；3-水印提取失败；'  ,
    `response_code` VARCHAR(32) NOT NULL COMMENT '返回状态码'  ,
	`use_time` int(32) NOT NULL COMMENT '耗时统计'  ,
    `start_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
    KEY `index_seq_no` (`seq_no`),
    KEY `index_app_id_response_code_fail_reason` (`app_id`,`response_code`,`fail_reason`),
	KEY `index_similarity` (`similarity`),
	KEY `index_use_time` (`use_time`),
	KEY `index_return_unique_id` (`return_unique_id`),
    KEY `index_start_time` (`start_time`),
    KEY `index_end_time` (`end_time`),
	KEY `index_create_time` (`create_time`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wm_email_task_info` (
	`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`task_id` VARCHAR(256) NULL DEFAULT 0 comment '任务id',
	`lock_date` VARCHAR(128) NULL DEFAULT 0 comment '锁时间',
	`email_hash` VARCHAR(256) NULL DEFAULT 0 comment '邮件hash',
	`email_content` longtext NULL DEFAULT NULL COMMENT '邮件内容'  ,
	`status` int(4) NOT NULL DEFAULT 0,
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`pk_id`),
	UNIQUE INDEX `lock_date` (`lock_date`),
	KEY `lock_date_status` (`lock_date`,`status`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;