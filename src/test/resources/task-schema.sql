drop table if exists task CASCADE;
drop table if exists tdList CASCADE;

create table tdList (id BIGINT PRIMARY KEY AUTO_INCREMENT, topic VARCHAR(255) NOT NULL);
create table task (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, tdList_id BIGINT NOT NULL);