CREATE TABLE IF NOT EXISTS parking_boy(
	id int auto_increment not null,
	nick_name varchar(30) not null,
	PRIMARY KEY (id),
	FOREIGN KEY (id) REFERENCES employee(id)
)