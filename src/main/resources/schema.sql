CREATE TABLE users (
  user_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  barangay varchar(255) DEFAULT NULL,
  municipality varchar(255) DEFAULT NULL,
  province varchar(255) DEFAULT NULL,
  street varchar(255) DEFAULT NULL,
  zipcode varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  oauth2_id varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY uk_username (username),
  UNIQUE KEY uk_email (email)
); 

CREATE TABLE roles (
  role_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE users_roles (
  user_id varchar(255) NOT NULL,
  role_id varchar(255) NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT fk_users_roles_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
  CONSTRAINT fk_users_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE categories (
  category_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  is_activated bit(1) DEFAULT NULL,
  is_deleted bit(1) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (category_id),
  UNIQUE KEY unique_category_name (name)
);

CREATE TABLE products (
  product_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  cost_price double DEFAULT NULL,
  current_quantity int DEFAULT NULL,
  description text,
  image mediumblob,
  is_activated bit(1) DEFAULT NULL,
  is_deleted bit(1) DEFAULT NULL,
  is_on_sale bit(1) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  sale_price double DEFAULT NULL,
  category_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (product_id),
  UNIQUE KEY uk_product_name (name),
  KEY fk_products_category_id (category_id),
  CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE shopping_carts (
  shopping_cart_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  total_items int DEFAULT NULL,
  total_price double DEFAULT NULL,
  user_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (shopping_cart_id),
  UNIQUE KEY uk_shopping_carts_user_id (user_id),
  CONSTRAINT fk_shopping_carts_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE cart_items (
  cart_item_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  quantity int DEFAULT NULL,
  unit_price double DEFAULT NULL,
  product_id varchar(255) DEFAULT NULL,
  shopping_cart_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (cart_item_id),
  KEY fk_cart_items_shopping_cart_id (shopping_cart_id),
  CONSTRAINT fk_cart_items_product_id FOREIGN KEY (product_id) REFERENCES products (product_id),
  CONSTRAINT fk_cart_items_shopping_cart_id FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts (shopping_cart_id)
);

CREATE TABLE orders (
  order_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  order_date date DEFAULT NULL,
  order_status enum('ACCEPTED','CANCELLED','COMPLETED','DENIED','IN_PROGRESS','PENDING','RETURNED','TO_RECEIVE','TO_SHIP') DEFAULT NULL,
  delivery_date date DEFAULT NULL,
  delivery_address varchar(255) DEFAULT NULL,
  is_accepted bit(1) DEFAULT NULL,
  message varchar(255) DEFAULT NULL,
  payment_method varchar(255) DEFAULT NULL,
  total_items int DEFAULT NULL,
  total_price double DEFAULT NULL,
  user_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (order_id),
  KEY fk_orders_user_id (user_id),
  CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE order_items (
  order_item_id varchar(255) NOT NULL,
  created datetime(6) DEFAULT NULL,
  modified datetime(6) DEFAULT NULL,
  quantity int DEFAULT NULL,
  unit_price double DEFAULT NULL,
  order_id varchar(255) DEFAULT NULL,
  product_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (order_item_id),
  KEY fk_order_items_order_id (order_id),
  CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders (order_id),
  CONSTRAINT fk_order_items_product_id FOREIGN KEY (product_id) REFERENCES products (product_id)
);