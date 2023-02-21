package com.example.myapplication.activities.gestion_comptes.gestion_etd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_ADMIN_Activity;
import com.example.myapplication.activities.activity_admin;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_affecter_prof_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_reunion;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_gestion_etudiant extends AppCompatActivity {
    public Button btn_add_etd,
            btn_affecter_etd_prof,
            btn_reunion,
            btn_gerer_doc_etd,
            btn_list_etd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_etudiant);
        btn_add_etd = findViewById(R.id.btn_add_etd);
        btn_affecter_etd_prof = findViewById(R.id.btn_affecter_etd_prof);
        btn_reunion = findViewById(R.id.btn_reunion);
        btn_list_etd = findViewById(R.id.btn_lister_etd);

        btn_add_etd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_etudiant.this, activity_ajouter_etd.class));
            }
        });

        btn_list_etd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_etudiant.this, activity_get_etd.class));
            }
        });
        btn_affecter_etd_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_etudiant.this, activity_affecter_prof_etd.class));
            }
        });

        btn_reunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_gestion_etudiant.this, activity_creer_reunion.class));

            }
        });
    }

}