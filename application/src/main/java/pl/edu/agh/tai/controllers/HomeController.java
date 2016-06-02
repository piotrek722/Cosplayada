package pl.edu.agh.tai.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @RequestMapping(value="/resource")
    public Map<String, Object> home() {
        //System.out.println("User token : " + new Md5PasswordEncoder().encodePassword("user", "tai12345"));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "Hello on Cosplayada site!");
        return model;
    }

}
