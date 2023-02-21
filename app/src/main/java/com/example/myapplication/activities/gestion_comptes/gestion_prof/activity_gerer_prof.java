package com.example.myapplication.activities.gestion_comptes.gestion_prof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class activity_gerer_prof extends AppCompatActivity {

    Button btn_ajout_prof,btn_list_prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_prof);
        getSupportActionBar().hide();
        btn_ajout_prof =findViewById(R.id.btn_ajout_prof);
        btn_list_prof = findViewById(R.id.btn_lister_prof);

        btn_ajout_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gerer_prof.this, activity_creer_compte_prof.class));
            }
        });
        btn_list_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gerer_prof.this, activity_get_prof.class));
            }
        });
    }
}