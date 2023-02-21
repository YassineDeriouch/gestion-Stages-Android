package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_profile_prof;

public class activity_professeur extends AppCompatActivity {
// dashboard
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professeur);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_prof_meny_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
startActivity(new Intent(activity_professeur.this, activity_profile_prof.class));
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}