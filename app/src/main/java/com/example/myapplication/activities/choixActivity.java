package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class choixActivity extends AppCompatActivity {

    private Button admin,etd,respo,prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
        admin = findViewById(R.id.adminBtn);
        etd=findViewById(R.id.etdBtn);
        respo=findViewById(R.id.respoBtn);
        prof=findViewById(R.id.profBtn);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(choixActivity.this, Login_ADMIN_Activity.class));
            }
        });

        etd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(choixActivity.this, Login_ETD_Activity.class));
            }
        });

        respo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(choixActivity.this, Login_RESPO_Activity.class));
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(choixActivity.this, Login_PROF_Activity.class));
            }
        });
    }
}