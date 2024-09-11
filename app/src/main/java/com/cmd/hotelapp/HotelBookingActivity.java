package com.cmd.hotelapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HotelBookingActivity extends Activity {

    private EditText mDisplayDate1;
    private EditText mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hotel_booking);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        CollectionReference ticketRef = db.collection("ticket");
        FirebaseUser user = mAuth.getCurrentUser();

        // Lấy dữ liệu khách sạn từ Intent
        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotel_name");
        String hotelPrice = intent.getStringExtra("hotel_price");
        String hotelAddress = intent.getStringExtra("hotel_address");

        // Hiển thị dữ liệu
        TextView nameTextView = findViewById(R.id.hotel_name);
        TextView priceTextView = findViewById(R.id.hotel_price);
        TextView addressTextView = findViewById(R.id.hotel_address);
        TextView bookingButton = findViewById(R.id.hotel_booking_button);

        nameTextView.setText(hotelName);
        priceTextView.setText(hotelPrice);
        addressTextView.setText(hotelAddress);

        // Mở bảng chọn ngày tháng năm khi chọn vào
        mDisplayDate1 = (EditText) findViewById(R.id.hotel_bookdate);
        mDisplayDate2 = (EditText) findViewById(R.id.hotel_checkoutdate);

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

        //Xử lí gửi data ticket len he thong
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String HotelName = nameTextView.getText().toString();
                String Price = priceTextView.getText().toString();
                String BookDate = mDisplayDate1.getText().toString();
                String CheckoutDate = mDisplayDate2.getText().toString();
                String UserEmail = user.getEmail();
                //
                Map<String,Object> ticket = new HashMap<>();
                ticket.put("Hotel Name", HotelName);
                ticket.put("Book Date", BookDate);
                ticket.put("Checkout Date", CheckoutDate);
                ticket.put("Email", UserEmail);
                ticket.put("Price",Price);
                //
                db.collection("ticket").add(ticket).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(HotelBookingActivity.this,"",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(HotelBookingActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}