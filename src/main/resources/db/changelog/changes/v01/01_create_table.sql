CREATE TABLE person
(
    name           varchar(60)        NOT NULL,
    surname        varchar(60)        NOT NULL,
    age            int check (age > 0 and age < 110),
    phone_number   varchar(15) UNIQUE NOT NULL,
    city_of_living varchar(60)        NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (name, surname, age)
);