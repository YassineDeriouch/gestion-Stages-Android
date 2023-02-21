package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.activity_gestion_comptes;

public class activity_gerer_respo extends AppCompatActivity {
    public static Button btn_ajout_respo;
    public static Button btn_affich_respo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_respo);
        getSupportActionBar().hide();
        btn_affich_respo= (Button) findViewById(R.id.btn_affich_respo);
        btn_ajout_respo = (Button) findViewById(R.id.btn_ajout_respo);

        btn_ajout_respo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(new Intent(activity_gerer_respo.this, activity_creer_compte_respo.class));

            }
        });

        btn_affich_respo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gerer_respo.this, activity_get_respo.class));
            }
        });
    }
}