package com.example.findshops.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findshops.R;
import com.example.findshops.model.Item;
import com.example.findshops.model.Location;
import com.example.findshops.model.Venue;
import com.example.findshops.utils.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Created by kalpana on 04-Feb-17.
 */

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {

    private static final String TAG = MyRecyclerViewAdapter.class.getSimpleName();
    private List <Item> items;

    MyRecyclerViewAdapter(List<Item> items) {
        this.items = items;
    }

    void updateData(List<Item> itemsList){
        this.items.clear();
        Collections.sort(itemsList);
        this.items.addAll(itemsList);
        Log.i(TAG, " inside adapter items = " + this.items);
        notifyDataSetChanged();
        Log.i(TAG, " after notify inside adapter items = " + this.items);

    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        return new MyRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        Item item = items.get(position);
        Venue venue = item.getVenue();
        Location location = venue.getLocation();
        holder.tvShopName.setText(venue.getName());
        holder.tvShopAddress.setText(location.getAddress());
        holder.tvShopDistance.setText(String.format("%s m", String.valueOf(location.getDistance())));
        String websiteUrl= Constants.UNAVAILABLE_URL + venue.getName();
        if(item.getTips() != null && item.getTips().size() >0 ){
             websiteUrl = item.getTips().get(0).getCanonicalUrl();
        }
        holder.websiteUrl = websiteUrl;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvShopName, tvShopAddress, tvShopDistance;
        String websiteUrl;

        MyRecyclerViewHolder(View itemView) {
            super(itemView);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvShopAddress = (TextView) itemView.findViewById(R.id.tv_shop_address);
            tvShopDistance = (TextView) itemView.findViewById(R.id.tv_shop_distance);
        }
    }

    String getWebsiteUrl(int pos){
        Item item = items.get(pos);
        String websiteUrl= "https://www.google.com.au/search?q=" + item.getVenue().getName();
        if(item.getTips() != null && item.getTips().size() >0 ){
            websiteUrl = item.getTips().get(0).getCanonicalUrl();
        }
        return websiteUrl;
    }


}
