CREATE DATABASE /*!32312 IF NOT EXISTS*/ tp_jdbc_1;
USE tp_jdbc_1;

DROP TABLE IF EXISTS pizza;	

create table pizza
(
	id_pizza int(5) auto_increment PRIMARY KEY,
    pizza_code varchar(4) NOT NULL,
	pizza_libelle varchar(20) NOT NULL,
    pizza_price float(4) default 0,
    pizza_categorie varchar(15) NOT null
);