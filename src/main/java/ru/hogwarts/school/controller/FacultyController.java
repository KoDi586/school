package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {

    private FacultyService service;

    @Autowired
    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public Faculty GetFaculty(@PathVariable Long id) {
        return service.findFaculty(id);
    }

    @PostMapping
    public Faculty PostFaculty(@RequestBody Faculty faculty) {
        return service.addFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return service.setFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return service.removeFaculty(id);
    }

//    @GetMapping(path = "/filterByColor/{color}")
//    public Collection<Faculty> filterByColor(@PathVariable String color) {
//        return service.filterByColor(color);
//    }
}
