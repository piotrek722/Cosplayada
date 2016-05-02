package pl.edu.agh.tai.events;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EventsController {

    @RequestMapping("/events")
    public String showEvents() {
        return "showing all the events";
    }
}
