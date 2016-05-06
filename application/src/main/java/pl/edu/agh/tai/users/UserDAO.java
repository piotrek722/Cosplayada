package pl.edu.agh.tai.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    public User findByNickname(String nickname);

}