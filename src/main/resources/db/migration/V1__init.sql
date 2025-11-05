CREATE TABLE providers (
                           id serial PRIMARY KEY,
                           fullname varchar(255),
                           email varchar(255),
                           phone varchar(100),
                           address varchar(255),
                           active boolean default true
);

CREATE TABLE provided_services (
                                   id serial PRIMARY KEY,
                                   title varchar(255),
                                   description text,
                                   price numeric,
                                   date_time_available timestamp,
                                   status varchar(50),
                                   provider_id integer REFERENCES providers(id) ON DELETE CASCADE
);
