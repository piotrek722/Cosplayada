package pl.edu.agh.tai.model;

import javax.persistence.*;

@Entity
@Table(name="characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column
    private String name;

    @Column
    private String description;

    //photo


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
}
