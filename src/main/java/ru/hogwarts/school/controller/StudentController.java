package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(path = "/create")
    public Student createStudent(Student student) {
        return service.createStudent(student);
    }

    @GetMapping("{id}")
    public Student GetStudent(@PathVariable Long id) {
        return service.findStudent(id);
    }

    @PostMapping
    public Student PostStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return service.setStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return service.removeStudent(id);
    }

    @GetMapping(path = "/filterByAge/{age}")
    public Collection<Student> filterByAge(@PathVariable int age) {
        return service.filterByAge(age);
    }
}
