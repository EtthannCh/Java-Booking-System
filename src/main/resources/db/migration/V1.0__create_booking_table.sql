create table if NOT EXISTS booking( 
    id serial4 PRIMARY KEY,
    booked_at TIMESTAMPtz not null,
    booked_by VARCHAR(250) not null,
    booked_by_id UUID not null
);