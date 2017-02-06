package com.example.findshops.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kalpana on 05-Feb-17.
 */

public class Location {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("formattedAddress")
    @Expose
    private List<String> formattedAddress = null;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Integer getDistance() {
        return distance;
    }

    private List<String> getFormattedAddress() {
        return formattedAddress;
    }

    public String getAddress() {
        if(getFormattedAddress() == null)
            return "Address Not available";

        StringBuilder stringBuilder = new StringBuilder();
        for(String addressLine : getFormattedAddress()){
            stringBuilder.append(addressLine);
        }
        return stringBuilder.toString();
    }
}
