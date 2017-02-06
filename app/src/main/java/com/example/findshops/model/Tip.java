package com.example.findshops.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by kalpana on 05-Feb-17.
 */

public class Tip {

    @SerializedName("canonicalUrl")
    @Expose
    public String canonicalUrl;

    public String getCanonicalUrl() {
        return canonicalUrl;
    }
}
