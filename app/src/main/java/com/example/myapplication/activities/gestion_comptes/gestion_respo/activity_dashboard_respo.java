package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.activities.activity_etudiant;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_gestion_etudiant;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_profile_etd;

public class activity_dashboard_respo extends AppCompatActivity {

    public CardView cv_list_etd_niv, cv_docs_fromETDs, cv_profile_respo, cv_rapportEtat, cv_stats, cv_other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard_respo);
        cv_profile_respo = findViewById(R.id.cv_profile_respo);
        cv_docs_fromETDs = findViewById(R.id.cv_visualiser_docsFromEtd);
        cv_list_etd_niv = findViewById(R.id.cv_visualiser_ETD_Niv);
        cv_rapportEtat = findViewById(R.id.cv_telecharger_rapportEtat);
        cv_stats = findViewById(R.id.cv_stat);
        cv_other = findViewById(R.id.cv_autre_respo);

        cv_docs_fromETDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_respo.this, activity_get_doc_by_etd.class));
            }
        });

        cv_profile_respo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_respo.this, activity_profile_Respo.class));
            }
        });
        cv_list_etd_niv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_respo.this, activity_get_etd.class));
            }
        });
        cv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_respo.this, activity_gestion_etudiant.class));
            }
        });
    }
}