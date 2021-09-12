package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.User;
import session.Session;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;
    private  Session session;
    FirebaseDatabase database;
    DatabaseReference userReference;
    FirebaseUser user;
    Context context;
    EditText edtEmail,edtName,edtPassword,edtConfirmPassword;
    TextView txtAlreadyMember;
    ProgressBar progressBar;
    Button btnRegister;
    String email,name,password,confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user=FirebaseAuth.getInstance().getCurrentUser();
        session=new Session(getApplicationContext());
        if (user != null){
            startActivity(new Intent(RegisterActivity.this,EmergencyActivity.class));
        }
        edtEmail=findViewById(R.id.edtEmail);
        edtName=findViewById(R.id.edtName);
        edtPassword=findViewById(R.id.edtPassword);
        edtConfirmPassword=findViewById(R.id.edtConfirm);

        txtAlreadyMember=findViewById(R.id.txtRegisteredMember);
        progressBar=findViewById(R.id.progessBar);
        btnRegister=findViewById(R.id.btnRegister);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        userReference=database.getReference("Users");

        txtAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        name=edtName.getText().toString().trim();
        email=edtEmail.getText().toString().trim();
        password=edtPassword.getText().toString().trim();
        confirm=edtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            edtName.setError("Name cannot be blank");
            return;
        }
        else if (TextUtils.isEmpty(email)){
            edtEmail.setError("Email cannot be blank!!");
        }
        else if (TextUtils.isEmpty(password)){
            edtPassword.setError("Password cannot be blank!!");
        }
        else if (TextUtils.isEmpty(confirm)){
            edtConfirmPassword.setError("Please confirm your password!!");
        }
        else if (!password.equals(confirm)){
            edtConfirmPassword.setError("Passwords do not match");
        }
        else if (password.length() < 6){
            edtEmail.setError("Password length should not be less than 6");
        }
        else {
            session.setEmail(email);
            session.setName(name);
            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(name)
                                .build();
                        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Account setup successfully!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

//                        User user=new User(name);
//                        userReference.child()
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                    else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(RegisterActivity.this, "User with this email already exists!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Something went wrong!!Please try again."+task.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }
}