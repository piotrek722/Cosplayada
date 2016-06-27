package pl.edu.agh.tai.model;


import java.util.Date;

public class EventInfo {

    private String name;
    private Date date;
    private String city;
    private byte[] photo;

    public EventInfo() {
    }

    public EventInfo(String name) {
        this.name = name;
    }

    public EventInfo(String name, String city,byte[] photo) {
        this.name = name;
        this.city = city;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }
}
