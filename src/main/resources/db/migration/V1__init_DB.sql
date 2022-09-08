create table users
(
    id       bigint not null auto_increment,
    email    varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

create table roles
(
    id   bigint not null auto_increment,
    name varchar(20),
    primary key (id)
);

create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

create table products
(
    id        bigint not null auto_increment,
    available integer,
    price     decimal(19, 2),
    title     varchar(255),
    primary key (id)
);

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

# alter table users drop index UKr43af9ap4edm43mmtq01oddj6;

alter table users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);

# alter table users drop index UK6dotkott2kjsp8vw4d0m25fb7;

alter table users
    add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);

alter table carts
    add constraint carts_products_fk foreign key (product_id) references products (id);

alter table carts
    add constraint carts_users_fk foreign key (user_id) references users (id);

alter table orders
    add constraint orders_products_fk foreign key (product_id) references products (id);

alter table orders
    add constraint orders_users_fk foreign key (user_id) references users (id);

alter table user_roles
    add constraint user_roles_roles_fk foreign key (role_id) references roles (id);

alter table user_roles
    add constraint user_roles_users foreign key (user_id) references users (id);