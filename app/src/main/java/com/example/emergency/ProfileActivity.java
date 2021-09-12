package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.Profile;

public class ProfileActivity extends AppCompatActivity {
    EditText edtName,edtEmail,edtPhone,edtAddress,edtKin,edtKinContact,edtRelationship;
    String name,userId,phone,address,kin,kinContact,relationship;
    Button updateProfile;
    TextView profile;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference profileRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        edtName=findViewById(R.id.name);
        edtEmail=findViewById(R.id.email);
        edtPhone=findViewById(R.id.phone);
        edtAddress=findViewById(R.id.address);
        edtKin=findViewById(R.id.kin);
        edtKinContact=findViewById(R.id.contacts);
        edtRelationship=findViewById(R.id.relationship);

        updateProfile=findViewById(R.id.btnUpdate);
        progressBar=findViewById(R.id.progessBar);
        profile=findViewById(R.id.profile);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        profileRef=database.getReference("Profile");
        edtEmail.setText(user.getEmail());
        edtName.setText(user.getDisplayName());
        populateProfile();
        profile.setText("Welcome, "+user.getDisplayName());

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void populateProfile() {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Profile profile=dataSnapshot.getValue(Profile.class);
                        edtPhone.setText(profile.getPhone());
                        edtAddress.setText(profile.getAddress());
                        edtKinContact.setText(profile.getKinContact());
                        edtRelationship.setText(profile.getRelationship());
                        edtKin.setText(profile.getKin());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Please update your profile!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
//        name=edtName.getText().toString().trim();
        userId=user.getUid();
        phone=edtPhone.getText().toString().trim();
        address=edtAddress.getText().toString().trim();
        kin=edtKin.getText().toString().trim();
        kinContact=edtKinContact.getText().toString().trim();
        relationship=edtRelationship.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            edtPhone.setError("Enter phone number");
        }else if(TextUtils.isEmpty(address)){
            edtAddress.setError("Enter address");
        }else if(TextUtils.isEmpty(kin)){
            edtKin.setError("Enter Kin name");
        }
        else if(TextUtils.isEmpty(kinContact)){
            edtKinContact.setError("Enter Kin contact");
        }
        else if(TextUtils.isEmpty(relationship)){
            edtAddress.setError("Enter relationship");
        }else{
            progressBar.setVisibility(View.VISIBLE);
            Profile profile=new Profile(userId,phone,address,kin,kinContact,relationship);
            profileRef.push().setValue(profile);
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Your profile has been updated successfully!!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Something went wrong!!Please try again..", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}