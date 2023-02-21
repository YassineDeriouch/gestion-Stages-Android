package com.example.myapplication.activities.gestion_comptes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.activities.activity_admin;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_gerer_prof;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_gerer_respo;
import com.example.myapplication.activities.gestion_etab.activity_gestion_etab;

public class activity_gestion_comptes extends AppCompatActivity {
    public static Button btn_respo;
    public static Button btn_prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_comptes);

        btn_respo = (Button)findViewById(R.id.btn_gest_respo);
        btn_prof = (Button)findViewById(R.id.btn_gest_prof);

        btn_respo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_comptes.this, activity_gerer_respo.class));
            }
        });
        btn_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_comptes.this, activity_gerer_prof.class));

            }
        });
    }
}