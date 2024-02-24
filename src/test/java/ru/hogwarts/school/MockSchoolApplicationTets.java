package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest//тестируем только часть приложения
public class MockSchoolApplicationTets {

    @Autowired
    private MockMvc mockMvc;// для создания моков?

    @MockBean // будем его мокать
    private StudentRepository studentRepository;

    @MockBean // будем его мокать
    private AvatarRepository avatarRepository;

    @MockBean // будем его мокать
    private FacultyRepository facultyRepository;

    @SpyBean //он нужен без его изменения
    private StudentService studentService;

    @SpyBean //он нужен без его изменения
    private AvatarService avatarService;

    @SpyBean //он нужен без его изменения
    private FacultyService facultyService;

    @InjectMocks //будем с ним работать
    private StudentController studentController;

    @Test
    public void saveStudentTest() throws Exception {
        long id = 33;
        String name = "33person";
        int age = 333;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student")
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.age").value(age));
    }
}
