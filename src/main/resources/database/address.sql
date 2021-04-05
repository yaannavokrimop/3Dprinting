create table address
(
    description varchar(255),
    user_id     uuid    not null
        constraint fk6i66ijb8twgcqtetl8eeeed6v
        references users,
    city_id     integer not null
        constraint fka6p4hdfq92oyy92gb5ra8xiw7
        references cities,
    constraint address_pkey
        primary key (user_id, city_id)
);