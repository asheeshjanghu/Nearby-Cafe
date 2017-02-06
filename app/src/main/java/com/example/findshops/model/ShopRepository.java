package com.example.findshops.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.findshops.BuildConfig;
import com.example.findshops.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalpana on 04-Feb-17.
 */

public class ShopRepository implements IShopDataSource{

    private static final String TAG = ShopRepository.class.getSimpleName();
    private Context context;

    public ShopRepository(Context context) {
        this.context = context;
    }

    @Override
    public void getShopsFromNetwork(android.location.Location location, final LoadCallback callback) {
        String lat = String.valueOf(location.getLatitude());
        String lng = String.valueOf(location.getLongitude());
        String url = Constants.BASE_URL + lat + "," + lng +"&client_id=" + BuildConfig.CLIENT_ID + "&client_secret=" + BuildConfig.CLIENT_SECRET + "&v=201702004";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Item> items = null;
                try {
                    JSONArray jsonArray = ((JSONObject)(response.getJSONObject("response").getJSONArray("groups").get(0))).getJSONArray("items");
                    items = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Item>>(){}.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onDataLoaded(items);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "error = " + error);
                callback.onDataLoadFailed();
            }
        });
        // add request to queue where it will be processed and started
        requestQueue.add(stringRequest);
    }
}
