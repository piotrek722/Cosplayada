package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.LoginInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.UserRepository;
import pl.edu.agh.tai.security.ILibraryService;
import pl.edu.agh.tai.security.LibraryException;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

//    @RequestMapping(value="/user")
//    public Principal user(Principal user) {
//        return user;
//    }


    @Autowired
    private ILibraryService libraryService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/user/add", method = RequestMethod.POST)
    public Boolean create(@RequestBody LoginInfo userinfo) {
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

    @RequestMapping(path = "/user", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRole() throws LibraryException {
        // dummy method
        Map<String, Object> retMap = new HashMap<String, Object>();

        retMap.put("role", libraryService.getRole());

        ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String,Object>>(retMap, HttpStatus.OK);
        return retValue;
    }

}
