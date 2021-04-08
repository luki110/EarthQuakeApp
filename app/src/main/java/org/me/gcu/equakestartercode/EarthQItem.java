package org.me.gcu.equakestartercode;
//Student Id S1911301 Lukasz Bonkowski
import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EarthQItem implements Serializable {

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

    public Date getDate() {return date;}

    public String getStringDate(){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("E, MMM dd yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getMagnitude() { return magnitude; }
    public void setMagnitude(double magnitude) { this.magnitude = magnitude; }
    public double getDepth() { return depth; }

    public void setDepth(double depth) { this.depth = depth; }


    @Override
    public String toString() {
        return location;
    }

    public EarthQItem(String title, String description, Date date, double latitude,
                      double longitude, String location, double magnitude, double depth) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.magnitude = magnitude;
        this.depth = depth;

    }

    public EarthQItem(String location,  Date date) {
        this.date = date;
        this.location = location;

    }


    public EarthQItem(){
        title ="";
        description = "";
        date = null;
        latitude = 0.00;
        longitude = 0.00;
        location = "";
        magnitude = 0.00;
        depth = 0.00;


    }

    private String title;
    private String description;
    private Date date;
    private double latitude;
    private double longitude;
    private String location;
    private double magnitude;
    private double depth;

}
