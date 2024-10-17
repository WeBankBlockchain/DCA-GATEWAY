
create table app_info
(
    pk_id bigint auto_increment comment '主键ID',
    app_id varchar(128) default '' not null comment 'app id',
    app_name varchar(128) default '' not null comment '应用名称',
    description text not null comment '描述',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (`pk_id`),
    unique `index_app_id` (`app_id`)
)
    DEFAULT CHARSET=utf8mb4
    COLLATE='utf8mb4_unicode_ci'
    ENGINE=InnoDB
;


create table collection_info
(
    pk_id bigint auto_increment comment '主键ID',
    collection_id varchar(128) default '' not null comment 'collection id',
    app_id varchar(128) default '' not null comment 'app id',
    collection_name varchar(128) default '' not null comment 'collection名称',
    description text not null comment '描述',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (`pk_id`),
    unique index `index_collection_id` (`app_id`, `collection_id`)
)
    DEFAULT CHARSET=utf8mb4
    COLLATE='utf8mb4_unicode_ci'
    ENGINE=InnoDB
;



create table threshold_info
(
    pk_id bigint auto_increment comment '主键ID',
    busi_name varchar(128) default '' not null comment 'threshold id',
    threshold varchar(64) default '' not null comment 'threshold value',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (`pk_id`),
    unique `index_busi_name` (`busi_name`)
)
    DEFAULT CHARSET=utf8mb4
    COLLATE='utf8mb4_unicode_ci'
    ENGINE=InnoDB
;



create table sys_config
(
    pk_id bigint auto_increment comment '主键ID',
    config_name varchar(128) default '' not null comment '配置名',
    config_value varchar(128) default '' not null comment '配置值',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (`pk_id`),
    unique `index_config_name` (`config_name`)
)
    DEFAULT CHARSET=utf8mb4
    COLLATE='utf8mb4_unicode_ci'
    ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `file_vector`(
`pk_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
`file_id` VARCHAR(256) NOT NULL,
`feature` longtext,
`algorithm`  VARCHAR(32) DEFAULT NULL,
`file_hash` varchar(256) NOT NULL,
`app_id` varchar(128) DEFAULT NULL,
`collection_id` varchar(128) DEFAULT NULL,
`create_time` timestamp not null default CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`pk_id`),
UNIQUE KEY `file_id` (`file_id`(128)),
UNIQUE KEY `file_hash` (`file_hash`(128)),
KEY `create_time` (`create_time`),
KEY `app_id` (`app_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `compute_task` (
  `pk_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seq_No` varchar(128) NOT NULL,
  `file_id` varchar(256) NOT NULL,
  `file_hash` varchar(256) NOT NULL,
  `file_type` smallint(6) DEFAULT '0',
  `fps_id` varchar(1024) DEFAULT NULL,
  `app_id` varchar(128) DEFAULT NULL,
  `collection_id` varchar(128) DEFAULT NULL,
  `create_time` timestamp not null default CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pk_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;