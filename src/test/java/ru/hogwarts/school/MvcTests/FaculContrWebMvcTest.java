package ru.hogwarts.school.MvcTests;

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
import ru.hogwarts.school.controller.FacultyController;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FaculContrWebMvcTest {
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
    private FacultyController facultyController;

    @Test
    public void getFaculty() throws Exception {
        long id = 33;
        String name = "33faculty";
        String color = "pink";
        String request = "/faculty/33";

        Faculty faculty = new Faculty(id, name, color);


        when(facultyRepository.findFirstById(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void findByNameOrColor() throws Exception {
        long id = 33;
        String name = "33faculty";
        String color = "pink";
        String request = "/faculty/findByNameOrColor?name=33faculty";
        Faculty faculty = new Faculty(id, name, color);
        List<Faculty> list = new ArrayList<>(List.of());
        list.add(faculty);

        when(facultyRepository.findFacultiesByNameIgnoreCase(any(String.class))).thenReturn(list);
        when(facultyRepository.findFacultiesByColorIgnoreCase(any(String.class))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }


    @Test
    public void getAll() throws Exception {
        long id = 33;
        String name = "33faculty";
        String color = "pink";
        String request = "/faculty/findByNameOrColor?name=33faculty";
        Faculty faculty = new Faculty(id, name, color);
        List<Faculty> list = new ArrayList<>(List.of());
        list.add(faculty);

        when(facultyRepository.findAll()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders      // localhost и тп spring сделает сам
                        .get("/faculty/getAll")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void postFacultyTest() throws Exception {
        long id = 33;
        String name = "33faculty";
        String color = "pink";

        String request = "/faculty";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders      // localhost и тп spring сделает сам
                        .post(request)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void editFaculty() throws Exception {
        long id = 33;
        String name = "33faculty";
        String color = "pink";
        String request = "/faculty";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty(id, name, color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders      // localhost и тп spring сделает сам
                        .put(request)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFaculty() throws Exception {
        long id = 33;
        String name = "33faculty";
        String color = "pink";

        String request = "/faculty/33";

        Faculty faculty = new Faculty(id, name, color);


        when(facultyRepository.findFirstById(any(Long.class))).thenReturn(faculty);

        doNothing().when(facultyRepository).delete(any(Faculty.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getStudentsByFacultyId() throws Exception {

        long facultyId = 32;
        String fName = "faculty";
        String color = "PINK";
        Faculty faculty = new Faculty(facultyId, fName, color);

        long id = 33;
        String name = "33person";
        int age = 333;
        Student student = new Student(id, name, age);
        Set<Student> set = new HashSet<>(List.of());
        set.add(student);
        faculty.setStudents(set);

        String request = "/faculty/getStudentsByFacultyId?id=33";


        when(facultyRepository.findFirstById(any(Long.class))).thenReturn(faculty);


        mockMvc.perform(MockMvcRequestBuilders
                        .get(request)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }


}
