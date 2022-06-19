package com.example.money_management.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.money_management.R;
import com.example.money_management.activities.AccountInformationActivity;
import com.example.money_management.activities.IntroductionActivity;
import com.example.money_management.activities.LoginActivity;
import com.example.money_management.activities.QuestionActivity;
import com.example.money_management.activities.SettingActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class AccountFragment extends Fragment {
    private View mView;
    private TextView txtUserName, txtUserEmail;
    private CircleImageView profile;
    private ImageView changeProfile;
    private CardView btnAccountIn;
    private CardView btnSetting;
    private CardView btnIntroduction;
    private CardView btnQuestion;
    private CardView btnLogout;
    private Boolean FirstShow = false;
    private SharedPreferences sharedpreferences; // Để thay đổi dữ trạng thái đăng nhập.
    private final String thisTag = "AccountFragmentTag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView= inflater.inflate(R.layout.fragment_account, container, false);
        mapping();
        sharedpreferences = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Lay thong tin dang nhap
        String email = sharedpreferences.getString("Email", null);
        FirstShow = true;
        syncUserWithFirebase(email);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AccountFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        btnAccountIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AccountInformationActivity.class));
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        btnIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), IntroductionActivity.class));
            }
        });

        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), QuestionActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();

            }

        });
        return mView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = null;
        try {
            uri = data.getData();
            profile.setImageURI(uri);


        }catch (Exception ignored){}
        StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child(FirebaseAuth.getInstance().getUid());

        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase.getInstance().getReference().child("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("userinfo")
                                        .child("image").setValue(uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(getContext() != null)
                                    Toasty.error(getContext(), e.getMessage(), Toasty.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        if(getContext() != null)
                            Toasty.error(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //load ảnh
    private void loadImage(String url)
    {
        if(url != null)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection httpURLConnection = null;
                    try {

                        httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                        Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());

                        if(getActivity() != null)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    profile.setImageBitmap(bitmap);
                                }
                            });
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
    }
    public void Logout(){
        SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
        editor.putString("Email", "");
        editor.commit();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onResume() {
        sharedpreferences = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Lay thong tin dang nhap
        String email = sharedpreferences.getString("Email", null);
        super.onResume();
        if(FirstShow == true){
            FirstShow = false;
            return;
        }
        syncUserWithFirebase(email);
    }

    private void syncUserWithFirebase(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Accounts");
        Query query = usersRef.whereEqualTo("Email", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Lấy dữ liệu user từ firebase
                if(task.isSuccessful()){
                    Log.d(thisTag, "Thành công lấy dữ liệu từ filestore", task.getException());
                    for(QueryDocumentSnapshot document : task.getResult()){
                        txtUserName.setText(document.getString("Username"));
                        txtUserEmail.setText(document.getString("Email"));
                    }

                }
            }
        });
    }

    private void mapping(){
        btnAccountIn = mView.findViewById(R.id.button_accountInfor);
        btnSetting = mView.findViewById(R.id.button_setting);
        btnIntroduction = mView.findViewById(R.id.button_introduction);
        btnQuestion = mView.findViewById(R.id.button_question);
        btnLogout = mView.findViewById(R.id.button_logout);
        txtUserName = mView.findViewById(R.id.user_name);
        txtUserEmail = mView.findViewById(R.id.user_email);
        profile = mView.findViewById(R.id.avatar);
        changeProfile = mView.findViewById(R.id.changeProfile);
    }
}