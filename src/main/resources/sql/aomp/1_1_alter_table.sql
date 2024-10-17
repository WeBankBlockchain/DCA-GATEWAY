alter table app_info add column capacity INT(20) NOT NULL DEFAULT '0';
alter table compute_task add column fps_hash varchar(1024) NOT NULL DEFAULT '';
alter table compute_task add index app_id(app_id);