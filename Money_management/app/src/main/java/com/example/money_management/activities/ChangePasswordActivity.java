package com.example.money_management.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView btnBack;
    private AppCompatButton btnChangePassword;
    private TextView btnForgotPassword;
    private TextInputEditText txtCurrentPassword, txtNewPassword;
    private SharedPreferences sharedpreferences; // Lấy dữ liệu đăng nhập.
    private final String thisTag = "ChangePasswordActivityTag";

    private interface EmailCallback{
        void isEmailExist(boolean exist, String remotePassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mapping();
        //Log.i("Tracking Activity Created", "ChangePasswordActivity");
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences" chứa email


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this,AccountInformationActivity.class));
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = sharedpreferences.getString("Email", null);
                String enteredNewPassword = txtNewPassword.getText().toString();
                String enteredCurrentPassword = txtCurrentPassword.getText().toString();

                if(enteredNewPassword.isEmpty() || enteredCurrentPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Kiểm tra tài khoản có tồn tại trên firebase không, kiểm trả luôn mật khẩu.
                    checkAccountExist(new EmailCallback() {
                        @Override
                        public void isEmailExist(boolean exist, String remotePassword) {
                            if (exist) {  // Tài khoản tồn tại
                                if (md5(enteredCurrentPassword).equals(remotePassword)) { // Đúng password
                                    //Log.d(thisTag, "Đổi mật khẩu thành công thành công" + "  " + enteredNewPassword + "   " + remotePassword + "   " + md5(enteredNewPassword));
                                    
                                    // Cập nhật mật khẩu lên firestore
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    CollectionReference usersRef = db.collection("Accounts");
                                    Query query = usersRef.whereEqualTo("Username", email);
                                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                //Log.d(thisTag, "Thành công lấy dữ liệu từ filestore", task.getException());
                                                String remotePassword = md5(md5(email));
                                                for (DocumentSnapshot documentsnap : task.getResult()) {
                                                    String account = documentsnap.getString("Username");
                                                    if (account.equals(email)) {
                                                        db.collection("Accounts").document(documentsnap.getId()).update("Password", md5(enteredNewPassword));
                                                        Log.d(thisTag, "Thành công đổi mật khẩu" + "    "  + documentsnap.getId(), task.getException());
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));  // Mở trang chủ
                                    finish();
                                } else { // Sai password
                                    AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
                                    alertDialog.setTitle("Sai mật khẩu");
                                    alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
                                    alertDialog.show();
                                    //Log.d(thisTag, "Sai mật khẩu");
                                }
                            } else { // Không tồn tại tài khoản
                                AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
                                alertDialog.setTitle("Tài khoản không tồn tại");
                                alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
                                alertDialog.show();
                               // Log.d(thisTag, "Không tồn tại tài khoản");
                            }
                        }
                    }, email);
                }

            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // Kiểm tra trên firebase nếu tài khoản có tồn tại và kiểm trả mật khẩu
    private void checkAccountExist(EmailCallback emailCallback, String email){
        Log.d(thisTag, "Tiến hành kiểm tra thông tin trên firebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Accounts");
        Query query = usersRef.whereEqualTo("Username", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(thisTag, "Thành công lấy dữ liệu từ filestore", task.getException());
                    boolean isExist = false;
                    String remotePassword = md5(md5(email));
                    for (DocumentSnapshot document : task.getResult()) {
                        String account = document.getString("Username");
                        if (account.equals(email)) {
                            isExist = true;
                            remotePassword = document.getString("Password");
                        }
                    }
                    if(isExist)
                        emailCallback.isEmailExist(true, remotePassword);
                    else
                        emailCallback.isEmailExist(false, remotePassword);

                } else {
                    Log.d(thisTag, "Không thành công lấy dữ liệu từ filestore", task.getException());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AccountInformationActivity.class));
        finish();
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        btnChangePassword = findViewById(R.id.button_change_password);
        btnForgotPassword = findViewById(R.id.forgot_password);
        txtCurrentPassword = findViewById(R.id.txt_current_password);
        txtNewPassword = findViewById(R.id.txt_new_password);
    }
}