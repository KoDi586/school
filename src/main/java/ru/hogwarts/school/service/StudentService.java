package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
@Transactional
public class StudentService {
//    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
//        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }



    //CREATE
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    //READ
    public Student findStudent(Long id) {
        return studentRepository.findFirstById(id);
    }

    //UPDATE
    public Student setStudent(Student student) {
        return studentRepository.save(student);
    }

    //DELETE
    public Student removeStudent(Long id) {
        Student student = studentRepository.findFirstById(id);
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

    public Faculty getFacultyByStudentId(Long id) {
        Student student = studentRepository.findFirstById(id);
        return student.getFaculty();

    }
    //возможно пригодится и нужно потом будет переделать, не удалять!
//    public Collection<Student> filterByAge(int age) {
//        return studentMap.values().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
//    }
}
