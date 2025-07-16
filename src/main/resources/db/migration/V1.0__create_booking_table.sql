create table if NOT EXISTS booking( 
    id serial4 PRIMARY KEY,
    room_id int4 not null,
    room_name varchar(250) not null,
    room_type varchar(10) not null,
    created_at TIMESTAMPtz not null,
    created_by VARCHAR(250) not null,
    created_by_id UUID not null,
    last_updated_at TIMESTAMPtz not null,
    last_updated_by VARCHAR(250) not null,
    last_updated_by_id UUID not null
);