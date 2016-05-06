package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.dao.EventDAO;


@Controller
public class EventController {

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    @ResponseBody
    public String showEvents() {

        return eventDAO.findAll().toString();
    }

    @RequestMapping(value = "/events/add", method = RequestMethod.POST)
    @ResponseBody
    public String addEvent(String name) {
        try {
            Event event = new Event(name);
            eventDAO.save(event);

        }
        catch (Exception ex) {
            return "Error creating the event: " + ex.toString();
        }
        return "Event created" ;
    }

    @Autowired
    private EventDAO eventDAO;
}
