package pl.edu.agh.tai.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column
    private String nickname;

    @Column
    private String password;

 //   @OneToMany(mappedBy = "user")     //cannot be mapped yet
 //   private Set<Character> characters;

    public User() {    }

    public User(long id, String nickname) {
        this.userId =id;
        this.nickname = nickname;
    }


    public User(String nickname) {
        this.nickname = nickname;
    }

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public long getId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

 /*   public Set<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }
*/
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
