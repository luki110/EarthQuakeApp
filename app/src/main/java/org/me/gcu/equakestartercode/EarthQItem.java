package org.me.gcu.equakestartercode;

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
        DateFormat dateFormat = new SimpleDateFormat("E, MMM dd yyyy");
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

    public double getMagnitude() {
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        try {
            return Double.parseDouble(split.get(4).substring(split.get(4).length() - 3));
        } catch (Exception e) {
            return 0;
        }
    }

    public double getDepth() {
        List<String> split = new ArrayList<String>(Arrays.asList(description.split(";")));
        try {
            return Double.parseDouble(split.get(3).substring(8, split.get(3).length() - 4));
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public String toString() {
        return location;
    }

    public EarthQItem(String title, String description, Date date, double latitude,
                      double longitude, String location) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;

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


    }

    private String title;
    private String description;
    private Date date;
    private double latitude;
    private double  longitude;
    private String  location;



}
