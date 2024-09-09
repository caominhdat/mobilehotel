package com.cmd.hotelapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.app.Activity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class HotelBookingActivity extends Activity {

    private TextView mDisplayDate1;
    private TextView mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_booking);

        // Lấy dữ liệu khách sạn từ Intent
        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotel_name");
        String hotelPrice = intent.getStringExtra("hotel_price");
        String hotelAddress = intent.getStringExtra("hotel_address");

        // Hiển thị dữ liệu
        TextView nameTextView = findViewById(R.id.hotel_name);
        TextView priceTextView = findViewById(R.id.hotel_price);
        TextView addressTextView = findViewById(R.id.hotel_address);

        nameTextView.setText(hotelName);
        priceTextView.setText(hotelPrice);
        addressTextView.setText(hotelAddress);

        // Mở bảng chọn ngày tháng năm khi chọn vào
        mDisplayDate1 = (TextView) findViewById(R.id.hotel_bookdate);
        mDisplayDate2 = (TextView) findViewById(R.id.hotel_checkoutdate);

        //Xử lí ngày đặt phòng
        mDisplayDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HotelBookingActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener1,
                        day,month,year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate1.setText(date);
            }
        };
        //Xử lí ngày trả phòng
        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HotelBookingActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener2,
                        day,month,year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate2.setText(date);
            }
        };
    }

}