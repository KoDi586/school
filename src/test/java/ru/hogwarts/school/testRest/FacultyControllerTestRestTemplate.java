package ru.hogwarts.school.testRest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;

import java.lang.reflect.Executable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {

    @LocalServerPort
    private int port;
    //после запуска приложения будет добавлен созданный порт

    @Autowired
    private StudentController studentController;
    // его будем проверять в первом тесте

    @Autowired
    private TestRestTemplate restTemplate;
    // класс, который поможет осуществлять проверки

    @Test
    public void getFacultyTest() throws Exception {
        long facultyId = 1;
        Assertions.assertThat(this.restTemplate.getForObject
                        (
                                "http://localhost:" + port + "/faculty/" + facultyId, String.class
                        ))
                .isNotNull();
    }
    //получил нужный факультет без ошибок

    @Test
    public void findByNameOrColorTest() throws Exception {
        HttpEntity<String> request = new HttpEntity<>("yello");
        Assertions.assertThat(this.restTemplate.exchange
                (
                        "http://localhost:" + port + "/faculty/findByNameOrColor", HttpMethod.GET,request,String.class
                        )).isNotNull();
    }
    // проверил получение студента по другому параметру

    @Test
    public void getAllFacultiesTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                (
                        "http://localhost:" + port + "/faculty/getAll", String.class
                )).isNotNull();
    }
    //проверил получение всех факультетов

    @Test
    public void getStudentsByFacultyId() throws Exception {
        long facultyId = 1;
        Assertions.assertThat(this.restTemplate.getForObject
                (
                        "http://localhost:" + port + "/faculty/getStudentsByFacultyId?id=" + facultyId, String.class
                )).isNotNull();
    }
    // проверил получения студентов факультета

    @Test
    public void postFacultyTest() throws Exception {
        Faculty faculty = new Faculty(11L, "RedUniversity", "red");
        Assertions.assertThat(this.restTemplate.postForEntity
                (
                        "http://localhost:" + port + "/faculty", faculty, String.class
                ))
                .isNotNull();
    }
    // проверил создание факультета

    @Test
    public void editFacultyTest() throws Exception {
        Long facultyId = 52L; //id только что созданного факультета
        Faculty faculty = new Faculty(facultyId, "BlackUniversity", "black");
        HttpEntity<Faculty> request = new HttpEntity<>(faculty);
        Assertions.assertThat(this.restTemplate.exchange
                (
                        "http://localhost:" + port + "/faculty", HttpMethod.PUT, request, String.class
                )).isNotNull();
    }
    // раннее созданный факультет изменился

    @Test
    public void deleteFacultyTest() throws Exception {
        long facultyId = 52; // тот же что и сверху
        this.restTemplate.delete
                (
                        "http://localhost:" + port + "/faculty/" + facultyId
                );
    }
    //ранее созданный факультет успешно удален


}
