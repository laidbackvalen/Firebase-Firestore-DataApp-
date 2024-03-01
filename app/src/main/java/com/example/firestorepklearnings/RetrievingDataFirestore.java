package com.example.firestorepklearnings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firestorepklearnings.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class RetrievingDataFirestore extends AppCompatActivity {
    TextView retrieveNameTextView, retrievePhoneTextView, retrieveEmailTextView,
            retrieveCityTextView, retrieveStateTextView, retrieveCountryTextView;
    Button logOutButton;
    ImageView proPicImgView;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieving_data_firestore);

        retrieveNameTextView = findViewById(R.id.textViewName);
        retrievePhoneTextView = findViewById(R.id.textViewPhone);
        retrieveEmailTextView = findViewById(R.id.textViewEmail);
        retrieveCityTextView = findViewById(R.id.textViewCity);
        retrieveStateTextView = findViewById(R.id.textViewState);
        retrieveCountryTextView = findViewById(R.id.textViewCountry);
        logOutButton = findViewById(R.id.logoutButton);
        proPicImgView = findViewById(R.id.profilePictureImageView);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


        retrieveData(retrieveNameTextView, retrievePhoneTextView, retrieveEmailTextView, retrieveCityTextView, retrieveStateTextView, retrieveCountryTextView, proPicImgView);
    }

//    private void retrieveData(TextView retrieveNameTextView, TextView retrievePhoneTextView, TextView retrieveEmailTextView, TextView retrieveCityTextView, TextView retrieveStateTextView, TextView retrieveCountryTextView) {
//
//        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot dataSnapshot = task.getResult();
//                    if (dataSnapshot.exists()){
//                        Toast.makeText(RetrievingDataFirestore.this, ""+dataSnapshot.getData().toString(), Toast.LENGTH_SHORT).show();
////                        Log.d("TAG_data", dataSnapshot.getData().toString());
//                    }else{
//                        Log.d("TAG", "no data");
//                        Toast.makeText(RetrievingDataFirestore.this, "No Data", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        });
//    }

    private void retrieveData(TextView retrieveNameTextView, TextView retrievePhoneTextView, TextView retrieveEmailTextView, TextView retrieveCityTextView, TextView retrieveStateTextView, TextView retrieveCountryTextView, ImageView proPicImgView) {

        FirebaseUtil.currentUserDetails().addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {

//                    Map<String, Object> map =   value.getData();
//                    map.get("name");
//                    retrieveNameTextView.setText(map.get("name").toString());


                    String name = value.getString("name");
                    retrieveNameTextView.setText(name);
                    String phone = value.getString("phone");
                    retrievePhoneTextView.setText(phone);
                    email = value.getString("email");
                    retrieveEmailTextView.setText(email);
                    String city = value.getString("city");
                    retrieveCityTextView.setText(city);
                    String state = value.getString("state");
                    retrieveStateTextView.setText(state);
                    String country = value.getString("country");
                    retrieveCountryTextView.setText(country);
                    String url = value.getString("url");

                    Glide.with(RetrievingDataFirestore.this)
                            .asBitmap()
                            .load(url)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(proPicImgView);
                }

            }
        });
    }


}