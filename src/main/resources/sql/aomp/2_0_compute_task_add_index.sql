alter table compute_task add unique index file_id(file_id(128));
alter table compute_task add index app_file_id(app_id,file_id(128));
