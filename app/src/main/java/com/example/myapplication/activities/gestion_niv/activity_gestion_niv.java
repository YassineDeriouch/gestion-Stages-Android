package com.example.myapplication.activities.gestion_niv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;


public class activity_gestion_niv extends AppCompatActivity {
    public static Button btn_ajout_niv;
    public static Button btn_supp_niv;
    public static Button btn_lister_niv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_niv);

        getSupportActionBar().hide();

        btn_ajout_niv = (Button) findViewById(R.id.btn_ajouter_niv);
        btn_lister_niv = findViewById(R.id.btn_list_niv);

        btn_ajout_niv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_niv.this, activity_ajouter_niv.class));
            }
        });


        btn_lister_niv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_niv.this, activity_get_niv.class));

            }
        });

    }
}
