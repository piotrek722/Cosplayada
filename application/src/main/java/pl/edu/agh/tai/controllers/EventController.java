package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.Character;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.CharacterRepository;
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
    public Boolean addEvent(@RequestBody EventInfo eventInfo) {

        System.out.println(eventInfo.getName());
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

    @RequestMapping(value = "/events/{id}/join/{characterId}")
    public Boolean joinEvent(@PathVariable long id, @PathVariable long characterId) {
        System.out.println("Joining for: " + id + " character " + characterId);
        Event event = eventRepository.findById(id);
        Character character = characterRepository.findOne(characterId);
        if (event != null && character != null) {
            event.getCharacterSet().add(character);
            character.getEvents().add(event);

            characterRepository.save(character);
            System.out.println("Joined user to " + event.getName());
            return true;
        }
        return false;
    }

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CharacterRepository characterRepository;


}
