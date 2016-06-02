package pl.edu.agh.tai.model;


public class CharacterInfo {

    private String user;
    private String name;
    private String description;
    private String photo;


    public CharacterInfo() {
    }

    public CharacterInfo(String user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public CharacterInfo(String user, String name, String description, String photo) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
