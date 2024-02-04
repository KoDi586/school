package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {

    @LocalServerPort
    private int port;
    //после запуска приложения будет добавлен созданный порт

    @Autowired
    private StudentController studentController;
    // его будем проверять в первом тесте

    @Autowired
    private TestRestTemplate restTemplate;

//    private String urlRequest;
//
//    public void initialUrl() {
//        urlRequest = String.format("http://localhost:%d/student", port);
//    }
    // класс, который поможет осуществлять проверки

    @Test
    public void getAllTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                        (
                                "http://localhost:" + port + "/student/getAll", String.class
                        ))
                .isNotNull();
    }


    @Test
    public void getByIdTest() throws Exception {
        long id = 502;
        Assertions.assertThat(this.restTemplate.getForObject
                        (
                                "http://localhost:" + port + "/student/" + id, String.class
                        ))
                .isNotNull();
    }

    @Test
    public void getBetweenAgeTest() throws Exception {
        int minAge = 1;
        int maxAge = 100;
        Assertions.assertThat(this.restTemplate.getForObject
                        (
                                "http://localhost:" + port + "/student/betweenAge?min=" + minAge + "&max=" + maxAge, String.class
                        ))
                .isNotNull();
        // в swagger метод работает нормально
    }


    @Test
    public void getStudentFacultyTest() throws Exception {
        int studentsId = 502;
        Assertions.assertThat(this.restTemplate.getForObject
                        (
                                "http://localhost:" + port + "/student/getFacultyByStudentId?id=" + studentsId, String.class
                        ))
                .isNotNull();
    }
    // тест метода выдачи факультета студента

    @Test
    public void postStudentTest() throws Exception {
        Student student = new Student(110L, "Herobrin", 112);
        Assertions.assertThat(this.restTemplate.postForObject
                        (
                                "http://localhost:" + port + "/student",
                                student,
                                String.class
                        ))
                .isNotNull();
    }
    // создан тестовый студент

    @Test
    public void editStudentTest() throws Exception {
        long youStudentId = 552;
        Student student = new Student(youStudentId, "Herobrin", 228);
        HttpEntity<Student> request = new HttpEntity<>(student);
        Assertions.assertThat(
                        this.restTemplate.exchange
                                (
                                        "http://localhost:" + port + "/student", HttpMethod.PUT, request, String.class
                                ))
                .isNotNull();
    }
    // данные созданного студента изменены

    @Test
    public void deleteStudent() throws Exception {
        Long studentId = 552L;
//        HttpEntity<Long> request = new HttpEntity<>(studentId);
        this.restTemplate.delete
                        (
                                "http://localhost:" + port + "/student/" + studentId
                        );
    }
    // ранее созданный студент удален


}
