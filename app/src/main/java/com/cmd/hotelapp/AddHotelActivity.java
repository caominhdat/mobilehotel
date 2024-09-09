package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cmd.hotelapp.Model.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;

public class AddHotelActivity extends Activity {

    private EditText editHotelName, editHotelAddress, editHotelPrice, editHotelDescription, editHotelImage, editRoomImage, editDescriptionRoom;
    private Button buttonAddHotel;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        editHotelName = findViewById(R.id.edit_hotel_name);
        editHotelAddress = findViewById(R.id.edit_hotel_address);
        editHotelPrice = findViewById(R.id.edit_hotel_price);
        editHotelDescription = findViewById(R.id.edit_hotel_description);
        editHotelImage = findViewById(R.id.edit_hotel_image);
        editRoomImage = findViewById(R.id.edit_room_image);
        editDescriptionRoom = findViewById(R.id.edit_room_description);
        buttonAddHotel = findViewById(R.id.button_add_hotel);

        db = FirebaseFirestore.getInstance();

        buttonAddHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHotel();
            }
        });
    }

    private void addHotel() {
        String name = editHotelName.getText().toString().trim();
        String address = editHotelAddress.getText().toString().trim();
        String price = editHotelPrice.getText().toString().trim();
        String description = editHotelDescription.getText().toString().trim();
        String image = editHotelImage.getText().toString().trim();
        String imgRoom = editRoomImage.getText().toString().trim();
        String descriptionRoom = editDescriptionRoom.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || price.isEmpty() || description.isEmpty() || image.isEmpty() || imgRoom.isEmpty() || descriptionRoom.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy giá trị currentId từ collection Counters và document HotelCounter
        final DocumentReference counterRef = db.collection("Counters").document("HotelCounter");
        counterRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Lấy currentId
                        long currentId = document.getLong("currentId");

                        // Tăng currentId cho khách sạn mới
                        long newId = currentId + 1;

                        // Tạo khách sạn mới
                        Hotel newHotel = new Hotel(name, description, price, address, descriptionRoom, image, imgRoom, 0, 0);

                        // Thêm khách sạn vào Firestore với ID mới là số tăng dần
                        db.collection("Hotel").document(String.valueOf(newId))
                                .set(newHotel)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Cập nhật lại currentId trong document Counters
                                            counterRef.update("currentId", newId);

                                            Toast.makeText(AddHotelActivity.this, "Khách sạn đã được thêm thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddHotelActivity.this, HotelManageActivity.class);
                                            startActivity(intent);

                                            finish(); // Đóng Activity sau khi thêm
                                        } else {
                                            Toast.makeText(AddHotelActivity.this, "Có lỗi xảy ra! Thử lại.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        // Nếu document không tồn tại, khởi tạo currentId = 1
                        counterRef.set(Collections.singletonMap("currentId", 1L));
                        Toast.makeText(AddHotelActivity.this, "Đang khởi tạo bộ đếm. Thử lại sau.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddHotelActivity.this, "Lỗi khi lấy ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
