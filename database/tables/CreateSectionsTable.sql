CREATE TABLE sections
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name varchar(25),
	description varchar(45),
	created_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
