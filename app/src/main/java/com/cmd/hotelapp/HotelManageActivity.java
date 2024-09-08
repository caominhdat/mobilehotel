package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cmd.hotelapp.Adapter.HotelAdapter;
import com.cmd.hotelapp.Model.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelManageActivity extends Activity {

    private FirebaseFirestore db;
    private CollectionReference hotelRef;
    private ListView hotelListView;
    private Button addHotelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_manage);

        db = FirebaseFirestore.getInstance();
        hotelRef = db.collection("Hotel");

        hotelListView = findViewById(R.id.hotel_list);
        addHotelButton = findViewById(R.id.add_hotel_button);

        // Tải danh sách khách sạn từ Firestore
        loadHotelList();

        // Xử lý sự kiện thêm khách sạn
        Button addHotelButton = findViewById(R.id.add_hotel_button);
        addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở AddHotelActivity khi nhấn nút
                Intent intent = new Intent(HotelManageActivity.this, AddHotelActivity.class);
                startActivity(intent);
            }
        });
    }

    // Tải danh sách khách sạn
    private void loadHotelList() {
        hotelRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Hotel> hotelList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Hotel hotel = document.toObject(Hotel.class);
                        hotelList.add(hotel);
                    }
                    // Hiển thị danh sách khách sạn lên ListView
                    HotelAdapter adapter = new HotelAdapter(HotelManageActivity.this, hotelList);
                    hotelListView.setAdapter(adapter);
                } else {
                    Toast.makeText(HotelManageActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
