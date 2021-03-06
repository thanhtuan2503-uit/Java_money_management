package com.example.money_management.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    TextInputEditText editText_email;
    TextInputEditText editText_password;
    TextInputEditText editText_confirmpassword;
    MaterialButton button_register;
    TextView textView_signin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    private final String thisTag = "RegisterActivityTag";

    private interface EmailCallback {
        void isEmailExist(boolean exist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }
        Log.i("Tracking Activity Created", "RegisterActivity");
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
//                String email = editText_email.getText().toString();
//                String password = editText_password.getText().toString();
//                String confirmpassword = editText_confirmpassword.getText().toString();
//                Log.d(thisTag, "??ang ki???m tra d??? li???u nh???p v??o");
//                Log.d(thisTag, "Email: " + email + "   Password: " + password);
//
//                // Ki???m tra username c?? ph???i l?? email
//                if (!checkRegisterTextFormat(email)) {
//                    Log.d(thisTag, "Kh??ng l?? ?????nh d???ng email");
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
//                    alertDialog.setTitle("Email kh??ng ????ng ?????nh d???ng");
//                    alertDialog.setMessage(("H??y ki???m tra l???i th??ng tin"));
//                    alertDialog.show();
//                    return;
//                }
//                else
//                    Log.d(thisTag, "L?? ?????nh d???ng email");
//
//                // Ki???m tra thi???u th??ng tin
//                if (checkRegisterTextEmpty(email, password, confirmpassword)) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
//                    alertDialog.setTitle("Thi???u th??ng tin");
//                    alertDialog.setMessage(("H??y ??i???n ????? th??ng tin"));
//                    alertDialog.show();
//                    Log.d(thisTag, "Thi???u th??ng tin");
//                    return;
//                } else
//                    Log.d(thisTag, "????? th??ng tin");
//
//
//                // Ki???m tra t??i kho???n c?? t???n t???i tr??n firebase kh??ng, ki???m tr??? lu??n m???t kh???u.
//                checkAccountExist(new EmailCallback() {
//                    @Override
//                    public void isEmailExist(boolean exist) {
//                        if (exist) {  // T??i kho???n t???n t???i
//                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
//                            alertDialog.setTitle("Email ???? ???????c s??? d???ng");
//                            alertDialog.setMessage(("H??y d??ng Email kh??c"));
//                            alertDialog.show();
//                            Log.d(thisTag, "???? t???n t???i t??i kho???n");
//                        }
//                        else { // Kh??ng t???n t???i t??i kho???n
//                            Log.d(thisTag, "Kh??ng t???n t???i t??i kho???n");
//                            // Ki???m tra password v?? passowrd nh???p l???i c?? gi???ng kh??ng
//                            if(password.equals(confirmpassword)) {// Gi???ng password
//                                SharedPreferences sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Ch???n file c?? t??n "LoginPreferences"
//                                SharedPreferences.Editor editor = sharedpreferences.edit(); // L??u tr???ng th??i ????ng nh???p
//                                editor.putString("Email", email);
//                                editor.commit();
//                                createMoneySource(email);
//                                createIncomeOutcomeType(email, "??n u???ng", "L????ng");
//                                createIncomeOutcomeType(email, "Di chuy???n", "H???t h???i");
//                                createIncomeOutcomeType(email, "H???n h??", "");
//                                createIncomeOutcomeType(email, "Gia ????nh", "");
//                                createIncomeOutcomeType(email, "D???ng c??? h???c t???p", "");
//                                createIncomeOutcomeType(email, "????? d??ng b???p", "");
//                                createIncomeOutcomeType(email, "M??? ph???m", "");
//                                createIncomeOutcomeType(email, "S???a t???m", "");
//                                createIncomeOutcomeType(email, "Gi???i tr??", "");
//                                createAccount(email, md5(password)); // T???o t??i kho???n tr??n firestore
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));  // M??? trang ch???
//                            }
//                            else{ // Kh??ng gi???ng password
//                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
//                                alertDialog.setTitle("M???t kh???u kh??ng tr??ng kh???p");
//                                alertDialog.setMessage(("H??y ki???m tra l???i"));
//                                alertDialog.show();
//                                Log.d(thisTag, "M???t kh???u kh??ng tr??ng kh???p");
//                            }
//                        }
//                    }
//                }, email);
//            }
        });

        textView_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }

    private void register() {
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();
        String confirmpassword = editText_confirmpassword.getText().toString().trim();
        //Ki???m tra email
        if (TextUtils.isEmpty(email)) {
            editText_email.setError("Vui l??ng nh???p Email!");
            return;
        }
        //Ki???m tra password
        if (TextUtils.isEmpty(password)) {
            editText_password.setError("Vui l??ng nh???p m???t kh???u");
            return;
        }
        //Ki???m tra ????? d??i
        if (editText_password.length() < 6) {
            editText_password.setError("Vui l??ng nh???p m???t kh???u l???n h??n 6 k?? t???!");
            return;
        }
        //Ki???m tra m???t kh???u c?? kh???p kh??ng
        if (!password.equals(confirmpassword)) {
            editText_confirmpassword.setError("M???t kh???u kh??ng kh???p!");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        // register user in firebase
        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                SharedPreferences sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Ch???n file c?? t??n "LoginPreferences"
                SharedPreferences.Editor editor = sharedpreferences.edit(); // L??u tr???ng th??i ????ng nh???p
                editor.putString("Email", email);
                editor.commit();
                createMoneySource(email);
                createIncomeOutcomeType(email, "??n u???ng", "L????ng");
                createIncomeOutcomeType(email, "Di chuy???n", "H???t h???i");
                createIncomeOutcomeType(email, "H???n h??", "");
                createIncomeOutcomeType(email, "Gia ????nh", "");
                createIncomeOutcomeType(email, "D???ng c??? h???c t???p", "");
                createIncomeOutcomeType(email, "????? d??ng b???p", "");
                createIncomeOutcomeType(email, "M??? ph???m", "");
                createIncomeOutcomeType(email, "S???a t???m", "");
                createIncomeOutcomeType(email, "Gi???i tr??", "");
                createAccount(email, password);

                //X??c th???c email
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).sendEmailVerification()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(), VerificationActivity.class));
                                progressBar.setVisibility(View.GONE);
                                finish();
                                //fAuth.signOut();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(RegisterActivity.this, Objects.requireNonNull(e.getMessage()), Toasty.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                //fAuth.signOut();
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(RegisterActivity.this, Objects.requireNonNull("Email kh??ng ?????ng ?????nh d???ng. Vui l??ng nh???p l???i!"), Toasty.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void createMoneySource(String email) {
        Map<String, Object> account = new HashMap<>();
        account.put("Email", email);
        account.put("Amount", "0");
        account.put("SourceName", "Ti???n M???t");

        fStore.collection("MoneySource")
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

    private void createIncomeOutcomeType(String email, String ocTypeName, String icTypeName) {
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
            fStore.collection("IncomeType")
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
            fStore.collection("SpendingType")
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

    // Ki???m tra n???i dung ng?????i d??ng nh???p c?? ph???i l?? email kh??ng.
    // True l?? email, False kh??ng l?? email.
    private boolean checkRegisterTextFormat(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Ki???m tra n???i dung ng?????i d??ng nh???p c?? r???ng kh??ng.
    // True l?? r???ng.
//    private boolean checkRegisterTextEmpty(String email, String password, String confirmpassword){
//        return email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty();
//    }

    // Ki???m tra n???u t??i kho???n ???? t???n t???i
    private void checkAccountExist(EmailCallback emailCallback, String email) {
        Log.d(thisTag, "Ti???n h??nh ki???m tra th??ng tin tr??n firebase");
        CollectionReference usersRef = fStore.collection("Accounts");
        Query query = usersRef.whereEqualTo("Email", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(thisTag, "Th??nh c??ng l???y d??? li???u t??? filestore", task.getException());
                    boolean isExist = false;
                    for (DocumentSnapshot document : task.getResult()) {
                        String account = document.getString("Email");
                        if (account.equals(email)) {
                            isExist = true;
                        }
                    }
                    if (isExist)
                        emailCallback.isEmailExist(true);
                    else
                        emailCallback.isEmailExist(false);
                } else {
                    Log.d(thisTag, "Kh??ng th??nh c??ng l???y d??? li???u t??? filestore", task.getException());
                }
            }
        });
    }

    // L??u t??i kho???n l??n firestore
    private void createAccount(String email, String password) {
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Accounts").document(userID);
        // Model
        Map<String, Object> account = new HashMap<>();
        account.put("Email", email);
        account.put("Username", email);
        account.put("Password", md5(password));
        account.put("ID", "");
        account.put("Name", "");
        documentReference.set(account).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(thisTag, "DocumentSnapshot added with ID: " + userID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(thisTag, "Error adding document", e);
            }
        });
    }

    //H??m hash m???t kh???u MD5
    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void anhxa() {
        textView_signin = findViewById(R.id.textView_signin);
        editText_email = findViewById(R.id.editext_email);
        editText_password = findViewById(R.id.edittext_password);
        editText_confirmpassword = findViewById(R.id.edittext_confirmpassword);
        button_register = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progress_bar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
}