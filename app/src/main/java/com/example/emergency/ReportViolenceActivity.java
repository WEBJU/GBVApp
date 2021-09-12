package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.ViolenceReports;

public class ReportViolenceActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference reportReference;
    EditText edtDescription,edtLocation,edtPhone;
    CheckBox cbTerms;
    TextView display;
    ProgressBar progressBar;
    Button btnReport;
    Spinner spViolenceType;
    Toolbar toolbar;
    String violenceType,description,location,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_violence);
//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        reportReference=database.getReference("ViolenceReports");
        spViolenceType=findViewById(R.id.spViolence);
        edtDescription=findViewById(R.id.edtDescription);
        edtLocation=findViewById(R.id.edtLocation);
        edtPhone=findViewById(R.id.edtPhone);
        cbTerms=findViewById(R.id.cbTerms);
        display=findViewById(R.id.display);
        display.setText("Hi, "+user.getDisplayName());
        btnReport=findViewById(R.id.btnReport);

        progressBar=findViewById(R.id.progessBar);

        String [] violenceType={"Select Violence Type",
                "Domestic violence","Intimate partner violence","Sexual violence"};

        ArrayAdapter gbvTypeAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,violenceType);
        gbvTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spViolenceType.setAdapter(gbvTypeAdapter);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportViolence();
            }
        });

    }

    private void reportViolence() {
        violenceType=spViolenceType.getSelectedItem().toString();
        description=edtDescription.getText().toString().trim();
        location=edtLocation.getText().toString().trim();
        phone=edtPhone.getText().toString().trim();
        String uid=null;
        if (auth.getCurrentUser() != null){
            uid=auth.getCurrentUser().getUid();
        }
        if (TextUtils.isEmpty(violenceType)){
            Toast.makeText(this, "Please indicate violence type..", Toast.LENGTH_SHORT).show();
            return;
        }
        else  if (TextUtils.isEmpty(description)){
            edtDescription.setError("Enter Description");
        }
        else  if (TextUtils.isEmpty(location)){
            edtDescription.setError("Enter Location");
        }
        else if (TextUtils.isEmpty(phone)){
            edtPhone.setError("Enter phone");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            ViolenceReports report=new ViolenceReports(violenceType,description,location,phone,uid);
            reportReference.push().setValue(report);
            reportReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ReportViolenceActivity.this, "Your report has been submitted successfully!!", Toast.LENGTH_SHORT).show();
                    edtDescription.setText("");
                    edtLocation.setText("");
                    edtPhone.setText("");
                    return;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ReportViolenceActivity.this, "Error occurred while reporting!!", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id=item.getItemId();
//
//        if (id == R.id.profile){
//            startActivity(new Intent(ReportViolenceActivity.this,ProfileActivity.class));
//        }
//
//        if (id == R.id.logout){
//            auth.signOut();
//            Toast.makeText(this, "You are now logged out!!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(ReportViolenceActivity.this,LoginActivity.class));
//        }
//        if (id == R.id.emergency){
//            startActivity(new Intent(ReportViolenceActivity.this,UserReportsActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

}