package com.cmd.hotelapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cmd.hotelapp.Model.Hotel;
import com.cmd.hotelapp.R;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private final List<Hotel> hotels;

    public GridAdapter(Context context, List<Hotel> hotels) {
        this.context = context;
        this.hotels = hotels;
    }

    @Override
    public int getCount() {
        return hotels.size();
    }

    @Override
    public Object getItem(int position) {
        return hotels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.roomName);
        ImageView imageView = convertView.findViewById(R.id.roomImage);

        Hotel hotel = hotels.get(position);

        textView.setText(hotel.getName());
        Glide.with(context).load(hotel.getImage()).into(imageView);

        return convertView;
    }

}