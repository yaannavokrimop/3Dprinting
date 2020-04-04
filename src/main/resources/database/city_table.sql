create table cities
(
    city_id integer not null
        constraint cities_pk
            primary key,
    title   varchar not null
);
