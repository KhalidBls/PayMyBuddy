/* Setting up PROD DB */
create database paymybuddy_prod;
use paymybuddy_prod;

create table users(
id int PRIMARY KEY AUTO_INCREMENT,
first_name varchar(100) NOT NULL,
last_name varchar(100) NOT NULL,
email varchar(100) NOT NULL,
balance double DEFAULT 0.0 NOT NULL,
password varchar(100) NOT NULL
);

create table users_relationship(
id_user_relating int NOT NULL,
id_user_related int NOT NULL,
FOREIGN KEY (id_user_relating)
 REFERENCES users(id),
 FOREIGN KEY (id_user_related)
 REFERENCES users(id)
);

create table transactions(
id int PRIMARY KEY AUTO_INCREMENT,
amount double DEFAULT 0.0 NOT NULL,
id_user_sender int NOT NULL,
id_user_receiver int NOT NULL,
fees double DEFAULT 0.0 NOT NULL,
id_description int NOT NULL,
type varchar(100),
 FOREIGN KEY (id_user_sender)
 REFERENCES users(id),
 FOREIGN KEY (id_description)
 REFERENCES descriptions(id),
 FOREIGN KEY (id_user_receiver)
 REFERENCES users(id));

 create table descriptions(
 id int PRIMARY KEY AUTO_INCREMENT,
 content varchar(100) NOT NULL
 );

 create table bank_acount(
 id int PRIMARY KEY AUTO_INCREMENT,
 id_user int NOT NULL,
 swift varchar(100) NOT NULL,
 iban varchar(100) NOT NULL,
 FOREIGN KEY (id_user)
 REFERENCES users(id));

INSERT INTO users(id,first_name, last_name, email, balance, password) VALUES (1,"bob","bobby","bob.bobby@mail.com",27,"bobpass");
INSERT INTO users(id,first_name, last_name, email, balance, password) VALUES (2,"jack","jacky","jack.jacky@mail.com",12,"jackpass");

/* Setting up TEST DB */
create database paymybuddy_test;
use paymybuddy_test;

create table users(
id int PRIMARY KEY AUTO_INCREMENT,
first_name varchar(100) NOT NULL,
last_name varchar(100) NOT NULL,
email varchar(100) NOT NULL,
balance double DEFAULT 0.0 NOT NULL,
password varchar(100) NOT NULL
);

create table users_relationship(
id_user_relating int NOT NULL,
id_user_related int NOT NULL,
FOREIGN KEY (id_user_relating)
 REFERENCES users(id),
 FOREIGN KEY (id_user_related)
 REFERENCES users(id)
);

create table transactions(
id int PRIMARY KEY AUTO_INCREMENT,
amount double DEFAULT 0.0 NOT NULL,
id_user_sender int NOT NULL,
id_user_receiver int NOT NULL,
fees double DEFAULT 0.0 NOT NULL,
id_description int NOT NULL,
type varchar(100),
 FOREIGN KEY (id_user_sender)
 REFERENCES users(id),
 FOREIGN KEY (id_description)
 REFERENCES descriptions(id),
 FOREIGN KEY (id_user_receiver)
 REFERENCES users(id));

 create table descriptions(
 id int PRIMARY KEY AUTO_INCREMENT,
 content varchar(100) NOT NULL
 );

 create table bank_acount(
 id int PRIMARY KEY AUTO_INCREMENT,
 id_user int NOT NULL,
 swift varchar(100) NOT NULL,
 iban varchar(100) NOT NULL,
 FOREIGN KEY (id_user)
 REFERENCES users(id));

INSERT INTO users(id,first_name, last_name, email, balance, password) VALUES (1,"bob","bobby","bob.bobby@mail.com",27,"bobpass");
INSERT INTO users(id,first_name, last_name, email, balance, password) VALUES (2,"jack","jacky","jack.jacky@mail.com",12,"jackpass");
