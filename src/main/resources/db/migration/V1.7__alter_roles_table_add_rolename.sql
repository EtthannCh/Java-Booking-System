alter TABLE roles
add column if not EXISTS code varchar(100) not null;