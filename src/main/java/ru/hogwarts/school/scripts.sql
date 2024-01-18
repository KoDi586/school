select * from student 

select * from student as s 
where s.age >= 10 and s.age <=30

select * from student as stu
where stu.name ilike '%A%'

select * from student as s
where s.age < 21

select s.age, s.name from student as s 
order by age

