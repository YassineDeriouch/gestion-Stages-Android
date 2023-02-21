package com.example.myapplication.activities.gestion_comptes.gestion_prof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.myapplication.R;
import com.example.myapplication.activities.Login_PROF_Activity;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_get_doc_by_etd;

public class activity_dashboard_prof extends AppCompatActivity {

    private LinearLayout lprofile, lETD_NIV;
    private CardView cvDocEtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_prof);
        lprofile = findViewById(R.id.ll_profile);
        lETD_NIV = findViewById(R.id.ll_visualiser_ETD_Niv);
        cvDocEtd = findViewById(R.id.prof_get_doc_etd);
        lprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_prof.this, activity_profile_prof.class));
            }
        });
        lETD_NIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_prof.this, activity_get_etd.class));
            }
        });

        cvDocEtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_dashboard_prof.this, activity_get_doc_by_etd.class));
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_reunion_prof_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ReunionProf:
                startActivity(new Intent(activity_dashboard_prof.this, activity_get_reunion_prof.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}