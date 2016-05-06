package pl.edu.agh.tai.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.tai.model.User;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    public User findByNickname(String nickname);

}