package com.example.firestorepklearnings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.firestorepklearnings.util.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddingLocationActivity extends AppCompatActivity {

    TextInputEditText inputEditTextCity, inputEditTextState, inputEditTextCountry;
    Button nextSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_location);

        inputEditTextCity = findViewById(R.id.enterCityTextView);
        inputEditTextState = findViewById(R.id.enterStateTextView);
        inputEditTextCountry = findViewById(R.id.enterCountryTextView);
        nextSubmitButton = findViewById(R.id.nextSubmitButton);

        nextSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = inputEditTextCity.getText().toString();
                String state = inputEditTextState.getText().toString();
                String country = inputEditTextCountry.getText().toString();

                if (city.isEmpty()) {
                    inputEditTextCity.setError("Invalid");
                } else if (state.isEmpty()) {
                    inputEditTextState.setError("Invalid");
                } else if (country.isEmpty()) {
                    inputEditTextCountry.setError("invalid");
                } else {
                    addingUserData(city, state, country);
                }
            }
        });


    }

    private void addingUserData(String city, String state, String country) {

        ModelClassFirestore modelClassFirestore = new ModelClassFirestore(city, state, country);

//WE WILL ADD HIS DATA WITHOUT USER INPUT //STATIC //EXAMPLE OF DYNAMIC Line No. 81

//        Map<String, Object> city = new HashMap<>();
//        city.put("name","valsad");
//        city.put("State","gujarat");
//        city.put("Country","india");


        //this way you can add data to the firestore data or go to line number 78, code starts from LNo.81

//        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
//
//        firestoreDB.collection("users").document("location").set(modelClassFirestore).
//                addOnCompleteListener(new OnCompleteListener<Void>() {


        //by creating FirebaseUtil class in util package under 'com.example.firestorepklearnings'  and creating a function currentUserDetails() which will get or return FirebaseFirestore.getInstance();

        //USER WILL PROVIDE THE DATA AS INPUT //DYNAMIC
        FirebaseUtil.currentUserDetails().set(modelClassFirestore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddingLocationActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddingLocationActivity.this, AddingNameContactAndEmail.class);
                    intent.putExtra("city", inputEditTextCity.getText().toString());
                    intent.putExtra("state", inputEditTextState.getText().toString());
                    intent.putExtra("country", inputEditTextCountry.getText().toString());

                    startActivity(intent);
                    finish();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddingLocationActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}