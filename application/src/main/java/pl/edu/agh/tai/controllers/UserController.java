package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.UserRepository;

import java.security.Principal;


@RestController
public class UserController {

//    @RequestMapping(value="/user")
//    public Principal user(Principal user) {
//        return user;
//    }

    @RequestMapping(value="/users/add")
    public Boolean create(LoginInfo userinfo) {
        try {
            User user = new User(userinfo.getUsername(), userinfo.getPassword());
            System.out.println("User created - name: " + user.getNickname() + " pass: " + user.getPassword());
            userRepository.save(user);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }


    @RequestMapping(value = "/users/{name}")
    public User getUserId(@PathVariable String name) {
        System.out.println("Getting user profile" + name);
        return userRepository.findByNickname(name);
    }

    @Autowired
    private UserRepository userRepository;

}
