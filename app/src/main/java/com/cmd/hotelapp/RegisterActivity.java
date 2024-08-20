package com.cmd.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPassEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        confirmPassEditText = findViewById(R.id.confirm_password_input);
        registerButton = findViewById(R.id.register_bnt);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm = confirmPassEditText.getText().toString();

                if (validateCredentials(username, password)) {
                    // Nếu đăng nhập thành công, chuyển sang màn hình chính
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
                    Toast.makeText(RegisterActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
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

