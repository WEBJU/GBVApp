package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import adapters.ReportAdapter;
import models.ViolenceReports;

public class UserReportsActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button report;
    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    FirebaseDatabase database;
    FirebaseUser user;
    LinearLayout empty;
    DatabaseReference reportReference;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reports);
        report=findViewById(R.id.reportNow);
        empty=findViewById(R.id.empty);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if (user != null){
            userId=user.getUid();
        }
        reportReference = FirebaseDatabase.getInstance().getReference();
        Query query=reportReference.child("ViolenceReports").orderByChild("user_id").equalTo(userId);
//        reportReference = database.getReference("ViolenceReports").orderByChild("user_id").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ViolenceReports> options = new FirebaseRecyclerOptions.Builder<ViolenceReports>()
                .setQuery(query, ViolenceReports.class)
                .build();
        reportAdapter=new ReportAdapter(options);
        recyclerView.setAdapter(reportAdapter);

    report.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(UserReportsActivity.this,ReportViolenceActivity.class));
        }
    });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reportAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reportAdapter.stopListening();
    }
}