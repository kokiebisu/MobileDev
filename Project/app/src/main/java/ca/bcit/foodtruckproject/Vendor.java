package ca.bcit.foodtruckproject;

import java.io.Serializable;

public class Vendor implements Serializable {
    String name;
    String description;
    String type;
    String locationDescription;
    String timeStamp;
    Double longCoord;
    Double latCoord;

    int id;

    public Vendor() {}

    public Vendor(String name, String description, String type, String locationDescription,
                  String timeStamp, Double longCoord, Double latCoord, int id) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.locationDescription = locationDescription;
        this.timeStamp = timeStamp;
        this.latCoord = latCoord;
        this.longCoord = longCoord;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getType() {
        return this.type;
    }

    public String getLocationDescription() {
        return this.locationDescription;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public Double getLongCoord() {
        return this.longCoord;
    }

    public Double getLatCoord() {
        return this.latCoord;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    };

    public void setType(String type) {
        this.type = type;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }



}
