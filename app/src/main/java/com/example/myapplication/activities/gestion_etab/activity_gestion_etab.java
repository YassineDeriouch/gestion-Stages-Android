package com.example.myapplication.activities.gestion_etab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class activity_gestion_etab extends AppCompatActivity {
    private static Button btn_ajout_etab;
    private static Button btn_list_etab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_etab);
        btn_ajout_etab = (Button) findViewById(R.id.btn_ajouter_etab);
        btn_list_etab = findViewById(R.id.btn_lister_etab);
        btn_ajout_etab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_etab.this, activity_ajouter_etab.class));
            }
        });

        btn_list_etab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_etab.this, activity_get_etab.class));
            }
        });
    }
}