CREATE DATABASE CarServiceSystemDB;

USE CarServiceSystemDB;

CREATE TABLE [USER] (
    User_ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Role VARCHAR(30),
    Password VARCHAR(100),
    Phone VARCHAR(20),
    Email VARCHAR(100)
);

CREATE TABLE GARAGE (
    Owner_ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Location VARCHAR(100),
    Phone_Number VARCHAR(20),
    Commercial_Registration VARCHAR(50),
    Status VARCHAR(30),
    User_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [USER](User_ID)
);

CREATE TABLE SERVICE_REQUEST (
    Service_Request_ID INT PRIMARY KEY,
    Year INT,
    Model VARCHAR(50),
    Location VARCHAR(100),
    CarName VARCHAR(50),
    User_ID INT,
    FOREIGN KEY (User_ID) REFERENCES [USER](User_ID)
);

CREATE TABLE SERVICE (
    Service_ID INT PRIMARY KEY,
    Name VARCHAR(100)
);

CREATE TABLE ITEM_REQUEST (
    Service_Request_ID INT,
    Service_ID INT,
    PRIMARY KEY (Service_Request_ID, Service_ID),
    FOREIGN KEY (Service_Request_ID) REFERENCES SERVICE_REQUEST(Service_Request_ID),
    FOREIGN KEY (Service_ID) REFERENCES SERVICE(Service_ID)
);

CREATE TABLE SERVICE_OPTION (
    Service_Option_ID INT PRIMARY KEY,
    Type VARCHAR(50),
    Brand VARCHAR(50),
    Price DECIMAL(10,2),
    Size VARCHAR(30),
    Service_ID INT,
    FOREIGN KEY (Service_ID) REFERENCES SERVICE(Service_ID)
);

CREATE TABLE SUBSCRIPTION (
    Subscription_ID INT PRIMARY KEY,
    Type VARCHAR(50),
    Price DECIMAL(10,2),
    Date DATE,
    Duration VARCHAR(30),
    Owner_ID INT,
    FOREIGN KEY (Owner_ID) REFERENCES GARAGE(Owner_ID)
);
