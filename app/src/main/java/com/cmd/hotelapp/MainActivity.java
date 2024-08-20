package com.cmd.hotelapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toolbar;
import androidx.annotation.Nullable;

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
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    GridView gridView;
    GridAdapter gridAdapter;
    private List<Hotel> hotelList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        viewFlipper.setFlipInterval(4000); // 4 giây cho mỗi quảng cáo
        viewFlipper.setAutoStart(true);// Tự động bắt đầu khi activity được khởi động
        hotelList = new ArrayList<>();
        gridAdapter = new GridAdapter(this, hotelList);
        gridView.setAdapter(gridAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference hotelsRef = db.collection("Hotel");

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
    }

    private void Anhxa() {
        toolbar=findViewById(R.id.toolbar_manhinhchinh);
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
}

