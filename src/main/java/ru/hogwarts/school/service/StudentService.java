package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.testRepository.TestRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final TestRepository testRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(TestRepository testRepository, StudentRepository studentRepository) {
        this.testRepository = testRepository;
        this.studentRepository = studentRepository;
    }

//    public Student createStudent(Student student) {
//        return testRepository.save(student);
//    }


    //CREATE
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    //READ
    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    //UPDATE
    public Student setStudent(Student student) {
        return studentRepository.save(student);
    }

    //DELETE
    public Student removeStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        studentRepository.delete(student);
        return student;
    }

    // метод для поиска студентов в промежутке по возрасту
    public Collection<Student> findStudentsBetweenAge(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    //возможно пригодится и нужно потом будет переделать, не удалять!
//    public Collection<Student> filterByAge(int age) {
//        return studentMap.values().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
//    }
}
