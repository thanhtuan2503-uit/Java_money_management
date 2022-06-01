package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextView txtViewRegister;
    private MaterialTextView txtViewForgotPassword;
    private MaterialButton btnLogin;
    private ImageView btnFacebook, btnGoogle;
    private final String thisTag = "LoginActivityTag";

    private interface EmailCallback{
        void isEmailExist(boolean exist, String remotePassword);
    }
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();


        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                Log.d(thisTag, "Đang kiểm tra dữ liệu nhập vào");
                Log.d(thisTag, "Email: " + email + "   Password: " + password);

                // Kiểm tra username có phải là email
                if (!checkLoginTextFormat(email)) {
                    Log.d(thisTag, "Không là định dạng email");
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Email không đúng định dạng");
                    alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
                    alertDialog.show();
                    return;
                }
                else
                    Log.d(thisTag, "Là định dạng email");

                // Kiểm tra thiếu thông tin
                if (checkLoginTextEmpty(email, password)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Thiếu thông tin");
                    alertDialog.setMessage(("Hãy điền đủ thông tin"));
                    alertDialog.show();
                    Log.d(thisTag, "Thiếu thông tin");
                    return;
                } else
                    Log.d(thisTag, "Đủ thông tin");

                // Kiểm tra tài khoản có tồn tại trên firebase không, kiểm trả luôn mật khẩu.
                checkAccountExist(new EmailCallback() {
                    @Override
                    public void isEmailExist(boolean exist, String remotePassword) {
                        if (exist) {  // Tài khoản tồn tại
                            if (md5(password).equals(remotePassword)) { // Đúng password
                                Log.d(thisTag, "Đăng nhập thành công");
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));  // Mở trang chủ
                                //finish()
                            } else { // Sai password
                                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                alertDialog.setTitle("Sai mật khẩu");
                                alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
                                alertDialog.show();
                                Log.d(thisTag, "Sai mật khẩu");
                            }
                        } else { // Không tồn tại tài khoản
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Tài khoản không tồn tại");
                            alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
                            alertDialog.show();
                            Log.d(thisTag, "Không tồn tại tài khoản");
                        }
                    }
                }, email);
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // Kiểm tra nội dung người dùng nhập có phải là email không.
    // True là email, False không là email.
    private boolean checkLoginTextFormat(CharSequence email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Kiểm tra nội dung người dùng nhập có rỗng không.
    // True là rỗng.
    private boolean checkLoginTextEmpty(String email, String password){
        return email.isEmpty() || password.isEmpty();
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
                    String remotePassword = md5(email);
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

    //Hàm hash mật khẩu MD5
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

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            this.finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    private void mapping() {
        editTextEmail = findViewById(R.id.editext_email_LogActivity);
        editTextPassword = findViewById(R.id.edittext_password_LogActivity);
        txtViewRegister = findViewById(R.id.textView_register_logActivity);
        txtViewForgotPassword = findViewById(R.id.txt_forgotpassword);
        btnLogin = findViewById(R.id.btn_login_LogActivity);
        btnFacebook = findViewById(R.id.button_facebook);
        btnGoogle = findViewById(R.id.button_google);
    }
}
