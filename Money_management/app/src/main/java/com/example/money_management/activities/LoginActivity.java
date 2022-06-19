package com.example.money_management.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextView txtViewRegister;
    private MaterialTextView txtViewForgotPassword;
    private MaterialButton btnLogin;
    private ImageView btnFacebook, btnGoogle;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    private final String thisTag = "LoginActivityTag";
    private SharedPreferences sharedpreferences; // Để thay đổi dữ trạng thái đăng nhập.

    private interface EmailCallback{
        void isEmailExist(boolean exist, String remotePassword);
    }
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        Log.i("Tracking Activity Created", "LoginActivity");
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
        String logged_Email = sharedpreferences.getString("Email", "");
        if(!logged_Email.equals("")){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { login();}
//                String email = editTextEmail.getText().toString();
//                String password = editTextPassword.getText().toString();
//                Log.d(thisTag, "Đang kiểm tra dữ liệu nhập vào");
//                Log.d(thisTag, "Email: " + email + "   Password: " + password);
//
//                // Kiểm tra username có phải là email
//                if (!checkLoginTextFormat(email)) {
//                    Log.d(thisTag, "Không là định dạng email");
//                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//                    alertDialog.setTitle("Email không đúng định dạng");
//                    alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
//                    alertDialog.show();
//                    return;
//                }
//                else
//                    Log.d(thisTag, "Là định dạng email");
//
//                // Kiểm tra thiếu thông tin
//                if (checkLoginTextEmpty(email, password)) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//                    alertDialog.setTitle("Thiếu thông tin");
//                    alertDialog.setMessage(("Hãy điền đủ thông tin"));
//                    alertDialog.show();
//                    Log.d(thisTag, "Thiếu thông tin");
//                    return;
//                } else
//                    Log.d(thisTag, "Đủ thông tin");
//
//                // Kiểm tra tài khoản có tồn tại trên firebase không, kiểm trả luôn mật khẩu.
//                checkAccountExist(new EmailCallback() {
//                    @Override
//                    public void isEmailExist(boolean exist, String remotePassword) {
//                        if (exist) {  // Tài khoản tồn tại
//                            if (md5(password).equals(remotePassword)) { // Đúng password
//                                Log.d(thisTag, "Đăng nhập thành công");
//                                SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
//                                editor.putString("Email", email);
//                                editor.commit();
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));  // Mở trang chủ
//                                finish();
//                            } else { // Sai password
//                                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//                                alertDialog.setTitle("Sai mật khẩu");
//                                alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
//                                alertDialog.show();
//                                Log.d(thisTag, "Sai mật khẩu");
//                            }
//                        } else { // Không tồn tại tài khoản
//                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//                            alertDialog.setTitle("Tài khoản không tồn tại");
//                            alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
//                            alertDialog.show();
//                            Log.d(thisTag, "Không tồn tại tài khoản");
//                        }
//                    }
//                }, email);
//            }
        });
        txtViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                finish();
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
    private void login(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        userID = fAuth.getCurrentUser().getUid();
        if(TextUtils.isEmpty(email))
        {
            editTextEmail.setError("Vui lòng nhập email!");
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            editTextPassword.setError("Vui lòng nhập mật khẩu!");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).isEmailVerified())
                {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(LoginActivity.this, "There is no user record\ncorresponding to this identifier. The\nuser may have been deleted.", Toasty.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    return;
                }
                SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                editor.putString("Email", email);
                editor.commit();
                Toasty.success(LoginActivity.this,"Đăng nhập thành công!",Toasty.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                SystemClock.sleep(1000);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(LoginActivity.this, "Vui lòng kiểm tra lại thông tin đăng nhập!", Toasty.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
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

    // Kiểm tra trên firebase nếu tài khoản có tồn tại và kiểm tra mật khẩu
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
        progressBar = findViewById(R.id.progress_bar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
}
