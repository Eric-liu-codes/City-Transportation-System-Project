INSERT INTO Person (id, first_name, last_name, address, phone_number, email)
VALUES (1, 'John', 'Doe', '111 First St', '1234567890', 'johndoe@email.com'),
       (2, 'Jane', 'Smith', '222 Second St', '0987654321', 'janesmith@email.com'),
       (3, 'Bob', 'Johnson', '333 Third St', '135791113', 'bobjohnson@email.com'),
       (4, 'Emily', 'Williams', '444 Fourth St', '246802468', 'emilywilliams@email.com'),
       (5, 'Michael', 'Brown', '555 Fifth St', '369258147', 'michaelbrown@email.com'),
       (6, "Mary", "Allen", "111 Holly St", "1234567890", "maryallen@email.com"),
       (7, "Andy", "Melcer", "222 Lily St", "0987654321", "andymelcer@email.com"),
       (8, "Joy", "Garcia", "333 Rose St", "135791113", "joygarcia@email.com"),
       (9, "Emily", "Miller", "444 Tulip St", "246802468", "emilymiller@email.com"),
       (10, "Michael", "Davis", "555 Orchid St", "369258147", "michaeldavis@email.com");

INSERT INTO Driver (id, license_number, years_of_experience, person_id)
VALUES (1, 'A123456789', 5, 1),
       (2, 'B234567890', 10, 2),
       (3, 'C345678901', 15, 3),
       (4, 'D456789012', 20, 4),
       (5, 'E567890123', 21,5);

INSERT INTO Passenger (id, number_of_rides, person_id)
VALUES (1, 10, 6),
       (2, 20, 7),
       (3, 30, 8),
       (4, 40, 9),
       (5, 50, 10);

INSERT INTO Vehicle (id, make, model, year, capacity, driver_id)
VALUES (1, 'Toyota', 'Coaster', 2020, 30, 1),
(2, 'Mercedes-Benz', 'Citan', 2018, 25, 2),
(3, 'Volkswagen', 'Crafter', 2019, 35, 3),
(4, 'Iveco', 'Daily', 2021, 40, 4),
(5, 'Peugeot', 'Boxer', 2022, 45, 5),
(6, "Toyota", "Camry", 2020, 4, 1),
(7, "Honda", "Civic", 2019, 4, 2),
(8, "Tesla", "Model 3", 2021, 5, 3),
(9, "Ford", "Fusion", 2018, 4, 4),
(10, "Chevrolet", "Cruze", 2017, 4, 5),
(11, "Bombardier", "Electric Multiple Unit", 2021, 1000, 1),
(12, "Alstom", "Coradia Stream", 2020, 800, 2),
(13, "Siemens", "Vectron", 2019, 500, 3),
(14, "Stadler Rail", "Flirt", 2018, 300, 4),
(15, "CRRC", "ZEFIRO", 2017, 200, 5);

INSERT INTO Bus (id, bus_number, vehicle_id)
VALUES (1, 11, 1),
(2, 12, 2),
(3, 13, 3),
(4, 14, 4),
(5, 15, 5);

INSERT INTO Taxi (id, vehicle_id, license_plate)
VALUES (1, 6, "ABC123"),
(2, 7, "DEF456"),
(3, 8, "GHI789"),
(4, 9, "JKL012"),
(5, 10, "MNO345");

INSERT INTO Train (id, vehicle_id, train_headcode)
VALUES (1, 11, "THC001"),
(2, 12, "THC002"),
(3, 13, "THC003"),
(4, 14, "THC004"),
(5, 15, "THC005");

INSERT INTO Route (id, name, vehicle_id)
VALUES (1, "Route 1", 1),
(2, "Route 2", 2),
(3, "Route 3", 3),
(4, "Route 4", 4),
(5, "Route 5", 5);

INSERT INTO Station (id, name, type, address, route_id) VALUES
(1, 'A', 'Type A', '123 Main St.', 1),
(2, 'B', 'Type B', '456 Elm St.', 1),
(3, 'C', 'Type C', '789 Oak St.', 2),
(4, 'D', 'Type D', '321 Maple St.', 2),
(5, 'E', 'Type E', '654 Pine St.', 3),
(6, 'F', 'Type F', '987 Cedar St.', 3);

-- A to B (5)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance) 
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'A'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'B'), 
     5);
     
-- B to A (5)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance) 
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'B'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'A'), 
     5);

-- A to E (10)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance) 
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'A'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'E'), 
     10);

-- E to A (10)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance)
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'E'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'A'), 
     10);

-- B to C (4)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance)
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'B'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'C'), 
     4);

-- C to B (4)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance)
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'C'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'B'), 
     4);
     
-- C to E (3)
INSERT INTO TransportationSystem.StationConnection (station_1_id, station_2_id, distance) 
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'C'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'E'), 
     2);
     
-- E to C (3)
INSERT INTO TransportationSystem.StationConnection (station_1_id, station_2_id, distance) 
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'E'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'C'), 
     3);

-- D to E (5)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance) 
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'D'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'E'), 
     5);

-- E to D (5)
INSERT INTO TransportationSystem.StationConnection (id, station_1_id, station_2_id, distance)
VALUES 
    ((SELECT id FROM TransportationSystem.Station WHERE name = 'E'), 
     (SELECT id FROM TransportationSystem.Station WHERE name = 'D'), 
     5);



INSERT INTO Ticket (id, payment_method, fare, passenger_id)
VALUES (1, "Credit Card", 10.5, 1),
(2, "Debit Card", 11.5, 2),
(3, "Cash", 12.5, 3),
(4, "Paypal", 13.5, 4),
(5, "Apple Pay", 14.5, 5);

INSERT INTO VehicleMaintenance (id, date, type, description, vehicle_id)
VALUES (1, "2023-01-01", "Oil change", "regular oil change", 1),
(2, "2023-02-02", "Corrective maintenance", "Repair of faulty parts", 2),
(3, "2023-03-03", "Brake pad replacement", "replacement of brake pads", 3),
(4, "2023-04-04", "Engine tune-up", "regular engine tune-up", 4),
(5, "2023-05-05", "Inspection", "Inspection of the train by regulatory authorities", 5);


INSERT INTO AccidentReport (id, date, description, person_id, vehicle_id)
VALUES (1, "2023-02-13", "Accident at intersection of Main St and First Ave", 1, 1),
       (2, "2023-02-14", "Bus collided with a car", 2, 2),
       (3, "2023-02-15", "Train derailed", 3, 3),
       (4, "2023-02-16", "Taxi hit a pedestrian", 4, 4),
       (5, "2023-02-17", "Accident involving multiple vehicles on the highway", 5, 5);
