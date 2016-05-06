package pl.edu.agh.tai.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.tai.model.Event;

@Transactional
public interface EventDAO extends CrudRepository<Event, Long> {

    public Event findByName(String Name);

}