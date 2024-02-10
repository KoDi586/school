SELECT id, age, name, avatar_id, faculty_id
FROM students;

select students.name, students.age, faculties.name
from students inner join faculties on students.faculty_id = faculties.id

select students.name, students.age, faculties.name
from students inner join faculties on students.faculty_id = faculties.id
where students.avatar_id is not null