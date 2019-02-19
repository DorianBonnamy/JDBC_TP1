CREATE DATABASE /*!32312 IF NOT EXISTS*/ tp_jdbc_1;
USE tp_jdbc_1;

DROP TABLE IF EXISTS pizza;	

create table pizza
(
	id_pizza int(5) auto_increment PRIMARY KEY,
    pizza_code varchar(4) NOT NULL,
	pizza_libelle varchar(20) NOT NULL,
    pizza_price float(4) default 0
);

update pizza set pizza_code='gfd', pizza_libelle ='fds',pizza_price='23.0' where pizza_code='IND';