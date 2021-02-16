BEGIN TRANSACTION;

DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user_preferences;
DROP TABLE IF EXISTS character_comic;
DROP TABLE IF EXISTS collection_comic;
DROP TABLE IF EXISTS collections;
DROP TABLE IF EXISTS comics;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS seq_collection_id;
DROP SEQUENCE IF EXISTS seq_comic_id;
DROP SEQUENCE IF EXISTS seq_character_id;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_collection_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_comic_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_character_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE user_info (
    user_id int NOT NULL,
    is_premium boolean DEFAULT false,
    first_name varchar(45) NOT NULL,
    last_name varchar(45) NOT NULL,
    email varchar(45) NOT NULL,
    
    CONSTRAINT PK_user_info PRIMARY KEY (user_id),
    CONSTRAINT FK_user_info FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE user_preferences (
    user_id int NOT NULL,
    author varchar(45),
    genre varchar(45),
    universe varchar(45),
    character varchar(45),
    
    CONSTRAINT PK_user_preferences PRIMARY KEY (user_id),
    CONSTRAINT FK_user_preferences FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE collections (
  collection_id int DEFAULT nextval('seq_collection_id'::regclass) NOT NULL,
  user_id int NOT NULL,
  name varchar(45) NOT NULL,
  hidden boolean DEFAULT false,
  
  CONSTRAINT PK_collection PRIMARY KEY (collection_id),
  CONSTRAINT FK_collection_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE comics (
  comic_id int DEFAULT nextval('seq_comic_id'::regclass) NOT NULL,
  title varchar(45) NOT NULL,
  issue_num int NOT NULL,
  genre varchar(45) NOT NULL,
  universe varchar(45) DEFAULT 'Unaffiliated',
  author varchar(45) NOT NULL,
  
  CONSTRAINT PK_comic PRIMARY KEY (comic_id)
);

CREATE TABLE characters (
  character_id int DEFAULT nextval('seq_character_id'::regclass) NOT NULL,
  name varchar(45) NOT NULL,
  
  CONSTRAINT PK_character PRIMARY KEY (character_id)
);




CREATE TABLE collection_comic (
  collection_id int NOT NULL,
  comic_id int NOT NULL,
  
  CONSTRAINT PK_collection_comic PRIMARY KEY (collection_id, comic_id),
  CONSTRAINT FK_collection_comic FOREIGN KEY (collection_id) REFERENCES collections (collection_id)
--  CONSTRAINT FK_comic_collection FOREIGN KEY (comic_id) REFERENCES comics (comic_id)
);


CREATE TABLE character_comic (
  character_id int NOT NULL,
  comic_id int NOT NULL,
  
  CONSTRAINT PK_character_comic PRIMARY KEY (character_id, comic_id),
  CONSTRAINT FK_character_comic FOREIGN KEY (character_id) REFERENCES characters (character_id),
  CONSTRAINT FK_comic_character FOREIGN KEY (comic_id) REFERENCES comics (comic_id)
);



COMMIT TRANSACTION;



-- Pre-Populate
BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO user_info (user_id, is_premium, first_name, last_name, email) VALUES (1, false, 'user', 'user', 'user@teelevator.edu');
INSERT INTO user_info (user_id, is_premium, first_name, last_name, email) VALUES (2, true, 'admin', 'admin', 'admin@teelevator.edu');


INSERT INTO characters (character_id, name)
  VALUES (2502, 'Silver-Surfer');

INSERT INTO comics (comic_id, title, issue_num, genre, author)
  VALUES (45555, 'Test', 2, 'Test', 'Test');
  
INSERT INTO character_comic (character_id, comic_id)
  VALUES (2502, 45555);
  
INSERT INTO collections (collection_id, name, user_id)
  VALUES (200, 'Test', 1);
  
INSERT INTO collection_comic (collection_id, comic_id)
  VALUES (200, 45555);

COMMIT TRANSACTION;
