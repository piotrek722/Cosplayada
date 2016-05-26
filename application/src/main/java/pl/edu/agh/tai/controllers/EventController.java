package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.dao.EventRepository;
import pl.edu.agh.tai.model.EventInfo;

import java.util.ArrayList;


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
            return false;
        }
        return true;
    }

    @Autowired
    private EventRepository eventRepository;
}
