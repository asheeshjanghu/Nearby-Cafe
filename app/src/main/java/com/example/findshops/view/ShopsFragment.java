package com.example.findshops.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findshops.R;
import com.example.findshops.model.Item;
import com.example.findshops.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by kalpana on 05-Feb-17.
 * View class responsible for updating the view
 * this class should not handle any other part other than view updates
 */

public class ShopsFragment extends Fragment implements ClickListener, IContract.IView {
    private static final String TAG = ShopsFragment.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private IContract.IPresenter presenter;

    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private List<Item> items = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int permissionDenied = 0;

    public ShopsFragment() {
        // required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup the adapter
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(items);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shops, container, false);
        setUpRecyclerView(rootView);
        return rootView;
    }

    private void setUpRecyclerView(View rootView) {
        // Prepare RecyclerView and its Adapter
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_shops);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myRecyclerViewAdapter);
        recyclerView.addOnItemTouchListener(new MyRecyclerTouchListener(getContext(), this));
    }

    @Override
    public void onClick(int position) {
        String websiteUrl = myRecyclerViewAdapter.getWebsiteUrl(position);
        presenter.onItemClicked(websiteUrl);
    }

    @Override
    public void showLoading() {
        showLoading(getString(R.string.network_wait_message));
    }

    public void showLoading(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void populateData(List<Item> itemList) {
        myRecyclerViewAdapter.updateData(itemList);
    }

    @Override
    public void showRationalForPermission() {
        Toast.makeText(getContext(),
                getString(R.string.permission_rationale_message),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void startNewActivity(String url) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(Constants.WEB_URL_KEY, url);
        startActivity(intent);
    }

    @Override
    public void setPresenter(IContract.IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(permissionDenied > 3)
            return;
        requestLocationUpdates();
    }

    @Override
    public void requestLocationUpdates() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        showLoading(getString(R.string.location_wait_message));
        String bestProvider = setCriteriaAndGetBestProvider(locationManager);
        locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);
    }

    public String setCriteriaAndGetBestProvider(LocationManager locationManager) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        return locationManager.getBestProvider(criteria, true);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, " location changed " + location);
            if (!isAdded())
                return;
            hideLoading();
            if (presenter == null)
                return;
            presenter.start(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onPermissionReceived();
                } else {
                    permissionDenied++;
                    presenter.onPermissionDenied();
                }
            }
        }
    }
}
