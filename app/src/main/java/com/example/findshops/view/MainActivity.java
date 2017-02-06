package com.example.findshops.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.findshops.R;
import com.example.findshops.model.ShopRepository;
import com.example.findshops.presenter.MyPresenter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShopsFragment shopsFragment = new ShopsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main, shopsFragment, null).commit();
        ShopRepository shopRepository = new ShopRepository(getApplicationContext());
        MyPresenter myPresenter = new MyPresenter(shopsFragment, shopRepository);
    }

}