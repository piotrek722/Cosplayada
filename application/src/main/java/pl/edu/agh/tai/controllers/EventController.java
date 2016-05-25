package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.dao.EventRepository;


@Controller
public class EventController {

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    @ResponseBody
    public String showEvents() {

        return eventRepository.findAll().toString();
    }

    @RequestMapping(value = "/events/add", method = RequestMethod.POST)
    @ResponseBody
    public String addEvent(String name) {
        try {
            Event event = new Event(name);
            eventRepository.save(event);

        }
        catch (Exception ex) {
            return "Error creating the event: " + ex.toString();
        }
        return "Event created" ;
    }

    @Autowired
    private EventRepository eventRepository;
}
