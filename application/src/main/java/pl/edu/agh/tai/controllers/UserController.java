package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.dao.UserDAO;


@RestController
public class UserController {


    @RequestMapping(value="/users/add")
    public Boolean create(LoginInfo userinfo) {
        try {
            User user = new User(userinfo.getUsername(), userinfo.getPassword());
            System.out.println("User created - name: " + user.getNickname() + " pass: " + user.getPassword());
            userDAO.save(user);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Autowired
    private UserDAO userDAO;

}
