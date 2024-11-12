CREATE TABLE person (
  id          INTEGER AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(64) NOT NULL,
  surname VARCHAR(64) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  completed   BIT NOT NULL);

CREATE TABLE qualification (
  id          INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  description VARCHAR(200) NOT NULL);

CREATE TABLE person_qualification (
  id                INTEGER AUTO_INCREMENT PRIMARY KEY,
  person_id         INTEGER ,
  qualification_id  INTEGER ,
  completed   DATE NOT NULL,
  grade VARCHAR(64) NOT NULL);

ALTER TABLE person_qualification
    ADD FOREIGN KEY (person_id) REFERENCES person(id);

 ALTER TABLE person_qualification
    ADD FOREIGN KEY (qualification_id) REFERENCES qualification(id);