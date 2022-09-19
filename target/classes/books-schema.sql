DROP TABLE IF EXISTS BOOKS;
  
create table books (id bigint not null auto_increment, 
			author varchar(255), 
			isbn bigint not null, 
			name varchar(255), 
			pages integer not null, 
			price float not null, 
			primary key (id));