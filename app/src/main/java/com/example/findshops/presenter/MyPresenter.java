package com.example.findshops.presenter;

import android.location.Location;

import com.example.findshops.model.IShopDataSource;
import com.example.findshops.model.Item;
import com.example.findshops.model.ShopRepository;
import com.example.findshops.view.IContract;

import java.util.List;

/**
 * Created by kalpana on 04-Feb-17.
 */

public class MyPresenter  implements IContract.IPresenter{

    private IContract.IView view;
    private IShopDataSource shopDataSource;

    public MyPresenter(IContract.IView view, ShopRepository shopRepository) {
        this.view = view;
        this.shopDataSource = shopRepository;
        view.setPresenter(this);
    }

    @Override
    public void start(Location location) {
        //show waiting message
        view.showLoading();
        // load the shops
        loadShops(location);
    }

    private void loadShops(Location location) {
        shopDataSource.getShopsFromNetwork(location, new IShopDataSource.LoadCallback() {

            @Override
            public void onDataLoaded(List<Item> items) {
                view.hideLoading();
                view.populateData(items);
            }

            @Override
            public void onDataLoadFailed() {
                view.hideLoading();
            }
        });
    }

    @Override
    public void onPermissionReceived() {
        view.requestLocationUpdates();
    }

    @Override
    public void onPermissionDenied() {
        view.showRationalForPermission();
    }

    @Override
    public void onItemClicked(String url) {
        view.startNewActivity(url);
    }
}
