alter table location
add COLUMN if not EXISTS active boolean default true;

alter table events
add column if not EXISTS location_name varchar(250) not null;

alter table events
add column if not exists start_time varchar(10)[] not NULL,
add column if not exists duration varchar(10) not NULL;