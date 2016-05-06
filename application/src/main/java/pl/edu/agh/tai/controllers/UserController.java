package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.dao.UserDAO;


@Controller
public class UserController {


    @RequestMapping("/users/add")
    @ResponseBody
    public String create(String name) {
        String userId = "";
        try {
            User user = new User(name);
            userDAO.save(user);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created with id = " + userId;
    }

    @Autowired
    private UserDAO userDAO;

}
