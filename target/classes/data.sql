INSERT INTO person (id, firstname, surname, gender, completed) VALUES
  (1000,'Fred', 'Flinstone', 'M', 0),
  (1001,'Barney', 'Rubble', 'M', 0);

INSERT INTO qualification (id, name, description) values
  (9001, 'DIGGING_101','Begineers guide to digging'),
  (9002, 'DIGGING_ADV','Advanced digging'),
  (9003, 'ROCK_BRK_001','Begineers guide to rock breaking'),
  (9004, 'ROCK_BRK_ADV','Advanced rock breaking');

INSERT INTO person_qualification (id, person_id, qualification_id, completed, grade) values
  (8001, 1000, 9001, '2022-12-31', 'PASS'),
  (8002, 1000, 9002, '2023-12-31', 'PASS');
