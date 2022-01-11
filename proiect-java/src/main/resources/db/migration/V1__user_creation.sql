CREATE TABLE appuser (
    id_appuser BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    user_type BIGINT,
    certificate_url varchar(256),
    account_created datetime,

    PRIMARY KEY (id_appuser)
    );

CREATE TABLE authority (
    name VARCHAR(50) NOT NULL,

    PRIMARY KEY (name)
);

CREATE TABLE appuser_authority (
    appuser_id BIGINT NOT NULL,
    authority_name VARCHAR(50) NOT NULL,

    PRIMARY KEY (appuser_id,authority_name),
    FOREIGN KEY (appuser_id) REFERENCES appuser(id_appuser),
    FOREIGN KEY (authority_name) REFERENCES authority(name)

);

CREATE TABLE category (
    id_category BIGINT NOT NULL auto_increment,
    category_name VARCHAR(50) NOT NULL,

    PRIMARY KEY (id_category)
);

CREATE TABLE product(
    id_product BIGINT NOT NULL auto_increment,
    name VARCHAR(50) NOT NULL,
    price DECIMAL(21,2) NOT NULL,
    stock DECIMAL(21,2) NOT NULL,
    category_id BIGINT,
    grower_id BIGINT,

    PRIMARY KEY (id_product),
    FOREIGN KEY (category_id) REFERENCES category(id_category),
    FOREIGN KEY (grower_id) REFERENCES appuser(id_appuser)
);

CREATE TABLE cart_order_details(
                                   id_cart_order_details BIGINT NOT NULL auto_increment,
                                   total_price DECIMAL(21,2),
                                   status_command VARCHAR(50),
                                   appuser_id BIGINT,

                                   PRIMARY KEY (id_cart_order_details),
                                   FOREIGN KEY (appuser_id) REFERENCES appuser(id_appuser)
);

CREATE TABLE cart_items(
    id_cart_items BIGINT NOT NULL auto_increment,
    quantity DECIMAL(21,2) NOT NULL,
    product_id BIGINT,
    cart_order_details_id BIGINT,

    PRIMARY KEY (id_cart_items),
    FOREIGN KEY (product_id) REFERENCES product(id_product),
    FOREIGN KEY (cart_order_details_id) REFERENCES cart_order_details(id_cart_order_details)
);

