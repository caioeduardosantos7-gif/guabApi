-- V1__init.sql  — Criação inicial do schema guanvendas

CREATE TABLE IF NOT EXISTS companies (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(150) NOT NULL,
    document VARCHAR(18)  NOT NULL,
    phone    VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       ENUM('admin','user') NOT NULL DEFAULT 'user',
    company_id BIGINT       NULL,
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_users_company FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS customers (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(120) NOT NULL,
    person_type ENUM('fisica','juridica') NOT NULL,
    document    VARCHAR(18)  NOT NULL,
    phone       VARCHAR(20)  NOT NULL,
    email       VARCHAR(150) NOT NULL,
    created_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS products (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    category    VARCHAR(80)  NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    old_price   DECIMAL(10,2) NULL,
    description TEXT         NOT NULL,
    tags        JSON         NOT NULL DEFAULT (JSON_ARRAY()),
    image       VARCHAR(500) NOT NULL,
    created_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS sales (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT       NULL,
    total       DECIMAL(10,2) NOT NULL,
    created_at  DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_sales_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS sale_items (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_id  BIGINT       NOT NULL,
    product  VARCHAR(150) NOT NULL,
    quantity INT          NOT NULL,
    price    DECIMAL(10,2) NOT NULL,
    method   VARCHAR(50)  NOT NULL,
    CONSTRAINT fk_sale_items_sale FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transactions (
    id     VARCHAR(20)  NOT NULL PRIMARY KEY,
    item   VARCHAR(200) NOT NULL,
    date   DATETIME(6)  NOT NULL,
    price  DECIMAL(10,2) NOT NULL,
    status ENUM('completed','cancelled') NOT NULL
);
