-- noinspection SqlDialectInspectionForFile

-- ISTE-330 Group 4: Database
-- 04/14/2023

-- Create the database
CREATE DATABASE IF NOT EXISTS research;
USE research;

-- Create the tables
CREATE TABLE IF NOT EXISTS role (
    roleID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (roleID)
);

CREATE TABLE IF NOT EXISTS account (
    accountID INT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    preferredContact VARCHAR(50) NOT NULL,
    roleID INT NOT NULL,
    PRIMARY KEY (accountID),
    FOREIGN KEY (roleID) REFERENCES role(roleID)
);

CREATE TABLE IF NOT EXISTS contact (
    accountID INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    PRIMARY KEY (accountID),
    FOREIGN KEY (accountID) REFERENCES account(accountID)
);

CREATE TABLE IF NOT EXISTS office (
    accountID INT NOT NULL,
    building VARCHAR(50) NOT NULL,
    office VARCHAR(50) NOT NULL,
    PRIMARY KEY (accountID),
    FOREIGN KEY (accountID) REFERENCES account(accountID)
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
    FOREIGN KEY (accountID) REFERENCES account(accountID),
    FOREIGN KEY (abstractID) REFERENCES abstract(abstractID)
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
    FOREIGN KEY (accountID) REFERENCES account(accountID),
    FOREIGN KEY (interestID) REFERENCES faculty_interest(interestID)
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
    FOREIGN KEY (accountID) REFERENCES account(accountID),
    FOREIGN KEY (interestID) REFERENCES student_interest(interestID)
);

CREATE TABLE IF NOT EXISTS guest_interest (
    interestID INT NOT NULL AUTO_INCREMENT,
    interest VARCHAR(50) NOT NULL,
    PRIMARY KEY (interestID)
);

CREATE TABLE IF NOT EXISTS account_guest_interests (
    accountID INT NOT NULL,
    interestID INT NOT NULL,
    PRIMARY KEY (accountID, interestID),
    FOREIGN KEY (accountID) REFERENCES account(accountID),
    FOREIGN KEY (interestID) REFERENCES guest_interest(interestID)
);

