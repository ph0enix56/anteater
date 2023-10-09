drop schema if exists anteater cascade;
create schema anteater;

create table anteater.language
(
    id     integer generated always as identity
        constraint language_pk
            primary key,
    name   text,
    exotic boolean
);

create table anteater.tool
(
    id   integer generated always as identity
        constraint tool_pk
            primary key,
    name text,
    type text default 'OTHER',
    constraint tooltype_enum
        check (type in ('ARTISAN', 'INSTRUMENT', 'GAMING', 'VEHICLE', 'OTHER'))
);

create table anteater.background
(
	id   integer generated always as identity
		constraint background_pk
			primary key,
	name text,
    description text,
	feature text,
	skill_amount integer,
	skill_defaults text array
    tool_amount integer,
    tool_defaults 
    -- check skill_defaults array
);
