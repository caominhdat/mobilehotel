package com.cmd.hotelapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cmd.hotelapp.Model.Hotel;
import com.cmd.hotelapp.R;

import java.util.List;

public class HotelAdapter extends ArrayAdapter<Hotel> {

    private Context mContext;
    private List<Hotel> hotelList;

    public HotelAdapter(@NonNull Context context, List<Hotel> list) {
        super(context, 0 , list);
        mContext = context;
        hotelList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.hotel_list_item,parent,false);

        Hotel currentHotel = hotelList.get(position);

        TextView name = listItem.findViewById(R.id.hotel_name);
        name.setText(currentHotel.getName());

        TextView address = listItem.findViewById(R.id.hotel_address);
        address.setText(currentHotel.getAddress());

        TextView price = listItem.findViewById(R.id.hotel_price);
        price.setText(currentHotel.getPrice());

        TextView description = listItem.findViewById(R.id.hotel_description);
        description.setText(currentHotel.getDescription());

        ImageView hotelImageView = listItem.findViewById(R.id.hotel_image); // Khai báo ImageView
        String hotelImageUrl = currentHotel.getImage(); // Lấy URL hình ảnh từ đối tượng Hotel

        ImageView roomImageView = listItem.findViewById(R.id.room_image); // Khai báo ImageView
        String roomImageUrl = currentHotel.getImgRoom();

        // Sử dụng Glide để tải hình ảnh vào ImageView
        Glide.with(mContext)
                .load(hotelImageUrl) // Tải hình ảnh từ URL
                .into(hotelImageView);


        // Các trường khác như địa chỉ, giá phòng...

        return listItem;
    }
}
