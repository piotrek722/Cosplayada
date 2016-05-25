package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.dao.UserDAO;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;

@RestController
public class LoginController {

    @RequestMapping("/auth")
    public Boolean auth(LoginInfo loginInfo) {
        System.out.println("Logging in - name: " + loginInfo.getUsername() + " pass: " + loginInfo.getPassword());
        User user = userDAO.findByNickname(loginInfo.getUsername());
        if (user != null) {
            System.out.println("Found in db - name: " + user.getNickname() + " pass: " + user.getPassword());
            if (loginInfo.getUsername().equals(user.getNickname()) && loginInfo.getPassword().equals(user.getPassword())) {
                return true;
            }
            else {
                return false;
            }
        }
        else
            return false;
    }


    @Autowired
    private UserDAO userDAO;
}
