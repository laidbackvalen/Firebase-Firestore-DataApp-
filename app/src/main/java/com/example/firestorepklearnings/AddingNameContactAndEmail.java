package com.example.firestorepklearnings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.StringPrepParseException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firestorepklearnings.util.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class AddingNameContactAndEmail extends AppCompatActivity {
    TextInputEditText inputEditTextName, inputEditTextPhoneNumber, inputEditTextEmail;
    Button submitInputButton;
    ImageView proPicImgView;
    String url;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_name_contact_and_email);

        inputEditTextName = findViewById(R.id.enterNameTextView);
        inputEditTextPhoneNumber = findViewById(R.id.enterPhoneTextView);
        inputEditTextEmail = findViewById(R.id.enterEmailTextView);
        submitInputButton = findViewById(R.id.submitButton);
        proPicImgView = findViewById(R.id.profilePictureImgCardView);

        proPicImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch("image/*"); // "*/*" //for any types of files

            }
        });

        submitInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputEditTextName.getText().toString();
                String number = inputEditTextPhoneNumber.getText().toString();
                email = inputEditTextEmail.getText().toString();

                String city = getIntent().getExtras().getString("city");
                String state = getIntent().getExtras().getString("state");
                String country = getIntent().getExtras().getString("country");

                if (name.isEmpty()) {
                    inputEditTextName.setError("Invalid");
                } else if (number.isEmpty()) {
                    inputEditTextPhoneNumber.setError("Invalid");
                } else if (email.isEmpty()) {
                    inputEditTextEmail.setError("invalid");
                } else {
                    mergeWithExistingData(city, state, country, name, number, email, url);
                }
            }
        });


    }

    private void mergeWithExistingData(String city, String state, String country, String name, String number, String email, String url) {

        ModelClassFirestore modelClassFirestore = new ModelClassFirestore(city, state, country, name, number, email, url);
        FirebaseUtil.currentUserDetails().set(modelClassFirestore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddingNameContactAndEmail.this, "Added Successfully" + task.getResult(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RetrievingDataFirestore.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddingNameContactAndEmail.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), results -> {

        //i have created this medthod in FirebaseUtil class
        FirebaseUtil.firebasestorage().getReference().child("images")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child("IMG_" + System.currentTimeMillis())
                .putFile(results)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //global variable
                                    url = uri.toString();
                                    Toast.makeText(AddingNameContactAndEmail.this, "" + url, Toast.LENGTH_SHORT).show();
                                    proPicImgView.setImageURI(results);
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddingNameContactAndEmail.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });

    });
}