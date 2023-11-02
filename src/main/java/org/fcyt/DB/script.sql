create table hotel
(
    id        integer default nextval('"Hotel_id_seq"'::regclass) not null
        constraint "Hotel_pkey"
            primary key,
    nombre    varchar(200)                                        not null,
    direccion varchar(200)                                        not null,
    telefono  varchar(40)                                         not null
);

alter table hotel
    owner to postgres;


