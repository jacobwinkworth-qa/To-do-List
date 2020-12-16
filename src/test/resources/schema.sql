drop table if exists `task` CASCADE;
drop table if exists `td_list` CASCADE;

create table td_list (id BIGINT PRIMARY KEY AUTO_INCREMENT, topic VARCHAR(255) NOT NULL);
create table task (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, td_list_id BIGINT);