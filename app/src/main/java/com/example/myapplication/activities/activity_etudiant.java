package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_reunion_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_import_doc_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_profile_etd;

//login
public class activity_etudiant extends AppCompatActivity {

    private CardView cv_upload_doc_ETD, cv_download_doc_ETD, cv_profile_ETD;
    private Button btn_reunion_etd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        getSupportActionBar().hide();

        cv_upload_doc_ETD = findViewById(R.id.cv_upload_doc_etd);
        cv_profile_ETD = findViewById(R.id.cv_profile_etd);
        cv_download_doc_ETD = findViewById(R.id.cv_download_doc_etd);
        btn_reunion_etd =findViewById(R.id.btn_etd_reunion);

        btn_reunion_etd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_etudiant.this, activity_get_reunion_etd.class));
            }
        });

        cv_profile_ETD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_etudiant.this, activity_profile_etd.class));
            }
        });
        cv_upload_doc_ETD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_etudiant.this, activity_import_doc_etd.class));
            }
        });

    }
}