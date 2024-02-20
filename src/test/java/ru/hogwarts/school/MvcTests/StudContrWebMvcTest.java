package ru.hogwarts.school.MvcTests;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest//тестируем только часть приложения
public class StudContrWebMvcTest {

    @Autowired
    private MockMvc mockMvc;    // для создания моков?

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
        String request = "/student";

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders      // localhost и тп spring сделает сам
                        .post(request)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }


    @Test
    public void getAllStudentsTest() throws Exception {
        long id = 33;
        String name = "33person";
        int age = 333;

        List<Student> students = new ArrayList<>(List.of());
        students.add(new Student(id, name, age));

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders      // localhost и тп spring сделает сам
                        .get("/student/getAll")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void getById() throws Exception {
        long id = 33;
        String name = "33person";
        int age = 333;
        String request = "/student/33";

        Student student = new Student(id, name, age);


        when(studentRepository.findFirstById(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }


    @Test
    public void getBetweenAge() throws Exception {
        long id = 33;
        String name = "33person";
        int age = 333;
        String request = "/student/betweenAge?min=30&max=40";

        List<Student> students = new ArrayList<>(List.of());
        students.add(new Student(id, name, age));


        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void editStudent() throws Exception {
        long id = 33;
        String name = "33person";
        int age = 333;
        String request = "/student";

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders      // localhost и тп spring сделает сам
                        .put(request)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
    @Test
    public void deleteStudent() throws Exception {
        long id = 33;
        String name = "33person";
        int age = 333;
        String request = "/student/33";

        Student student = new Student(id, name, age);


        when(studentRepository.findFirstById(any(Long.class))).thenReturn(student);

        doNothing().when(studentRepository).delete(any(Student.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getFacultyByStudentId() throws Exception {
        long facultyId = 32;
        String fName = "faculty";
        String color = "PINK";
        Faculty faculty = new Faculty(facultyId, fName, color);

        long id = 33;
        String name = "33person";
        int age = 333;
        Student student = new Student(id, name, age);
        student.setFaculty(faculty);

        String request = "/student/getFacultyByStudentId?id=33";


        when(studentRepository.findFirstById(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(fName))
                .andExpect(jsonPath("$.color").value(color));
    }
}
