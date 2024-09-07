package com.cmd.hotelapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;


import androidx.annotation.NonNull;

import com.cmd.hotelapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends Activity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo Firebase Auth và Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Khởi tạo các EditText và Spinner
        firstNameEditText = findViewById(R.id.firstName_input);
        lastNameEditText = findViewById(R.id.lastName_input);
        emailEditText = findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_input);
        confirmPasswordEditText = findViewById(R.id.confirm_password_input);
        phoneEditText = findViewById(R.id.phone_input);
        registerButton = findViewById(R.id.register_bnt);
        progressBar = findViewById(R.id.progressBar);

        // Khởi tạo Spinner cho role
        roleSpinner = findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String role = roleSpinner.getSelectedItem().toString();

                // Kiểm tra các thông tin nhập vào
                if (TextUtils.isEmpty(firstName)) {
                    firstNameEditText.setError("Vui lòng nhập tên");
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    lastNameEditText.setError("Vui lòng nhập họ");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Vui lòng nhập email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Vui lòng nhập mật khẩu");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordEditText.setError("Vui lòng xác nhận mật khẩu");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Mật khẩu không khớp");
                    return;
                }
                if (password.length() < 6) {
                    passwordEditText.setError("Mật khẩu phải dài ít nhất 6 ký tự");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    phoneEditText.setError("Vui lòng nhập số điện thoại");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Đăng ký thành công, lưu thông tin người dùng vào Firestore
                                    saveUserInfoToFirestore(firstName, lastName, email, phone, role);
                                } else {
                                    // Kiểm tra xem lỗi có phải do email đã được sử dụng không
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(RegisterActivity.this, "Email đã được sử dụng bởi tài khoản khác.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Hiển thị lỗi nếu đăng ký thất bại
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

//                if (role.equals("Admin")) {
//                    showAdminOptionDialog();
//                } else {
//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }


            }
        });
    }

    private void saveUserInfoToFirestore(String firstName, String lastName, String email, String phone, String role) {
        String userId = mAuth.getCurrentUser().getUid();

        User newUser = new User(firstName, lastName, email, phone, role);

        db.collection("user").document(userId)
                .set(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Lưu thông tin người dùng thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    private void showAdminOptionDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Chọn hành động");
//        builder.setMessage("Bạn muốn vào trang Quản trị hay trang chính?");
//
//        builder.setPositiveButton("Quản trị", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Chuyển đến trang quản trị
//                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        builder.setNegativeButton("Trang chính", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Chuyển đến trang chính
//                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        builder.show();
//    }

}
