package com.example.firestorepklearnings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UserRegisterationActivity extends AppCompatActivity {

    ImageView backiconImg;
    private EditText email, password;
    private Button registration;
    private TextView alreadyAccount, txtpasswordStatus, txtEmailStatusMessage,
            txtStatusMessage;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registeration);


                //LINKING WITH XML
                backiconImg = findViewById(R.id.backIconimageView);
                email = findViewById(R.id.emailAddressRegisteration);
                password = findViewById(R.id.passwordRegistration);
                txtpasswordStatus = findViewById(R.id.passwordMessage);
                txtEmailStatusMessage = findViewById(R.id.emailMessageStatus);
                txtStatusMessage = findViewById(R.id.message);
                alreadyAccount = findViewById(R.id.textViewAlreadyRegistered);
                registration = findViewById(R.id.buttonSubmitRegistration);


                //INSTANCE VARIABLE
                firebaseAuth = FirebaseAuth.getInstance();   //ABSTRACT CLASS


                //BACKICON IMAGE ON CLICK
                backiconImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });

                //ALREADY HAVE AN ACCOUNT INTENT
                alreadyAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


                //REGISTRATION
                registration.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {    //ON CLICK
                        String txtEmail = email.getText().toString();    //TAKING INPUT STORING IT IN String txtEmail VARIABLE
                        String txtPassword = password.getText().toString();

                        Toast.makeText(UserRegisterationActivity.this, txtPassword, Toast.LENGTH_SHORT).show();    // TOAST TO SHOW PASSWORD


                        //CONDITIONS FOR TAKING INPUT


                        if (txtEmail.isEmpty()) {
                            txtStatusMessage.setText("Insert Email Address");

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
                            createUser(txtEmail, txtPassword);
                            txtEmailStatusMessage.setText(" ");
                            txtStatusMessage.setText(" ");
                            txtpasswordStatus.setText(" ");
                        }
                        if (txtEmail.isEmpty() && txtPassword.isEmpty()) {
                            txtStatusMessage.setText("Empty credentials");
                        }
                    }
                });


            }

            private void createUser(String email, String password) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                    firebaseUser.isEmailVerified()
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    txtStatusMessage.setText("verification mail has been sent to your email");

                                }
                            });


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(UserRegisterationActivity.this, LoginActivity.class));
                                    finish();

                                }
                            }, 3000);


                        } else {
                            txtEmailStatusMessage.setText(task.getException().getMessage());
                        }
                    }
                });

            }

        }

