package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.Entities.Reunion;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.SpinnerHelper.ProfesseurAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_affecter_prof_etd;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_creer_reunion extends AppCompatActivity {

    private static int selectedId = 0;
    public Button btn_affecter_prof_etd;
    public static EditText id_etd;
    public static EditText id_prof;
    public static EditText libelle,salle,date;
    public Button btnValiderReunion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_reunion);
        id_prof = findViewById(R.id.id_prof_reunion);
        id_etd = findViewById(R.id.id_etd_reunion);
        libelle = findViewById(R.id.libelle_reunion);
        salle = findViewById(R.id.salle_reunion);
        date = findViewById(R.id.date_reunion);
        btnValiderReunion = findViewById(R.id.btn_creer_reunion);

        btnValiderReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organiserReunion();
            }
        });
    }

    private ProgressDialog progressDialog;
    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

        public void organiserReunion(){
            Reunion reunion = new Reunion();
            int idE = Integer.parseInt(id_etd.getText().toString());
            int idP = Integer.parseInt(id_prof.getText().toString());
            String lib = libelle.getText().toString();
            String sal = salle.getText().toString();
            String dateR = date.getText().toString();

            Retrofit_Instance retrofit_instance = new Retrofit_Instance();
            Api_Calls_Interface apiCallsInterface =retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

            reunion.setIdE(idE);
            reunion.setIdP(idP);
            reunion.setLibelle(lib);
            reunion.setSalle(sal);
            reunion.setDate(dateR);

            Retrofit_Instance retrofitRespo = new Retrofit_Instance();
            Api_Calls_Interface apiProf = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);

            apiProf.organiserReunion(reunion,idE,idP).enqueue(new Callback<Reunion>() {
                @Override
                public void onResponse(Call<Reunion> call, Response<Reunion> response) {
                    if (response.isSuccessful()) {
                        System.out.println("****response is: " + response);
                        Toast.makeText(getApplicationContext(), "reunion créée avec succès !", Toast.LENGTH_LONG).show();
                    } switch (response.code()){
                        case 404:
                            Toast.makeText(getApplicationContext(), "Uitlisateur introuvable !", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "Erreur lors de la recuperation de vos données ! réssayez", Toast.LENGTH_LONG).show();
                            break;
                    }

                }

                @Override
                public void onFailure(Call<Reunion> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                    Logger.getLogger(activity_creer_reunion.class.getName())
                            .log(Level.SEVERE, " ERROR OCCURED", t);
                }
            });

        }


        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.affecter_afficher_etds_toolbar, menu);
            return super.onCreateOptionsMenu(menu);
        }
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.listerEtds:
                    startActivity(new Intent(activity_creer_reunion.this, activity_get_etd.class));
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

        }
    }
