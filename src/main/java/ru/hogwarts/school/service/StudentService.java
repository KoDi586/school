package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
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

        studentRepository.save(student);
        Student createfulStudent = studentRepository.findStudentByNameAndAge(student.getName(), student.getAge());

        if (createfulStudent == null) {
            throw new RuntimeException("ошибка при поиске");
        }
        System.out.println("first point");

        String needId = student.getId().toString();
        String nowId = createfulStudent.getId().toString();

        if (needId == null || nowId == null || needId == nowId) {
            throw new RuntimeException("ошибка при поиске индексов");
        }
        System.out.println("second point");
        studentRepository.changeIdByLostId(needId, nowId);
        return student;
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

    public Faculty getFacultyByStudentId(Long id) {
        Student student = studentRepository.findStudentById(id);
        return student.getFaculty();
    }
    //возможно пригодится и нужно потом будет переделать, не удалять!
//    public Collection<Student> filterByAge(int age) {
//        return studentMap.values().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
//    }
}
