package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.annotation.Nullable;

import android.widget.SearchView;
import android.widget.ViewFlipper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.cmd.hotelapp.Adapter.GridAdapter;
import com.cmd.hotelapp.Model.Hotel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    //Toolbar toolbar;
    ViewFlipper viewFlipper;
    GridView gridView;
    GridAdapter gridAdapter;
    SearchView searchView;
    private List<Hotel> hotelList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        viewFlipper.setFlipInterval(4000); // 4 giây cho mỗi quảng cáo
        viewFlipper.setAutoStart(true);// Tự động bắt đầu khi activity được khởi động
        hotelList = new ArrayList<>();
        gridAdapter = new GridAdapter(this,  hotelList);
        gridView.setAdapter(gridAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference hotelsRef = db.collection("Hotel");

        // Lắng nghe sự kiện nhập vào SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHotels(query); // Gọi hàm tìm kiếm
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchHotels(newText); // Gọi hàm tìm kiếm khi thay đổi
                return false;
            }
        });

        hotelsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Xử lý lỗi
                    return;
                }

                hotelList.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    Hotel hotel = document.toObject(Hotel.class);
                    hotelList.add(hotel);
                }
                gridAdapter.notifyDataSetChanged();
            }


        });

        // Xử lý sự kiện khi nhấn vào một item trong GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin khách sạn được chọn
                Hotel selectedHotel = hotelList.get(position);

                // Chuyển sang HotelDetailActivity
                Intent intent = new Intent(MainActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel_name", selectedHotel.getName());
                intent.putExtra("hotel_address", selectedHotel.getAddress());
                intent.putExtra("hotel_description", selectedHotel.getDescription());
                intent.putExtra("hotel_image", selectedHotel.getImage());
                intent.putExtra("hotel_price", selectedHotel.getPrice());
                intent.putExtra("room_image", selectedHotel.getImgRoom());
                intent.putExtra("room_description", selectedHotel.getDescriptionRoom());
                // Truyền thêm các thông tin khác nếu cần
                startActivity(intent);
            }
        });
    }



    private void Anhxa() {
        //toolbar=findViewById(R.id.toolbar_manhinhchinh);
        viewFlipper=findViewById(R.id.viewflipper);
        gridView=findViewById(R.id.gridview);
    }


    // Bạn cũng có thể thêm các phương thức để điều khiển ViewFlipper thủ công
    private void showNextAd() {
        viewFlipper.showNext();
    }

    private void showPreviousAd() {
        viewFlipper.showPrevious();
    }

    private void searchHotels(String query) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference hotelsRef = db.collection("Hotel");

        hotelsRef.whereGreaterThanOrEqualTo("name", query)
                .whereLessThanOrEqualTo("name", query + '\uf8ff')
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }

                        hotelList.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Hotel hotel = document.toObject(Hotel.class);
                            hotelList.add(hotel);
                        }
                        gridAdapter.notifyDataSetChanged();
                    }
                });
    }




}