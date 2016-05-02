package pl.edu.agh.tai.users;


public class User {


    private long id;
    private String nickname;


    public User(long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public long getId() {
        return id;
    }


}
