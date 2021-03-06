package pl.edu.agh.tai.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.tai.model.Event;

@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {

    public Event findByName(String Name);
    public Event findById(long id);

}