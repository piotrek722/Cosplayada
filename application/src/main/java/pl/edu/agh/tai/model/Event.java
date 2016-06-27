package pl.edu.agh.tai.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column
    @Lob
    private byte[] photo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    @JsonManagedReference
    private Set<Character> characterSet;

    public Event() {
        characterSet = new HashSet<>();
    }

    public Event(String name) {
        this.name = name;
        characterSet = new HashSet<>();
    }

    public Event(String name, String city, byte[] photo) {
        this.name = name;
        this.city = city;
        this.photo = photo;
        characterSet = new HashSet<>();
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

    public Set<Character> getCharacterSet() {
        return characterSet;
    }

    public void setUserSet(Set<Character> characterSet) {
        this.characterSet = characterSet;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }
}
