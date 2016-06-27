package pl.edu.agh.tai.model;


import java.util.Date;

public class EventInfo {

    private String name;
    private Date date;
    private String city;
    private String address;
    private Date time;
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

    public EventInfo(String name, Date date, String city) {
        this.name = name;
        this.date = date;
        this.city = city;
    }

    public EventInfo(String name, Date date, String city, String address, Date time,byte[] photo) {
        this.name = name;
        this.date = date;
        this.city = city;
        this.address = address;
        this.time = time;
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

    public String getAddress() {
        return address;
    }

    public Date getTime(){
        return time;
    }

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }
}
