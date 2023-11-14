CREATE TABLE users  (
  user_id int AUTO_INCREMENT,
  user_name varchar(40) DEFAULT NULL,
  creation_date TIMESTAMP,
  last_modified_date TIMESTAMP,
  active boolean DEFAULT true,
  PRIMARY KEY (user_id)
);

ALTER TABLE users AUTO_INCREMENT = 1000;

CREATE TABLE games (
  game_id int AUTO_INCREMENT,
  game_name varchar(40) DEFAULT NULL,
  creation_date TIMESTAMP,
  last_modified_date TIMESTAMP,
  active boolean DEFAULT true,
  PRIMARY KEY (game_id)
);

ALTER TABLE games AUTO_INCREMENT = 1000;

CREATE TABLE score_board (
  score_id int NOT NULL AUTO_INCREMENT,
  game_id int NOT NULL,
  user_id int NOT NULL,
  score double precision(40,2),
  creation_date TIMESTAMP,
  last_modified_date TIMESTAMP,
  active boolean DEFAULT true,
  PRIMARY KEY (score_id),
  FOREIGN KEY (game_id) REFERENCES games (game_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id)
);

ALTER TABLE score_board AUTO_INCREMENT = 0;