package Assignment2.DockerContainer3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/

@SpringBootApplication
public class DockerContainer3Application {

	public static void main(String[] args) {
		SpringApplication.run(DockerContainer3Application.class, args);
	}
	
	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
