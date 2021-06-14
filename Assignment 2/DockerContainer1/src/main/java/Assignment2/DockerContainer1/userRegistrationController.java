package Assignment2.DockerContainer1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Controller;
        import org.springframework.stereotype.Service;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
@Service
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
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        	
        if(result > 0)
        {
            int res = service.saveUserState(user,request);

            if(res > 0)
            {
            	
            	mv.setViewName("confirmation");
                System.out.println("User state inserted successfully");
                return mv;
            }
            else
            {
                System.out.println("User state insertion failed.");
            }

            System.out.println("User inserted successfully");
        }
        else
        {
            System.out.println("User creation error");
        }
        
        mv.setViewName("registrationPage");
        return mv;
    }
    
    

}
