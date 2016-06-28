package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.Character;
import pl.edu.agh.tai.model.Event;
import pl.edu.agh.tai.model.EventInfo;
import pl.edu.agh.tai.model.Response;
import pl.edu.agh.tai.repository.CharacterRepository;
import pl.edu.agh.tai.repository.EventRepository;
import pl.edu.agh.tai.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.List;


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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
            Event event = new Event(
                    eventInfo.getName(),
                    dateFormat.format(eventInfo.getDate()),
                    eventInfo.getCity(),
                    eventInfo.getAddress(),
                    timeFormat.format(eventInfo.getTime()),
                    eventInfo.getDescription(),
                    eventInfo.getPhoto());
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
        System.out.println("Event found: " + eventRepository.findById(id));
        return eventRepository.findById(id);
    }

    @RequestMapping(value = "/events/{id}/join/{characterId}")
    public Response joinEvent(@PathVariable long id, @PathVariable long characterId) {
        System.out.println("Joining for: " + id + " character " + characterId);
        Event event = eventRepository.findById(id);
        Character character = characterRepository.findOne(characterId);
        if (event != null && character != null) {

            List<Character> characterList = characterRepository.findByUser(character.getUser());    //lista characterow usera
            for (Character findCharacter : characterList) {
                if (event.getCharacterSet().contains(findCharacter)) {
                    return new Response(false, "You already joined this event with diffrent chatacter");
                }
            }

            event.getCharacterSet().add(character);
            character.getEvents().add(event);

            characterRepository.save(character);
            System.out.println("Joined user to " + event.getName());
            return new Response(true, null);
        }
        return new Response(false, "Error");
    }

    @RequestMapping(value = "/events/{id}/characters")
    public Iterable<Character> showParticipants(@PathVariable long id) {
        Event event = eventRepository.findById(id);
        if (event != null) {
            return event.getCharacterSet();
        }
        return null;
    }

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CharacterRepository characterRepository;


}
