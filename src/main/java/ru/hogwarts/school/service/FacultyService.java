package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //CREATE
    public Faculty addFaculty(Faculty faculty) {
        logger.info("create faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    //READ
    public Faculty findFaculty(Long id) {
        Faculty faculty = facultyRepository.findFirstById(id);
        if (faculty == null) {
            logger.warn("foundful faculty is null");
        }
        return faculty;
    }

    //UPDATE
    public Faculty setFaculty(Faculty faculty) {
        logger.info("new faculty is: {}", faculty);
        return facultyRepository.save(faculty);
    }

    //DELETE
    public Faculty removeFaculty(Long id) {
        Faculty faculty = facultyRepository.findFirstById(id);
        logger.warn("{}. this faculty will be remove", faculty);
        facultyRepository.delete(faculty);
        return faculty;
    }

    //метод для поиска по цвету либо по имени
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        if (name != null & StringUtils.isNotBlank(name)) {
            logger.debug("was found by NAME with ignore case");
            return facultyRepository.findFacultiesByNameIgnoreCase(name);
        } else if (color != null & StringUtils.isNotBlank(color)) {
            logger.debug("was found by COLOR with ignore case");
            return facultyRepository.findFacultiesByColorIgnoreCase(color);
        }
        return new ArrayList<>();
    }

    public Collection<Faculty> getAll() {
        List<Faculty> facultyRepositoryAll = facultyRepository.findAll();
        logger.info("count anything faculties - {}", facultyRepositoryAll.size());
        return facultyRepositoryAll;
    }

    public Collection<Student> getStudentsByFacultyId(Long id) {
        Faculty faculty = facultyRepository.findFirstById(id);
        Set<Student> students = faculty.getStudents();
        logger.debug("found count students: {}", students.size());
        return students;
    }

    public String getLongName() {
        List<Faculty> faculties = facultyRepository.findAll();
        int MAX;
        MAX = faculties.stream()
                .map(faculty -> faculty.getName().length())
                .max(Comparator.naturalOrder())
                .orElse(-1);

        if (MAX == -1) {
            logger.debug("факультеты не найдены");
            throw new RuntimeException("факультетов скорее всего нет!");
        }
        logger.debug("хотябы один факультет существует");

        return faculties.stream()
                .map(Faculty::getName)
                .filter(name -> name.length() == MAX)
                .findFirst().orElse("ошибка");

    }
}
