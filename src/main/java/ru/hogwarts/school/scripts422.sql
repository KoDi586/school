SELECT id, age, name, avatar_id, faculty_id
FROM students;

CREATE TABLE persons (
    id INT PRIMARY KEY ,
    name VARCHAR(255) unique NOT NULL,
    age INT NOT NULL,
    hasrights BOOLEAN NOT NULL,
    car_id INT REFERENCES cars (id)
);

CREATE TABLE cars (
    id INT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    cost INT NOT NULL
);
