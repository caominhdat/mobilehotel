package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_bnt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateCredentials(username, password)) {
                    // Nếu đăng nhập thành công, chuyển sang màn hình chính
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm giả lập xác thực người dùng
    private boolean validateCredentials(String username, String password) {
        // Bạn có thể thay thế đoạn này bằng việc kiểm tra từ cơ sở dữ liệu hoặc API
        return username.equals("admin") && password.equals("password");
    }
}

