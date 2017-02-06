package com.example.findshops.view;

import com.example.findshops.BasePresenter;
import com.example.findshops.BaseView;
import com.example.findshops.model.Item;

import java.util.List;

/**
 * Created by kalpana on 04-Feb-17.
 */

public interface IContract {
    interface IView extends BaseView<IPresenter>{
        void showLoading();
        void hideLoading();
        void showError(String message);
        void populateData(List<Item> itemList);
        void requestLocationUpdates();
        void showRationalForPermission();
        void startNewActivity(String url);
    }

    interface IPresenter extends BasePresenter{
        void onPermissionReceived();
        void onPermissionDenied();
        void onItemClicked(String url);
    }
}
