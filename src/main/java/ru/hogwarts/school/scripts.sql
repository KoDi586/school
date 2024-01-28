select * from student 

select * from student as s 
where s.age >= 10 and s.age <=30

select * from student as stu
where stu.name ilike '%A%'

select * from student as s
where s.age < 21

select s.age, s.name from student as s 
order by age

UPDATE students
SET avatar_id = 3
WHERE students.id = 3;

UPDATE faculty
SET id = 4
WHERE faculty."id" = 54;

update avatars
set id = 4
where avatars.id = 352

select a.id from avatars as a

update students
set age = 100
where students.name = 'aleks' and students.age = 83;

