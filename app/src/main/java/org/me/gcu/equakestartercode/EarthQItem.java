package org.me.gcu.equakestartercode;

import java.util.Date;

public class EarthQItem {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return location + magnitude;
    }

    public EarthQItem(String title, String description, String date, String latitude,
                      String longitude, String location, String magnitude) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.magnitude = magnitude;
    }


    public EarthQItem(){
        title ="";
        description = "";
        date = "";
        latitude = "";
        longitude = "";
        location = "";
        magnitude = "";

    }

    private String title;
    private String description;
    private String date;
    private String latitude;
    private String longitude;
    private String location;
    private String magnitude;


}
