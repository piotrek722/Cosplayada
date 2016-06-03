package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.UserRepository;
import pl.edu.agh.tai.services.IUserService;
import pl.edu.agh.tai.util.UserException;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value="/user/add", method = RequestMethod.POST)
    public Boolean create(@RequestBody LoginInfo userinfo) {
        boolean answer = false;
        try {
           answer = userService.addUser(userinfo);
        }
        catch (Exception ex) {
            return false;
        }
        return answer;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRole() throws UserException {
        // dummy method
        Map<String, Object> retMap = new HashMap<String, Object>();

        retMap.put("role", userService.getRole());

        ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String,Object>>(retMap, HttpStatus.OK);
        return retValue;
    }

    @RequestMapping(value = "/users/{name}")
    public User getUserId(@PathVariable String name) {
        System.out.println("Getting user profile" + name);
        return userRepository.findByNickname(name);
    }

    @Autowired
    private UserRepository userRepository;

}
