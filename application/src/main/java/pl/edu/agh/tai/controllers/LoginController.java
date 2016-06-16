package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.Response;
import pl.edu.agh.tai.repository.UserRepository;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;

@RestController
public class LoginController {

    @RequestMapping("/auth")
    public Response auth(LoginInfo loginInfo) {
        System.out.println("Logging in - name: " + loginInfo.getUsername() + " pass: " + loginInfo.getPassword());
        User user = userRepository.findByNickname(loginInfo.getUsername());
        if (user != null) {
            System.out.println("Found in db - name: " + user.getNickname() + " pass: " + user.getPassword());
            if (loginInfo.getUsername().equals(user.getNickname()) && loginInfo.getPassword().equals(user.getPassword())) {
                return new Response(true, null);
            }
            else {
                return new Response(false, "Invalid password");
            }
        }
        else
            return new Response(false, "User does not exist.");
    }


    @Autowired
    private UserRepository userRepository;
}
