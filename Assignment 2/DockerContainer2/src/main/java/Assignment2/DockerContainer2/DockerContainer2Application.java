package Assignment2.DockerContainer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [2] "RestTemplate Module",  docs.spring.io [online]				   									*/
/*         https:https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html  	*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/

@SpringBootApplication
public class DockerContainer2Application {

	public static void main(String[] args) {
		SpringApplication.run(DockerContainer2Application.class, args);
	}
	
	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
