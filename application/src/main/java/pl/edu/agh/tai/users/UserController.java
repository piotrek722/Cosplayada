package pl.edu.agh.tai.users;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private List<User> users = new LinkedList<>();



    @RequestMapping("/user")
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
}
