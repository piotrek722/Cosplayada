package pl.edu.agh.tai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CHARACTER_ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonBackReference
    private User user;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String photo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "character_events", joinColumns = {
            @JoinColumn(name = "CHARACTER_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "EVENT_ID",
                    nullable = false, updatable = false) })
    @JsonBackReference
    private Set<Event> events;



    public Character() {
    }

    public Character(String name) {
        this.name = name;
    }

    public Character(User user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public Character(User user, String name, String description, String photo) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
