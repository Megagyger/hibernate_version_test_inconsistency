create database test;
use test;

create table accounting_customers_other
(
    accounting_customers_pending_id int auto_increment primary key,
    customers_id int default 0 not null
)
    charset = utf8;

create table accounting_customers
(
    accounting_customers_id int auto_increment primary key,
    customers_id int default 0 not null

)
    charset = utf8;

create table customers
(
    customers_id int auto_increment primary key,
    accounting_customers_id int null
)
    charset = utf8;

alter table accounting_customers_other add constraint
    accounting_customers_other_cust_id_uk
    unique (customers_id);

alter table accounting_customers add constraint
    accounting_customers_customers_fk foreign key (customers_id) references customers (customers_id);

alter table customers add constraint
    fk_customers_accounting_customers foreign key (accounting_customers_id) references accounting_customers (accounting_customers_id);