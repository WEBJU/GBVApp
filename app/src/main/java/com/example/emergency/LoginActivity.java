package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    ProgressBar progressBar;
    EditText edtEmail,edtPassword;
    Button btnLogin;
    TextView txtExistingMember,txtForgotPassword;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(LoginActivity.this,EmergencyActivity.class));
        }
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);

        btnLogin=findViewById(R.id.btnLogin);
        txtExistingMember=findViewById(R.id.txtRegisterText);
        txtForgotPassword=findViewById(R.id.txtForgot);
        progressBar=findViewById(R.id.progessBar);
        txtExistingMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        email=edtEmail.getText().toString().trim();
        password=edtPassword.getText().toString().trim();
        
        if (TextUtils.isEmpty(email)){
            edtEmail.setError("Email cannot be blank!!");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            edtPassword.setError("Password cannot be blank");
            return;
        }
        else if (password.length() < 6){
            Toast.makeText(this, "Password length should not be less than 6", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(LoginActivity.this,EmergencyActivity.class));
                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Incorrect credentials!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}