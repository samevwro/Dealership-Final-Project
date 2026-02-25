DROP TABLE IF EXISTS dealer_ship_customer;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS dealer_ship;

--this is only used for creating the h2 in memory database for j-unit testing
CREATE TABLE dealer_ship (
	dealer_ship_id int NOT NULL AUTO_INCREMENT,
	dealer_ship_name VARCHAR(128) NOT NULL,
	dealer_ship_address VARCHAR(128) NOT NULL,
	dealer_ship_state VARCHAR(64),
	dealer_ship_zip int,
	dealer_ship_phone VARCHAR(64),
	PRIMARY KEY (dealer_ship_id)
);

CREATE TABLE employee (
	employee_id int NOT NULL AUTO_INCREMENT,
	employee_first_name VARCHAR(128) NOT NULL,
	employee_last_name VARCHAR(128) NOT NULL,
	employee_phone VARCHAR(128),
	employee_email VARCHAR(128),
	employee_job_title VARCHAR(64),
	dealer_ship_id int,
	PRIMARY KEY (employee_id),
	FOREIGN KEY (dealer_ship_id) REFERENCES dealer_ship (dealer_ship_id) ON DELETE CASCADE
);

CREATE TABLE vehicle (
	vehicle_id int NOT NULL AUTO_INCREMENT,
	vehicle_year integer NOT NULL,
	vehicle_make VARCHAR(64) NOT NULL,
	vehicle_model VARCHAR(64) NOT NULL,
	vehicle_milage int,
	vehicle_physical_damage VARCHAR(256),
	vehicle_image VARCHAR(256),
	vehicle_type VARCHAR(128),
	dealer_ship_id int,
	PRIMARY KEY (vehicle_id),
	FOREIGN KEY (dealer_ship_id) REFERENCES dealer_ship (dealer_ship_id) ON DELETE CASCADE
);

CREATE TABLE customer (
	customer_id int NOT NULL AUTO_INCREMENT,
	customer_first_name VARCHAR(128) NOT NULL,
	customer_last_name VARCHAR(128) NOT NULL,
	customer_email VARCHAR(128) NOT NULL,
	PRIMARY KEY (customer_id)
);

CREATE TABLE dealer_ship_customer (
	dealer_ship_id int NOT NULL,
	customer_id int NOT NULL,
	FOREIGN KEY (dealer_ship_id) REFERENCES dealer_ship (dealer_ship_id) ON DELETE CASCADE,
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);