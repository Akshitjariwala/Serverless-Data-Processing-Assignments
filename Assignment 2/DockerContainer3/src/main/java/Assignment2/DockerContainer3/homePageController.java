package Assignment2.DockerContainer3;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/

@RestController
public class homePageController {

    ApplicationService service = new ApplicationService();

    @GetMapping(value="/home")
    public ArrayList<String> homePage(ModelMap model) throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list = service.fetchOnlineUsers();
        return list;
    }
}
