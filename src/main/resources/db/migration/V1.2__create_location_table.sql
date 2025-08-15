create table if not EXISTS location (
    id serial4 PRIMARY key,
    name varchar(250) not null,
    room_type varchar(10) not null,
    part_of int4 REFERENCES location (id),
    created_by_id uuid not null,
    created_by varchar(250) not null,
    created_at TIMESTAMPTZ not null DEFAULT now(),
    last_updated_by_id uuid not null,
    last_updated_by varchar(250) not null,
    last_updated_at TIMESTAMPTZ not null default now()
);

create table if not EXISTS seat_history (
    id serial4 PRIMARY KEY,
    code varchar(50) not null,
    status VARCHAR(50) not null,
    location_id int4 not null,
    created_by_id uuid not NULL,
    created_by VARCHAR(250) not null,
    created_at TIMESTAMPtz not null DEFAULT now(),
    last_updated_by_id uuid not null,
    last_updated_by varchar(250) not null,
    last_updated_at TIMESTAMPTZ not null default now(),
    FOREIGN key (location_id) REFERENCES location (id)
)