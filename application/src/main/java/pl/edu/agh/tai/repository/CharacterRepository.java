package pl.edu.agh.tai.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.tai.model.Character;
import pl.edu.agh.tai.model.User;

import java.sql.Blob;
import java.util.List;


@Transactional
public interface CharacterRepository extends CrudRepository<Character, Long> {

    public Character findByName(String Name);
    public List<Character> findByUser(User user);

}