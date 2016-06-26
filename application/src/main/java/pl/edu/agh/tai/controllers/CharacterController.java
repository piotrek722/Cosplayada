package pl.edu.agh.tai.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.model.CharacterInfo;
import pl.edu.agh.tai.model.User;
import pl.edu.agh.tai.repository.CharacterRepository;
import pl.edu.agh.tai.model.Character;
import pl.edu.agh.tai.repository.UserRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


@RestController
public class CharacterController {

    @RequestMapping(value = "/characters/add")
    public Boolean create(@RequestBody CharacterInfo characterInfo) {
        System.out.println("Character to add: " + characterInfo.getName());
        try {
            User user = userRepository.findByNickname(characterInfo.getUser());
            if (user != null) {
                Character character = new Character(user, characterInfo.getName(), characterInfo.getDescription(),characterInfo.getPhoto());
                System.out.println("Adding character to user: " + user.getNickname());
                characterRepository.save(character);
            } else {
                System.out.println("cannot find user " + characterInfo.getUser());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/users/{name}/characters")
    public Iterable<Character> getUserCharacters(@PathVariable String name) {
        User user = userRepository.findByNickname(name);
        if (user != null) {
            System.out.println("Characters found: " + characterRepository.findByUser(user).toString());
            return characterRepository.findByUser(user);
        }
        else {
            System.out.println("cant fing user "+ user.getNickname());
        }
        return null;

    }

    @RequestMapping(value = "users/{name}/characters/{id}")
    public Character getCharacter(@PathVariable String name, @PathVariable long id) {
        return characterRepository.findOne(id);
    }

    @RequestMapping(value = "characters/{id}")
    public Character getCharacter(@PathVariable long id) {
        return characterRepository.findOne(id);
    }

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserRepository userRepository;
}
