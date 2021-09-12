package com.example.emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class EmergencyActivity extends AppCompatActivity {
    CardView helpline,alert,guide;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        helpline=findViewById(R.id.helpline);
        alert=findViewById(R.id.history);
        guide=findViewById(R.id.guide);
        auth=FirebaseAuth.getInstance();
        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyActivity.this,HelpActivity.class));
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyActivity.this,UserReportsActivity.class));
            }
        });
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyActivity.this,ReportViolenceActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (id == R.id.profile){
            startActivity(new Intent(EmergencyActivity.this,ProfileActivity.class));
        }

        if (id == R.id.logout){
            auth.signOut();
            Toast.makeText(this, "You are now logged out!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EmergencyActivity.this,LoginActivity.class));
        }
//        if (id == R.id.emergency){
//            startActivity(new Intent(EmergencyActivity.this,UserReportsActivity.class));
//        }
        return super.onOptionsItemSelected(item);
    }
}