package com.example.money_management.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.money_management.R;
import com.example.money_management.activities.AccountInformationActivity;
import com.example.money_management.activities.IntroductionActivity;
import com.example.money_management.activities.LoginActivity;
import com.example.money_management.activities.QuestionActivity;
import com.example.money_management.activities.SettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AccountFragment extends Fragment {
    private View mView;
    private TextView txtUserName, txtUserEmail;
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
    }
}