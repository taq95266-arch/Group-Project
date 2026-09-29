USE CarServiceSystemDB;

INSERT INTO [USER]
(User_ID, Name, Role, Password, Phone, Email)
VALUES
(1, 'Mohammed Al Balushi', 'Customer', 'Mohammed123', '91234567', 'mohammed@gmail.com'),
(2, 'Abdulaziz Mohammed', 'Owner', 'Abdulaziz123', '92345678', 'abdulaziz@gmail.com'),
(3, 'Intisar Al Shezawi', 'Customer', 'Intisar123', '93456789', 'intisar@gmail.com'),
(4, 'Abdulrhman Al Gheilani', 'Owner', 'Abdulrhman123', '94567890', 'abdulrhman@gmail.com'),
(5, 'Taqwa Al Hinai', 'Customer', 'Taqwa123', '95678901', 'taqwa@gmail.com');

INSERT INTO GARAGE
(Owner_ID, Name, Location, Phone_Number, Commercial_Registration, Status, User_ID)
VALUES
(101, 'Muscat Auto Care', 'Al Khoudh, Muscat', '24123456', 'CR1001', 'Active', 2),
(102, 'Al Gheilani Garage', 'Bawshar, Muscat', '24567890', 'CR1002', 'Active', 4);

INSERT INTO SERVICE
(Service_ID, Name)
VALUES
(1, 'Oil Change'),
(2, 'Battery Replacement'),
(3, 'Car Washing'),
(4, 'Towing Service'),
(5, 'General Maintenance');

INSERT INTO SERVICE_REQUEST
(Service_Request_ID, Year, Model, Location, CarName, User_ID)
VALUES
(1001, 2022, 'Land Cruiser GXR', 'Al Khoudh, Muscat', 'Toyota', 1),
(1002, 2021, 'Patrol', 'Seeb, Muscat', 'Nissan', 3),
(1003, 2023, 'Camry', 'Bawshar, Muscat', 'Toyota', 5),
(1004, 2020, 'Accord', 'Al Amerat, Muscat', 'Honda', 1),
(1005, 2024, 'Sunny', 'Mabela, Muscat', 'Nissan', 3);

INSERT INTO ITEM_REQUEST
(Service_Request_ID, Service_ID)
VALUES
(1001, 1),
(1001, 5),
(1002, 2),
(1003, 3),
(1004, 4),
(1005, 1);

INSERT INTO SERVICE_OPTION
(Service_Option_ID, Type, Brand, Price, Size, Service_ID)
VALUES
(201, 'Synthetic Oil', 'Shell', 25.00, '5L', 1),
(202, 'Synthetic Oil', 'Mobil', 28.00, '5L', 1),
(203, 'Battery', 'Amaron', 45.00, '70AH', 2),
(204, 'Battery', 'ACDelco', 50.00, '70AH', 2),
(205, 'Full Wash', 'Standard', 8.00, 'SUV', 3),
(206, 'Basic Wash', 'Standard', 5.00, 'Sedan', 3),
(207, 'Tow Truck', 'Standard', 30.00, 'Local', 4),
(208, 'Full Inspection', 'Standard', 20.00, 'All Cars', 5);

INSERT INTO SUBSCRIPTION
(Subscription_ID, Type, Price, Date, Duration, Owner_ID)
VALUES
(301, 'Premium', 120.00, '2026-09-01', '1 Year', 101),
(302, 'Basic', 60.00, '2026-09-10', '6 Months', 102);
