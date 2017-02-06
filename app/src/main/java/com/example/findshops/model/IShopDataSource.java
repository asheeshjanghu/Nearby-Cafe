package com.example.findshops.model;

import java.util.List;

/**
 * Created by kalpana on 05-Feb-17.
 */

public interface IShopDataSource {

    interface LoadCallback{
        void onDataLoaded(List<Item> items);
        void onDataLoadFailed();
    }

    void getShopsFromNetwork(android.location.Location location, LoadCallback callback);
}
