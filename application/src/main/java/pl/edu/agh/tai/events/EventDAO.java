package pl.edu.agh.tai.events;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EventDAO extends CrudRepository<Event, Long> {

    public Event findByName(String Name);

}