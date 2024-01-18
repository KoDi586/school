package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    //метод для теста работы с репозиторием потом удалить
//    @PostMapping(path = "/create")
//    public Student createStudent(Student student) {
//        return service.createStudent(student);
//    }

    @PostMapping
    public Student PostStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }


    @GetMapping(path = "/getAll")
    public Collection<Student> getAllStudents() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public Student GetStudent(@PathVariable Long id) {
        return service.findStudent(id);
    }

    @GetMapping(path = "/betweenAge")
    public ResponseEntity<Collection<Student>> findBetweenAge(@RequestParam Integer min,
                                                              @RequestParam Integer max) {
        return ResponseEntity.ok(service.findStudentsBetweenAge(min, max));
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return service.setStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return service.removeStudent(id);
    }

//    @GetMapping(path = "/filterByAge/{age}")
//    public Collection<Student> filterByAge(@PathVariable int age) {
//        return service.filterByAge(age);
//    }
}
