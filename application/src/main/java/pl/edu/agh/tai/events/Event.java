package pl.edu.agh.tai.events;


public class Event {

    private long id;
    private String name;

    public Event(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
