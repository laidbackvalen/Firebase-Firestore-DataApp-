package com.example.firestorepklearnings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestorepklearnings.util.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginbutton;
    private TextView txtWelcomeBackForTestingLayout, registerUser, forgetpassword, txtpasswordStatus, txtEmailStatusMessage, txtStatusMessage;
    FirebaseAuth firebaseAuth;   //abstract class
    //    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


                email = findViewById(R.id.editTextEmailAddress);    //linking this with XML
                password = findViewById(R.id.editTextTextPassword);
                registerUser = findViewById(R.id.textReturnToLoginPage);
                forgetpassword = findViewById(R.id.textViewForgetPassword);
                loginbutton = findViewById(R.id.loginButton);
                txtpasswordStatus = findViewById(R.id.passwordMessage);
                txtEmailStatusMessage = findViewById(R.id.emailMessageStatus);
                txtStatusMessage = findViewById(R.id.messageStatus);

                //Testing
//        txtWelcomeBackForTestingLayout = findViewById(R.id.textWelcomeBack);
//        txtWelcomeBackForTestingLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, SavingDataInFirebaseRealtimeDatabase.class));
//            }
//        });


                //instance variable
                firebaseAuth = FirebaseAuth.getInstance();


                //CREATE A NEW USER
                registerUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, UserRegisterationActivity.class));
                         finish();
                    }
                });


                loginbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtEmail = email.getText().toString();
                        String txtPassword = password.getText().toString();

//                Toast.makeText(LoginActivity.this,txtEmail, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, txtPassword, Toast.LENGTH_SHORT).show();


                        if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
//                    if (txtEmail.isEmpty()) {
//                        txtStatusMessage.setText("Enter Email Address");
//                    } else if (txtPassword.isEmpty()) {
//                        txtStatusMessage.setText("Enter Password");
//                    } else {
//                        txtStatusMessage.setText("Empty credentials");
//                    }
//
//                    txtEmailStatusMessage.setText("www.indianrocks@example.com");
                        }
                        if (txtEmail.isEmpty()) {
                            txtStatusMessage.setText("Insert Email Address");
                            txtStatusMessage.setText("Empty credentials");

                        } else if (txtPassword.isEmpty()) {
                            txtStatusMessage.setText("Insert Password");
                        } else {
                            txtStatusMessage.setText(" ");
                        }


                        if (txtEmail.length() < 7) {
                            txtEmailStatusMessage.setText("www.humanbeing@example.com");
                        } else if (!(txtEmail.endsWith(".com"))) {
                            txtEmailStatusMessage.setText("email must ends with .com/org");
                        } else {
                            txtEmailStatusMessage.setText(" ");
                        }

                        if (txtPassword.contains(" ")) {
                            txtpasswordStatus.setText("Spaces are not allowed");
                        } else if ((txtPassword.length() < 6) || (txtPassword.length() >= 15)) {
                            txtpasswordStatus.setText("Minimum of 6 characters is required, Maximum 15");

                        } else if ((txtPassword.length() < 6) || (txtPassword.length() >= 15)) {
                            txtpasswordStatus.setText("Minimum of 6 characters is required, Maximum 15");
                        } else if (txtPassword.contains(" ")) {
                            txtpasswordStatus.setText("Spaces are not allowed");
                        } else if (!(txtPassword.contains("@") || txtPassword.contains("#")
                                || txtPassword.contains("!") || txtPassword.contains("~")
                                || txtPassword.contains("$") || txtPassword.contains("%")
                                || txtPassword.contains("^") || txtPassword.contains("&")
                                || txtPassword.contains("*") || txtPassword.contains("(")
                                || txtPassword.contains(")") || txtPassword.contains("-")
                                || txtPassword.contains("+") || txtPassword.contains("/")
                                || txtPassword.contains(":") || txtPassword.contains(".")
                                || txtPassword.contains(", ") || txtPassword.contains("<")
                                || txtPassword.contains(">") || txtPassword.contains("?")
                                || txtPassword.contains("|"))) {
                            txtpasswordStatus.setText("at least 1 special character is required");
                        } else {
                            userSignIn(txtEmail, txtPassword);

                            txtpasswordStatus.setText(" ");
                            txtStatusMessage.setText(" ");
                            txtEmailStatusMessage.setText(" ");
                        }
                    }
                });

            }

            private void userSignIn(String email, String password) {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                        assert user != null;
                            FirebaseUser user = task.getResult().getUser();
                            if (task.getResult().getUser().isEmailVerified()) {

                                txtStatusMessage.setText("User SignIn Successfully");
                                startActivity(new Intent(LoginActivity.this, AddingLocationActivity.class));
                                finish();

                                FirebaseUtil.currentUserDetails().addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                   if (value.exists()){
                                       startActivity(new Intent(getApplicationContext(), RetrievingDataFirestore.class));
                                       finish();
                                   }
                                    }
                                });


                            } else {
                                txtStatusMessage.setText("Email Not Verified");
                            }
                        } else {
                            txtStatusMessage.setText("Invalid Login Credentials");
                        }

                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG_name", "onFailure: " + e.getMessage());
                    }
                });
            }
        }



