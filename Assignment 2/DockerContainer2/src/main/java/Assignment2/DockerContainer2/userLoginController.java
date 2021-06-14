package Assignment2.DockerContainer2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [2] "RestTemplate Module",  docs.spring.io [online]				   									*/
/*         https:https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html  	*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/


@RestController
public class userLoginController {

    ValidationService service = new ValidationService();

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/login")
    public ModelAndView userLogin(HttpServletRequest request) {

        if (request.getSession(false) == null) {
            
        	ModelAndView mv = new ModelAndView();
            
        	mv.setViewName("userLogin");
    		
        	return mv;
        } else 
        {
        	ModelAndView mv = new ModelAndView();
        	
        	mv.setViewName("homePage");
    		
        	return mv;
        }

    }

    @GetMapping(value="/userList")
    public ArrayList<String> loginuserlist()
    {
    	ArrayList<String> list = service.fetchOnlineUsers(restTemplate);
        
    	return list;
    }

    @RequestMapping(value = "/validate")
    public ModelAndView validateUser(@ModelAttribute("user") User user,HttpServletRequest request)
            throws ClassNotFoundException, SQLException, ServletException, IOException {
        Boolean result = service.validateUser(user);
        
        request.getSession().setAttribute("userName", user.getUserEmail());

        if (result == true)
        {	
        	service.updateUserState(user, request);
        	
        	ArrayList<String> list = loginuserlist();
        	
        	ModelAndView mv = new ModelAndView();
        	
        	mv.setViewName("homePage");
        	
        	mv.addObject("lists", list);
        	
        	return mv;
        } 
        else 
        {
        	ModelAndView mv = new ModelAndView();
        	
        	mv.setViewName("userLogin");
            
        	return mv;
        }

    }
    
    @PostMapping(value="/logout")
    public ModelAndView loggedOut(HttpServletRequest request) throws ClassNotFoundException, SQLException
    {
        
    	service.makeUserOffline(request);
        
    	ModelAndView mv = new ModelAndView();
        
    	mv.setViewName("userLogin");
        
    	request.getSession().invalidate();
        
    	return mv;
    }
}

