-- noinspection SqlDialectInspectionForFile

-- ISTE-330 Group 4: Database
-- 04/14/2023

DROP DATABASE IF EXISTS iste330group4;

-- Create the database
CREATE DATABASE IF NOT EXISTS iste330group4;
USE iste330group4;

-- Create the tables
CREATE TABLE IF NOT EXISTS role (
    roleID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (roleID)
);

INSERT INTO role (name) VALUES ("student"); -- 1
INSERT INTO role (name) VALUES ("faculty"); -- 2
INSERT INTO role (name) VALUES ("guest");   -- 3

CREATE TABLE IF NOT EXISTS account (
    accountID INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    preferredContact VARCHAR(50) NOT NULL,
    roleID INT NOT NULL,
    PRIMARY KEY (accountID),
    CONSTRAINT account_roleID_FK FOREIGN KEY (roleID) REFERENCES role(roleID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS contact (
    accountID INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    PRIMARY KEY (accountID),
    CONSTRAINT contact_accountID_FK FOREIGN KEY (accountID) REFERENCES account(accountID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS office (
    accountID INT NOT NULL,
    building VARCHAR(50) NOT NULL,
    number VARCHAR(50) NOT NULL,
    PRIMARY KEY (accountID),
    CONSTRAINT office_accountID_FK FOREIGN KEY (accountID) REFERENCES account(accountID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS abstract (
    abstractID INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    body TEXT NOT NULL,
    PRIMARY KEY (abstractID)
);

CREATE TABLE IF NOT EXISTS faculty_abstract (
    accountID INT NOT NULL,
    abstractID INT NOT NULL,
    PRIMARY KEY (accountID, abstractID),
    CONSTRAINT faculty_abstract_accountID_FK FOREIGN KEY (accountID) REFERENCES account(accountID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT faculty_abstract_abstractID_FK FOREIGN KEY (abstractID) REFERENCES abstract(abstractID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS faculty_interest (
    interestID INT NOT NULL AUTO_INCREMENT,
    interest VARCHAR(50) NOT NULL,
    PRIMARY KEY (interestID)
);

CREATE TABLE IF NOT EXISTS account_faculty_interest (
    accountID INT NOT NULL,
    interestID INT NOT NULL,
    PRIMARY KEY (accountID, interestID),
    CONSTRAINT account_faculty_interest_accountID_FK FOREIGN KEY (accountID) REFERENCES account(accountID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT account_faculty_interest_interestID_FK FOREIGN KEY (interestID) REFERENCES faculty_interest(interestID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS student_interest (
    interestID INT NOT NULL AUTO_INCREMENT,
    interest VARCHAR(50) NOT NULL,
    PRIMARY KEY (interestID)
);

CREATE TABLE IF NOT EXISTS account_student_interest (
    accountID INT NOT NULL,
    interestID INT NOT NULL,
    PRIMARY KEY (accountID, interestID),
    CONSTRAINT account_student_interest_accountID_FK FOREIGN KEY (accountID) REFERENCES account(accountID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT account_student_interest_interestID_FK FOREIGN KEY (interestID) REFERENCES student_interest(interestID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS guest_interest (
    interestID INT NOT NULL AUTO_INCREMENT,
    interest VARCHAR(50) NOT NULL,
    PRIMARY KEY (interestID)
);

CREATE TABLE IF NOT EXISTS account_guest_interest (
    accountID INT NOT NULL,
    interestID INT NOT NULL,
    PRIMARY KEY (accountID, interestID),
    CONSTRAINT account_guest_interest_accountID_FK FOREIGN KEY (accountID) REFERENCES account(accountID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT account_guest_interest_interestID_FK FOREIGN KEY (interestID) REFERENCES guest_interest(interestID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO iste330group4.account(firstName, lastName, password, preferredContact, roleID) VALUES 
("Tom", "Bombadil", "HH88P6Zmmgb4Dop8pwwvlzSGWmgNFesSwo0Vow6xJd0=", "office visit", 2), 
("James", "Bond", "zsPC6/3tioC7RLr1ibeughOIUynmbdjon3MSRPdwTog=", "email", 2), 
("jimmy", "john", "fuJS5U4HQpNXJ8ci/Q/a5VT+Ad27+WHzFfnx+omvLrU=", "phone", 1),
("johnny", "jim", "k/CfMae9IgeaQHhha+UgEqpcWooei+Ix06tFGHrh+9k=", "phone", 1),
("john", "smith", "txjxNU9yRzEuyghtmgJK/l+nF93qWt7d1vErz5RbLow=", "none", 3);

INSERT INTO iste330group4.office(accountID, building, number) VALUES 
(1, "forest", "a301"),
(2, "MI6", "007");
INSERT INTO iste330group4.contact(accountID, email, phone) VALUES 
(1, "email01@this.dontmatter", "0123456789"), 
(2, "007@this.dontmatter", "0070070070"),
(3, "email02@this.dontmatter", "987654321"),
(4, "email03@this.dontmatter", "123459876"),
(5, "nunya", "beeswax");




