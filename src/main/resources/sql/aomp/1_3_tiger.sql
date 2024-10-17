INSERT INTO app_info
(app_id, app_name, description, capacity)
VALUES('DG', 'tiger', 'tiger', 202203);

INSERT INTO collection_info
( collection_id, app_id, collection_name, description)
VALUES( 'Tiger', 'DG', 'tiger', 'tiger');

INSERT INTO file_vector_0(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=0 and c.file_id = f.file_id
and c.pk_id >= 0 and c.pk_id <= 1000;

INSERT INTO file_vector_0(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=0 and c.file_id = f.file_id
and c.pk_id >1000 and c.pk_id <= 2000;

INSERT INTO file_vector_0(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=0 and c.file_id = f.file_id
and c.pk_id >2000 and c.pk_id <= 3000;

INSERT INTO file_vector_0(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=0 and c.file_id = f.file_id
and c.pk_id >3000;

INSERT INTO file_vector_1(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=1 and c.file_id = f.file_id
and c.pk_id >= 0 and c.pk_id <= 1000;

INSERT INTO file_vector_1(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=1 and c.file_id = f.file_id
and c.pk_id >1000 and c.pk_id <= 2000;

INSERT INTO file_vector_1(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=1 and c.file_id = f.file_id
and c.pk_id >2000 and c.pk_id <= 3000;

INSERT INTO file_vector_1(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=1 and c.file_id = f.file_id
and c.pk_id >3000;

INSERT INTO file_vector_2(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=2 and c.file_id = f.file_id
and c.pk_id >= 0 and c.pk_id <= 1000;

INSERT INTO file_vector_2(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=2 and c.file_id = f.file_id
and c.pk_id >1000 and c.pk_id <= 2000;

INSERT INTO file_vector_2(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=2 and c.file_id = f.file_id
and c.pk_id >2000 and c.pk_id <= 3000;

INSERT INTO file_vector_2(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=2 and c.file_id = f.file_id
and c.pk_id >3000;

INSERT INTO file_vector_3(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=3 and c.file_id = f.file_id
and c.pk_id >= 0 and c.pk_id <= 1000;

INSERT INTO file_vector_3(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=3 and c.file_id = f.file_id
and c.pk_id >1000 and c.pk_id <= 2000;

INSERT INTO file_vector_3(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=3 and c.file_id = f.file_id
and c.pk_id >2000 and c.pk_id <= 3000;

INSERT INTO file_vector_3(file_id, feature, algorithm, file_hash, app_id, collection_id, create_time, update_time)
select f.file_id, f.feature , f.algorithm ,f.file_hash ,f.app_id ,f.collection_id ,f.create_time ,f.update_time  from file_vector f, compute_task c where c.pk_id%4=3 and c.file_id = f.file_id
and c.pk_id >3000;

