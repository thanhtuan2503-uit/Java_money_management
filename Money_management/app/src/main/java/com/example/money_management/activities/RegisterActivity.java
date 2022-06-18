package com.example.money_management.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    EditText editText_email;
    EditText editText_password;
    EditText editText_confirmpassword;
    Button button_register;
    TextView textView_signin;
    private final String thisTag = "RegisterActivityTag";

    private interface EmailCallback{
        void isEmailExist(boolean exist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        Log.i("Tracking Activity Created", "RegisterActivity");
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();
                String confirmpassword = editText_confirmpassword.getText().toString();
                Log.d(thisTag, "Đang kiểm tra dữ liệu nhập vào");
                Log.d(thisTag, "Email: " + email + "   Password: " + password);

                // Kiểm tra username có phải là email
                if (!checkRegisterTextFormat(email)) {
                    Log.d(thisTag, "Không là định dạng email");
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Email không đúng định dạng");
                    alertDialog.setMessage(("Hãy kiểm tra lại thông tin"));
                    alertDialog.show();
                    return;
                }
                else
                    Log.d(thisTag, "Là định dạng email");

                // Kiểm tra thiếu thông tin
                if (checkRegisterTextEmpty(email, password, confirmpassword)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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
                    public void isEmailExist(boolean exist) {
                        if (exist) {  // Tài khoản tồn tại
                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                            alertDialog.setTitle("Email đã được sử dụng");
                            alertDialog.setMessage(("Hãy dùng Email khác"));
                            alertDialog.show();
                            Log.d(thisTag, "Đã tồn tại tài khoản");
                        }
                        else { // Không tồn tại tài khoản
                            Log.d(thisTag, "Không tồn tại tài khoản");
                            // Kiểm tra password và passowrd nhập lại có giống không
                            if(password.equals(confirmpassword)) {// Giống password
                                SharedPreferences sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
                                SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                                editor.putString("Email", email);
                                editor.commit();
                                createMoneySource(email);
                                createIncomeOutcomeType(email, "Ăn uống", "Lương");
                                createIncomeOutcomeType(email, "Di chuyển", "Hốt hụi");
                                createIncomeOutcomeType(email, "Hẹn hò", "");
                                createIncomeOutcomeType(email, "Gia đình", "");
                                createIncomeOutcomeType(email, "Dụng cụ học tập", "");
                                createIncomeOutcomeType(email, "Đồ dùng bếp", "");
                                createIncomeOutcomeType(email, "Mỹ phẩm", "");
                                createIncomeOutcomeType(email, "Sữa tắm", "");
                                createIncomeOutcomeType(email, "Giải trí", "");
                                createAccount(email, md5(password)); // Tạo tài khoản trên firestore
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));  // Mở trang chủ
                            }
                            else{ // Không giống password
                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                                alertDialog.setTitle("Mật khẩu không trùng khớp");
                                alertDialog.setMessage(("Hãy kiểm tra lại"));
                                alertDialog.show();
                                Log.d(thisTag, "Mật khẩu không trùng khớp");
                            }
                        }
                    }
                }, email);
            }
        });

        textView_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

    private void createMoneySource(String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> account = new HashMap<>();
        account.put("Email", email);
        account.put("Amount", "0");
        account.put("SourceName", "Tiền Mặt");

        db.collection("MoneySource")
                .add(account)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(thisTag, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(thisTag, "Error adding document", e);
                    }
                });
    }

    private void createIncomeOutcomeType(String email, String ocTypeName, String icTypeName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> income = new HashMap<>();
        income.put("Describe", "");
        income.put("Email", email);
        income.put("Icon", "");
        income.put("Limit", 100.0F);
        income.put("TypeName", icTypeName);

        Map<String, Object> outcome = new HashMap<>();
        outcome.put("Describe", "");
        outcome.put("Email", email);
        outcome.put("Icon", "");
        outcome.put("Limit", 100.0F);
        outcome.put("TypeName", ocTypeName);

        if(!icTypeName.equals(""))
        db.collection("IncomeType")
                .add(income)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(thisTag, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(thisTag, "Error adding document", e);
                    }
                });

        if(!ocTypeName.equals(""))
        db.collection("SpendingType")
                .add(outcome)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(thisTag, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(thisTag, "Error adding document", e);
                    }
                });
    }

    // Kiểm tra nội dung người dùng nhập có phải là email không.
    // True là email, False không là email.
    private boolean checkRegisterTextFormat(CharSequence email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Kiểm tra nội dung người dùng nhập có rỗng không.
    // True là rỗng.
    private boolean checkRegisterTextEmpty(String email, String password, String confirmpassword){
        return email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty();
    }

    // Kiểm tra nếu tài khoản đã tồn tại
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
                    for (DocumentSnapshot document : task.getResult()) {
                        String account = document.getString("Username");
                        if (account.equals(email)) {
                            isExist = true;
                        }
                    }
                    if(isExist)
                        emailCallback.isEmailExist(true);
                    else
                        emailCallback.isEmailExist(false);
                } else {
                    Log.d(thisTag, "Không thành công lấy dữ liệu từ filestore", task.getException());
                }
            }
        });
    }

    // Lưu tài khoản lên firestore
    private void createAccount(String email, String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Model
        Map<String, Object> account = new HashMap<>();
        account.put("Email", email);
        account.put("Username", email);
        account.put("Password", password);
        account.put("ID", "");
        account.put("Name", "");

        // Thêm vào firestore
        db.collection("Accounts")
                .add(account)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(thisTag, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(thisTag, "Error adding document", e);
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
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void anhxa() {
        textView_signin = findViewById(R.id.textView_signin);
        editText_email = findViewById(R.id.editext_email);
        editText_password = findViewById(R.id.edittext_password);
        editText_confirmpassword = findViewById(R.id.edittext_confirmpassword);
        button_register = findViewById(R.id.btn_register);
        lottieAnimationView = findViewById(R.id.LottieAnimationView_Reg);
    }
}