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

select faculties
from faculties
where faculties.id = 2;




// тестовая часть
select * from tabl where date <= '2021.03.01' and date >= '2021.01.01';

// для количества
select count(*)
from tabl
where date <= '2021.03.01' and date >= '2021.01.01';

// тотал это название для полученного значения, avg это среднее
select sum(amount) as total, max(amount) as maximum, avg(amount)
from tabl where date <= '2021.03.01' and date >= '2021.01.01';

// для того чтобы разделить по группам и взять сумму
select category, sum(amount) as amount from tabl group by categoty


select category, sum(amount) as amount
from tabl
group by categoty
having sum(amount) > 1000;

// ограниченный вывод
select * from tabl limit 4
// пропустить первые 4
select * from tabl offset 4

select count(*)
from students

select *
from students
ORDER BY id asc
offset 4

select * from avatars