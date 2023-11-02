create database db_hoteles;

create table hotel
(
    id serial primary key                                         not null,
    nombre    varchar(200)                                        not null,
    direccion varchar(200)                                        not null,
    telefono  varchar(40)                                         not null
);

alter table hotel
    owner to postgres;


