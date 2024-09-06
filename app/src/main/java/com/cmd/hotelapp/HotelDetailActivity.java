package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide; // Dùng Glide để tải hình ảnh từ URL hoặc drawable

public class HotelDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        // Lấy dữ liệu khách sạn từ Intent
        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotel_name");
        String hotelDescription = intent.getStringExtra("hotel_description");
        String hotelImageUrl = intent.getStringExtra("hotel_image");
        String hotelPrice = intent.getStringExtra("hotel_price");
        String hotelAddress = intent.getStringExtra("hotel_address");
        String roomImageUrl = intent.getStringExtra("room_image");
        String roomDescription = intent.getStringExtra("room_description");

        // Hiển thị dữ liệu
        TextView nameTextView = findViewById(R.id.hotel_name);
        TextView descriptionTextView = findViewById(R.id.hotel_description);
        ImageView hotelImageView = findViewById(R.id.hotel_image);
        TextView priceTextView = findViewById(R.id.hotel_price);
        TextView addressTextView = findViewById(R.id.hotel_address);
        ImageView roomImageView = findViewById(R.id.room_image);
        TextView roomDescriptionTextView = findViewById(R.id.room_description);

        nameTextView.setText(hotelName);
        descriptionTextView.setText(hotelDescription);
        priceTextView.setText(hotelPrice);
        addressTextView.setText(hotelAddress);
        roomDescriptionTextView.setText(roomDescription);

        // Sử dụng Glide để load ảnh (hoặc thay bằng cách load khác nếu cần)
        Glide.with(this)
                .load(hotelImageUrl)// Đây có thể là URL hình ảnh hoặc tên resource drawable
                .into(hotelImageView);
        Glide.with(this)
                .load(roomImageUrl)// Đây có thể là URL hình ảnh hoặc tên resource drawable
                .into(roomImageView);

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo URI để mở Google Maps với địa chỉ
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(hotelAddress));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                // Kiểm tra nếu có app Google Maps để mở
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // Mở Google Maps trong trình duyệt nếu không có Google Maps app
                    Uri browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(hotelAddress));
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
                    startActivity(browserIntent);
                }
            }
        });

    }


}
