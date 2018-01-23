CREATE TABLE IF NOT EXISTS user ( 
	id INT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(8) NOT NULL , 
	first_name VARCHAR(100) NOT NULL ,
	last_name VARCHAR(100) NOT NULL ,
	email VARCHAR(100) NOT NULL , 
	 PRIMARY KEY (id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS song ( 
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL , 
	artist VARCHAR(100) NOT NULL ,
	album VARCHAR(100) NOT NULL ,
	release_year INT NOT NULL , 
	 PRIMARY KEY (id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS songlist ( 
	id INT NOT NULL AUTO_INCREMENT,
	owner INT NOT NULL , 
	access VARCHAR(10) NOT NULL ,
	 PRIMARY KEY (id) ,
	 FOREIGN KEY (owner) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS playlistEntries ( 
	list_id INT NOT NULL,
	song_id INT NOT NULL, 
	 PRIMARY KEY (list_id, song_id),
	 FOREIGN KEY (list_id) REFERENCES songlist(id) ON DELETE CASCADE,
	 FOREIGN KEY (song_id) REFERENCES song(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

