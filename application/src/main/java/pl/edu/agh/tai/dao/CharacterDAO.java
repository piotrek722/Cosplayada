package pl.edu.agh.tai.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.tai.model.Character;


@Transactional
public interface CharacterDAO extends CrudRepository<Character, Long> {

    public Character findByName(String Name);

}