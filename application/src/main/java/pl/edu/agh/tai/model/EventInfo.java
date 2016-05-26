package pl.edu.agh.tai.model;


import java.util.Date;

public class EventInfo {

    private String name;
    private Date date;
    private String city;

    public EventInfo() {
    }

    public EventInfo(String name) {
        this.name = name;
    }

    public EventInfo(String name, String city) {
        this.name = name;
        this.city = city;
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
}
