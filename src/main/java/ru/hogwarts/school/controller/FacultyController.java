package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Faculty getFaculty(@PathVariable Long id) {
        return service.findFaculty(id);
    }

    @GetMapping(path = "/findByNameOrColor")
    public Collection<Faculty> findByNameOrColor(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String color) {
        return service.findByNameOrColor(name, color);
    }

    @GetMapping(path = "/getAll")
    public Collection<Faculty> getAllFaculties() {
        return service.getAll();
    }

    @PostMapping
    public Faculty postFaculty(@RequestBody Faculty faculty) {
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

    @GetMapping(path = "/getStudentsByFacultyId")
    public Collection<Student> getStudentsByFacultyId(Long id) {
        return service.getStudentsByFacultyId(id);
    }

    @GetMapping("name/long")
    public ResponseEntity<String> getLongName() {
        return ResponseEntity.ok(service.getLongName());
    }
    // не удалять потом пригодится
//    @GetMapping(path = "/filterByColor/{color}")
//    public Collection<Faculty> filterByColor(@PathVariable String color) {
//        return service.filterByColor(color);
//    }
}
