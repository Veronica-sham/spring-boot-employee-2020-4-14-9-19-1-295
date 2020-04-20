CREATE TABLE IF NOT EXISTS employee(
	id int auto_increment not null,
	name varchar(30) not null,
	age int not null,
	gender varchar(30) not null,
	salary int not null,
	company_Id int not null,
	PRIMARY KEY (id),
	FOREIGN KEY (company_Id) REFERENCES company(id)
)