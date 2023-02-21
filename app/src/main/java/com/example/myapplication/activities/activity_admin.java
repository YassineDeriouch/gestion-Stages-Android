package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.activity_gestion_comptes;
import com.example.myapplication.activities.gestion_etab.activity_gestion_etab;
import com.example.myapplication.activities.gestion_niv.activity_gestion_niv;

public class activity_admin extends AppCompatActivity {
    public static ImageView btn_compte;
    public static ImageView btn_etab;
    public static ImageView btn_niv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().hide();

        btn_compte = findViewById(R.id.btn_gest_compte);
        btn_etab = findViewById(R.id.btn_gestion_etab);
        btn_niv =  findViewById(R.id.btn_gest_niv);

       btn_etab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
           startActivity(new Intent(activity_admin.this, activity_gestion_etab.class));

           }
       });

    btn_niv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(activity_admin.this, activity_gestion_niv.class));

        }
    });

    btn_compte.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(activity_admin.this, activity_gestion_comptes.class));
        }
    });

}
}
