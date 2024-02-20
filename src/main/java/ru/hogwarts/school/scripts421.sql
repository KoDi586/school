delete from students
where age < 16

alter table students add CONSTRAINT age check (age >= 16)

delete from students
where name is null;

select name
from students
group by name
having count(name) > 1;
--найденно имя Alex, оно повтаряется

delete
from students
where name = 'Alex'
-- удалил его и все его повторения
insert into students(name, id, age)
values ('Alex', 83, 29)
--добавил
select count(*)
from students
where name = 'Alex'
-- проверил на одно присутствие


alter table students alter column name set not null
--сделал сначала чтобы небыло равно null
alter table students add constraint uniqueal unique (name)
-- затем сделал уникальным


alter table faculties add constraint name_and_color_unique unique (name, color)

alter table students
alter column age
set default 20
-- изменил default age студентов
