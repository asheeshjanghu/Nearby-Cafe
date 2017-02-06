package com.example.findshops.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("name")
    @Expose
    public String name;

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
