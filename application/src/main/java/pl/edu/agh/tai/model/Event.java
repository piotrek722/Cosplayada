package pl.edu.agh.tai.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private long id;

    @Column
    private String name;

    @Column
    private Date date;

    @Column
    private String city;

    @Column
    private String address;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    private Set<User> userSet;

    public Event() {
        userSet = new HashSet<>();
    }

    public Event(String name) {
        this.name = name;
        userSet = new HashSet<>();
    }

    public Event(String name, String city) {
        this.name = name;
        this.city = city;
        userSet = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}
