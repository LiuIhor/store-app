create table carts
(
    id         bigint not null auto_increment,
    created    datetime(6),
    quantity   integer,
    product_id bigint,
    user_id    bigint,
    primary key (id)
);
create table orders
(
    id         bigint not null auto_increment,
    created    datetime(6),
    quantity   integer,
    status     varchar(255),
    updated    datetime(6),
    product_id bigint,
    user_id    bigint,
    primary key (id)
);
create table products
(
    id        bigint not null auto_increment,
    available integer,
    price     decimal(19, 2),
    title     varchar(255),
    primary key (id)
);
create table tokens
(
    id      bigint not null auto_increment,
    created datetime(6),
    token   varchar(255),
    user_id bigint not null,
    primary key (id)
);
create table users
(
    id       bigint not null auto_increment,
    email    varchar(255),
    password varchar(255),
    username varchar(255),
    role     varchar(255),
    primary key (id)
);
alter table carts
    add constraint carts_products_fk foreign key (product_id) references products (id);
alter table carts
    add constraint carts_users_fk  foreign key (user_id) references users (id);
alter table orders
    add constraint orders_products_fk foreign key (product_id) references products (id);
alter table orders
    add constraint orders_users_fk foreign key (user_id) references users (id);
alter table tokens
    add constraint tokens_users foreign key (user_id) references users (id);