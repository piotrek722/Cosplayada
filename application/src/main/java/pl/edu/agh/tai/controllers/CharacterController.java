package pl.edu.agh.tai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.tai.repository.CharacterRepository;
import pl.edu.agh.tai.model.Character;

@Controller
public class CharacterController {

    @RequestMapping(value = "/characters/add", method = RequestMethod.POST)
    @ResponseBody
    public String create(String name) {
        try {
            Character character = new Character(name);
            characterRepository.save(character);
        }
        catch (Exception ex) {
            return "Error creating the character: " + ex.toString();
        }
        return "Character created";
    }

    @Autowired
    private CharacterRepository characterRepository;
}
