package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText edtEmail;
    TextView tvBack;
    Button btnReset;
    ProgressBar progressBar;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth=FirebaseAuth.getInstance();
        edtEmail=findViewById(R.id.edtEmail);
        tvBack=findViewById(R.id.tvBack);
        btnReset=findViewById(R.id.btnReset);
        progressBar=findViewById(R.id.progessBar);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        email=edtEmail.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be blank!!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "We have sent an email with password reset instructions.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ResetPasswordActivity.this, "Something went wrong.Please check your email and try again!!", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }
}