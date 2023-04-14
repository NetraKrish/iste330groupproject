-- ISTE-330 Group 4: Database
-- 04/14/2023

-- Create the database
CREATE DATABASE IF NOT EXISTS research;
USE research;

-- Create the tables
CREATE TABLE IF NOT EXISTS role (
    role_id INT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS account (
    account_id INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    account_password VARCHAR(50) NOT NULL,
    preferredContact VARCHAR(50) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (account_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE IF NOT EXISTS contact (
    account_id INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    PRIMARY KEY (account_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS office (
    account_id INT NOT NULL,
    building_name VARCHAR(50) NOT NULL,
    office_number VARCHAR(50) NOT NULL,
    PRIMARY KEY (account_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS abstract (
    abstract_id INT NOT NULL AUTO_INCREMENT,
    abstract_title VARCHAR(50) NOT NULL,
    abstract_text VARCHAR(120) NOT NULL,
    PRIMARY KEY (abstract_id)
);

CREATE TABLE IF NOT EXISTS faculty_abstract (
    account_id INT NOT NULL,
    abstract_id INT NOT NULL,
    PRIMARY KEY (account_id, abstract_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (abstract_id) REFERENCES abstract(abstract_id)
); 

CREATE TABLE IF NOT EXISTS account_faculty_interests (
    account_id INT NOT NULL,
    search_term_id INT NOT NULL,
    PRIMARY KEY (account_id, search_term_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (search_term_id) REFERENCES search(search_term_id)
);

CREATE TABLE IF NOT EXISTS faculty_topic (
    topic_id INT NOT NULL AUTO_INCREMENT,
    topic VARCHAR(50) NOT NULL,
    PRIMARY KEY (search_term_id)
);

CREATE TABLE IF NOT EXISTS account_student_interests (
    account_id INT NOT NULL,
    search_term_id INT NOT NULL,
    PRIMARY KEY (account_id, search_term_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (search_term_id) REFERENCES search(search_term_id)
);

CREATE TABLE IF NOT EXISTS student_topic (
    search_term_id INT NOT NULL AUTO_INCREMENT,
    search_term VARCHAR(50) NOT NULL,
    PRIMARY KEY (search_term_id)
);

CREATE TABLE IF NOT EXISTS account_guest_interests (
    account_id INT NOT NULL,
    search_term_id INT NOT NULL,
    PRIMARY KEY (account_id, search_term_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (search_term_id) REFERENCES search(search_term_id)
);

CREATE TABLE IF NOT EXISTS guest_topic (
    search_term_id INT NOT NULL AUTO_INCREMENT,
    search_term VARCHAR(50) NOT NULL,
    PRIMARY KEY (search_term_id)
);