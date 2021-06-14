package Assignment2.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import Assignment2.Model.User;
import Assignment2.service.UserService;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [2] "RestTemplate Module",  docs.spring.io [online]				   									*/
/*         https:https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html  	*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/


@RestController
public class userRegistrationController {

    @Autowired
    RestTemplate restTemplate;

    UserService service = new UserService();

    @GetMapping(value="/")
    public ModelAndView registerUser()
    {
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("registrationPage");
        return mv;
    }

    @PostMapping(value="/register")
    public ModelAndView createNewUser(@ModelAttribute("user") User user,HttpServletRequest request) throws ClassNotFoundException, SQLException
    {
        int result = service.saveUser(user);
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        mv.setViewName("registrationPage");
        
        if(result > 0)
        {
            int res = service.saveUserState(user,request);

            if(res > 0)
            {
                System.out.println("User state inserted successfully");
            }
            else
            {
                System.out.println("User state insertion failed.");
            }

            System.out.println("User inserted successfully");
            session.setAttribute("status",1);
        }
        else
        {
            System.out.println("User creation error");
            session.setAttribute("status",0);
        }
        
        mv.setViewName("registrationPage");
        
        return mv;
    }
}
