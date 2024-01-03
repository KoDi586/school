package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long incrementId = 0L;

    //CREATE
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++incrementId);
        return facultyMap.put(incrementId, faculty);
    }

    //READ
    public Faculty findFaculty(Long id) {
        if (facultyMap.containsKey(id)) {
            return facultyMap.get(id);
        } else {
            throw new RuntimeException("this is bad id!");
        }
    }

    //UPDATE
    public Faculty setFaculty(Faculty faculty) {
        return facultyMap.put(faculty.getId(), faculty);
    }

    //DELETE
    public Faculty removeFaculty(Long id) {
        if (facultyMap.containsKey(id)) {
            return facultyMap.remove(id);
        } else {
            throw new RuntimeException("this is bad id!");
        }
    }

    public Collection<Faculty> filterByColor(String color) {
        return facultyMap.values().stream()
                .filter(student -> Objects.equals(student.getColor(), color))
                .collect(Collectors.toList());
    }
}
