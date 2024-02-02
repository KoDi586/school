package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer min, Integer max);
    List<Student> findAll();

    Student findFirstById(Long id);

    Student findStudentByNameAndAge(String name, int age);

    @Modifying
    @Query("UPDATE Student s SET s.id = :needId WHERE s.id = :nowId")
    void changeIdByLostId(String needId, String nowId);
}
