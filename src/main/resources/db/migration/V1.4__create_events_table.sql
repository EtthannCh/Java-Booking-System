create table if not EXISTS events(
    id serial PRIMARY KEY,
    name VARCHAR(250) not NULL UNIQUE,
    description text,
    period_start TIMESTAMPtz not null,
    period_end TIMESTAMPtz not null,
    active boolean default true,
    created_at TIMESTAMPtz not null,
    created_by VARCHAR(250) not null,
    created_by_id UUID not null,
    last_updated_at TIMESTAMPtz not null,
    last_updated_by VARCHAR(250) not null,
    last_updated_by_id UUID not null
);