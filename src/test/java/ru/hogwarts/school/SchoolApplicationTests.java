package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

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
	void contextLoads() throws Exception{
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	public void testDefaultMassage() {
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/check", String.class)).isEqualTo("hello");
	}

//здесь будет тест с моком
	{

	}
}
