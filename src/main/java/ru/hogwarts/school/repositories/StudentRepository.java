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
    @Query(value = "UPDATE Student s SET s.id = :needId WHERE s.id = :nowId", nativeQuery = true)
    void changeIdByLostId(String needId, String nowId);

    @Query(value = "select count(*)" +
            " from students ", nativeQuery = true)
    int getCountAllStudents();

    @Query(value = "select avg(age) " +
            "from students", nativeQuery = true)
    float getAverageAge();

    @Query(value = "select *\n" +
            "from students\n" +
            "ORDER BY id asc\n" +
            "offset :minusFive", nativeQuery = true)
    List<Student> getLastFiveStudents(int minusFive);
}
