-- ISTE-330 Group 4: Database
-- 04/14/2023

-- Create the database
DROP DATABASE IF EXISTS research;
CREATE DATABASE research;
USE research;

-- Create the tables
CREATE TABLE account (
    account_id INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    account_password VARCHAR(50) NOT NULL,
    prefferedContact VARCHAR(50) NOT NULL,
    PRIMARY KEY (researcher_id)
    FOREIGN KEY (role_id) REFERENCES account(account_id)
);

CREATE TABLE role (
    role_id INT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE contact (
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE office (
    building_name VARCHAR(50) NOT NULL,
    office_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE faculty_department (
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

CREATE TABLE department (
    department_id INT NOT NULL AUTO_INCREMENT,
    department_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (department_id)
);

CREATE TABLE faculty_abstract (
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (abstract_id) REFERENCES abstract(abstract_id)
);

CREATE TABLE abstract (
    abstract_id INT NOT NULL AUTO_INCREMENT,
    abstract_title VARCHAR(50) NOT NULL,
    abstract_text VARCHAR(120) NOT NULL,
    PRIMARY KEY (abstract_id)
);

CREATE TABLE faculty_search (
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (search_id) REFERENCES search(search_id),
);

CREATE TABLE faculty_topic (
    search_term_id INT NOT NULL AUTO_INCREMENT,
    search_term VARCHAR(50) NOT NULL,
    PRIMARY KEY (search_term_id)
);

CREATE TABLE student_search (
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (search_id) REFERENCES search(search_id)
);

CREATE TABLE student_topic (
    search_term_id INT NOT NULL AUTO_INCREMENT,
    search_term VARCHAR(50) NOT NULL,
    PRIMARY KEY (search_term_id)
);

CREATE TABLE guest_search (
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (search_id) REFERENCES search(search_id)
);

CREATE TABLE guest_topic (
    search_term_id INT NOT NULL AUTO_INCREMENT,
    search_term VARCHAR(50) NOT NULL,
    PRIMARY KEY (search_term_id)
);