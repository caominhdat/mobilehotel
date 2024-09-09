package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class AdminActivity extends Activity {

    private ListView adminMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminMenu = findViewById(R.id.admin_menu);

        // Danh sách chức năng quản trị
        String[] adminOptions = {"Quản trị khách sạn", "Quản trị đơn đặt phòng"};

        // Sử dụng ArrayAdapter để hiển thị danh sách
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item, adminOptions);
        adminMenu.setAdapter(adapter);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.admin_menu_item, adminOptions);
//        ListView listView = findViewById(R.id.admin_menu);
//        listView.setAdapter(adapter);


        // Xử lý sự kiện khi chọn chức năng
        adminMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Quản trị khách sạn
                        Intent intentHotel = new Intent(AdminActivity.this, HotelManageActivity.class);
                        startActivity(intentHotel);
                        break;
                    case 1: // Quản trị đơn đặt phòng
                        Intent intentOrder = new Intent(AdminActivity.this, HotelOrderActivity.class);
                        startActivity(intentOrder);
                        break;
                    default:
                        Toast.makeText(AdminActivity.this, "Chức năng chưa được triển khai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

