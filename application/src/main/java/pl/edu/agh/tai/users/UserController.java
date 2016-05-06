package pl.edu.agh.tai.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Controller
public class UserController {



    @RequestMapping("/add")
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

 /*   @RequestMapping("/user")
    public User user(@RequestParam(value="name", defaultValue="World") String name) {
        return new User(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/user/add/{name}")
    public void addUser(@PathVariable String name) {
        User user = new User(counter.incrementAndGet(),name);
        users.add(user);
    }

    @RequestMapping("/user/{name}")
    public String getUser(@PathVariable String name) {
        return "will return proper user";

    }

    @RequestMapping("/users")
    public String showUsers() {
        return "showing all the users";
    }
*/
}
