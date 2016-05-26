package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.repository.EventRepository;
import pl.edu.agh.tai.model.EventInfo;


@RestController
public class EventController {

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public Iterable<Event> showEvents() {

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

    @Autowired
    private EventRepository eventRepository;
}
