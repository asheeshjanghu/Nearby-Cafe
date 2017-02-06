package com.example.findshops.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item implements Comparable<Item>{

    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("tips")
    @Expose
    private List<Tip> tips = null;

    public Venue getVenue() {
        return venue;
    }

    public List<Tip> getTips() {
        return tips;
    }

    @Override
    public int compareTo(Item item) {
        return this.getVenue().getLocation().getDistance() - item.getVenue().getLocation().getDistance();
    }
}