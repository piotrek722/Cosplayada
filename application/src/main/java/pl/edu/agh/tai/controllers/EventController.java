package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.EventRepository;
import pl.edu.agh.tai.model.EventInfo;
import pl.edu.agh.tai.repository.UserRepository;

import java.util.HashSet;


@RestController
public class EventController {

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public Iterable<Event> showEvents() {

        System.out.println("Showing events by findall:");
        System.out.println(eventRepository.findAll().toString());
        return eventRepository.findAll();
    }

    @RequestMapping(value = "/events/add")
    public Boolean addEvent(EventInfo eventInfo) {
        try {
            Event event = new Event(eventInfo.getName(), eventInfo.getCity());
            System.out.println("Event created with name: " + eventInfo.getName());
            eventRepository.save(event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/events/{id}")
    public Event showEventWithId(@PathVariable long id) {
        System.out.println("Event found: " + eventRepository.findById(id).getName());
        return eventRepository.findById(id);
    }

    @RequestMapping(value = "/events/{id}/join/{username}")
    public Boolean joinEvent(@PathVariable long id, @PathVariable String username) {
        Event event = eventRepository.findById(id);
        User user = userRepository.findByNickname(username);
        if (event != null && user != null) {
            event.getUserSet().add(user);
            user.getEvents().add(event);
            userRepository.save(user);
            System.out.println("Joined user to " + event.getName());
            return true;
        }
        return false;
    }

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;
}
