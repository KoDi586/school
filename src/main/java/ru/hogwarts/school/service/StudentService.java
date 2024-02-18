package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);


    @Autowired
    public StudentService(StudentRepository studentRepository) {
//        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }



    //CREATE
    public Student addStudent(Student student) {
        logger.info("Create student: {}", student);
        return studentRepository.save(student);
    }

    //READ
    public Student findStudent(Long id) {
        Student firstById = studentRepository.findFirstById(id);
        if (firstById == null) {
            logger.warn("be careful, student is null");
        }
        return firstById;
    }

    //UPDATE
    public Student setStudent(Student student) {
        Student oldStudent = studentRepository.findFirstById(student.getId());
        if (student.equals(oldStudent)) {
            logger.warn("new student equals old student");
        }
        return studentRepository.save(student);
    }

    //DELETE
    public Student removeStudent(Long id) {
        Student student = studentRepository.findFirstById(id);
        studentRepository.delete(student);
        if (studentRepository.findAllById(student.getId()).isEmpty()) {
            logger.debug("student will be remove");
        }
        return student;
    }

    // метод для поиска студентов в промежутке по возрасту
    public Collection<Student> findStudentsBetweenAge(Integer min, Integer max) {
        Collection<Student> studentCollection = studentRepository.findByAgeBetween(min, max);
        logger.info("will be found {} students", studentCollection.size());
        return studentCollection;
    }

    public Collection<Student> getAll() {
        List<Student> students = studentRepository.findAll();
        logger.info("will be found {} students", students.size());
        return students;
    }

    public Faculty getFacultyByStudentId(Long id) {
        Student student = studentRepository.findFirstById(id);
        logger.debug("fount student is: {}", student);
        return student.getFaculty();

    }

    public int getCountAllStudents() {
        int countAllStudents = studentRepository.getCountAllStudents();
        logger.info("found student s count: {}", countAllStudents);
        return countAllStudents;
    }

    public float getAverageAge() {
        float averageAge = studentRepository.getAverageAge();
        logger.debug("found average age without error");
        return averageAge;
    }

    public Collection<Student> getLastFiveStudents() {
        int count = studentRepository.getCountAllStudents();
        List<Student> lastFiveStudents = studentRepository.getLastFiveStudents(count - 5);
        logger.debug("last five students is: {}", lastFiveStudents.toString());
        return lastFiveStudents;
    }

    //возможно пригодится и нужно потом будет переделать, не удалять!
//    public Collection<Student> filterByAge(int age) {
//        return studentMap.values().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
//    }
}
