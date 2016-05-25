package pl.edu.agh.tai.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.LoginInfo;

@RestController
public class LoginController {

    @RequestMapping("/auth")
    public Boolean auth(LoginInfo loginInfo) {
        if (loginInfo.getUsername().equals("user2"))
            return true;
        else
            return false;
    }

//    @RequestMapping("/logout")
//    public Boolean logout(String username) {
//        return true;
//    }
}
