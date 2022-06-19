package com.example.money_management.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.money_management.R;
import com.example.money_management.fragments.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class AccountInformationActivity extends AppCompatActivity {
    AccountFragment accountFragment = new AccountFragment();
    private TextView txtUserName, txtUserEmail;
    private CardView btnChangeName, btnChangePassword, btnLogout;
    private ImageView btnBack;
    private SharedPreferences sharedpreferences; // Để thay đổi dữ trạng thái đăng nhập.
    private final String thisTag = "AccountInformationTag";
    private CircleImageView imageUser;
    FirebaseUser user;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        //Log.i("Tracking Activity Created", "AccountInformationActivity");
        mapping();

        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Lay thong tin dang nhap
        String email = sharedpreferences.getString("Email", null);
        syncUserWithFirebase(email);

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChangeNameActivity.class));
                finish();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                editor.putString("Email", "");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }

    //load ảnh


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(requestCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                imageUser.setImageURI(imageUri);

                updateImageToFireBase(imageUri);
            }
        }
    }

    private void updateImageToFireBase(Uri imageUri) {
        //upload image to firebase storage
        StorageReference fileRef = storageReference.child("profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toasty.success(getApplicationContext(), "Ảnh đã được tải lên!",Toasty.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(getApplicationContext(), "Tải ảnh lên thất bại!",Toasty.LENGTH_SHORT).show();
            }
        });
    }

    // load dữ liệu User từ firebase lên giao diện
    private void syncUserWithFirebase(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Accounts");
        Query query = usersRef.whereEqualTo("Email", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //Lấy dữ liệu user từ firebase
                            if(task.isSuccessful()){
                                //Log.d(thisTag, "Thành công lấy dữ liệu từ filestore", task.getException());
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    txtUserName.setText(document.getString("Username"));
                                    txtUserEmail.setText(document.getString("Email"));
                                }

                            }
                        }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.i("Reload data user", "AccountInformationActivity");
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Lay thong tin dang nhap
        String email = sharedpreferences.getString("Email", null);
        SystemClock.sleep(1000);
        syncUserWithFirebase(email);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    private void mapping(){
        txtUserName = findViewById(R.id.user_name);
        txtUserEmail = findViewById(R.id.user_email);
        btnChangeName = findViewById(R.id.button_changeName);
        btnChangePassword = findViewById(R.id.button_change_password);
        btnLogout = findViewById(R.id.button_logout);
        btnBack =  findViewById(R.id.button_back);
        imageUser = findViewById(R.id.avatar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }


}