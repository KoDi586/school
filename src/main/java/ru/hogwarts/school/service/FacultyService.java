package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacultyService {
//    private Map<Long, Faculty> facultyMap = new HashMap<>();
//    private Long incrementId = 0L;
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //CREATE
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
//        faculty.setId(++incrementId);
//        return facultyMap.put(incrementId, faculty);
    }

    //READ
    public Faculty findFaculty(Long id) {
        return facultyRepository.findFirstById(id);
//        if (facultyMap.containsKey(id)) {
//            return facultyMap.get(id);
//        } else {
//            throw new RuntimeException("this is bad id!");
//        }
    }

    //UPDATE
    public Faculty setFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
//        return facultyMap.put(faculty.getId(), faculty);
    }

    //DELETE
    public Faculty removeFaculty(Long id) {
        Faculty faculty = facultyRepository.findFirstById(id);
        facultyRepository.delete(faculty);
        return faculty;
    }

    //метод для поиска по цвету либо по имени
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        if (name != null & StringUtils.isNotBlank(name)) {
            return facultyRepository.findFacultiesByNameIgnoreCase(name);
        } else if (color != null & StringUtils.isNotBlank(color)) {
            return facultyRepository.findFacultiesByColorIgnoreCase(color);
        }
        return new ArrayList<>();
    }

    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Collection<Student> getStudentsByFacultyId(Long id) {
        int count = facultyRepository.findFirstById(id).getStudents().size();
        Faculty faculty = facultyRepository.findFirstById(id);
//        Faculty faculty1 = facultyRepository.fi
        return faculty.getStudents();
    }

//    public Collection<Faculty> filterByColor(String color) {
//        return facultyMap.values().stream()
//                .filter(student -> Objects.equals(student.getColor(), color))
//                .collect(Collectors.toList());
//    }
}
