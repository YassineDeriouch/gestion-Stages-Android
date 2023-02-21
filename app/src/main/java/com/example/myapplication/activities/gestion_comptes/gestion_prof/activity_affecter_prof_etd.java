package com.example.myapplication.activities.gestion_comptes.gestion_prof;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.SpinnerHelper.ProfesseurAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_compte_respo;
import com.example.myapplication.activities.gestion_niv.ListViewHelper.NiveauAdapter;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_affecter_prof_etd extends AppCompatActivity {

    private static int selectedId = 0;
    public Button btn_affecter_prof_etd;
    public EditText id_etds, idProf;
    Professeur professeur = new Professeur();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affecter_prof_etd);
        id_etds = findViewById(R.id.id_etd_affect);
        idProf = findViewById(R.id.id_prof);
        btn_affecter_prof_etd = findViewById(R.id.btn_affecter);
        String ide = id_etds.getText().toString();

        try {
            int idET = Integer.parseInt(ide);
        } catch (NumberFormatException nfe) {
            // Handle the condition when str is not a number.
        }

        btn_affecter_prof_etd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                affectation();
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




    public void affectation() {
        Retrofit_Instance retrofitRespo = new Retrofit_Instance();
        Api_Calls_Interface apiProf = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);
        int idP = Integer.parseInt(idProf.getText().toString());
        int idE = Integer.parseInt(id_etds.getText().toString());

        professeur.setId(idP);
        getETDbyId(idE);
        apiProf.affecterPROF_ETD(professeur,idP).enqueue(new Callback<Professeur>() {
            @Override
            public void onResponse (Call <Professeur> call, Response <Professeur> response){
                System.out.println("affectation "+response.body());
                if (response.isSuccessful()) {
                    //getETDbyId(idE);
                    Toast.makeText(activity_affecter_prof_etd.this, "Affectation éffectué", Toast.LENGTH_LONG).show();
                }

                if (response.code() == 404) {
                    Toast.makeText(activity_affecter_prof_etd.this, "La liste est vide !", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(activity_affecter_prof_etd.this, "Erreur", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure (Call <Professeur> call, Throwable t){
                Toast.makeText(activity_affecter_prof_etd.this, "erreur serveur", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getETDbyId(int selectedId){
        Retrofit_Instance retrofitRespo = new Retrofit_Instance();
        Api_Calls_Interface apiProf = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);
        int idET = Integer.parseInt(id_etds.getText().toString());
        selectedId = idET;
        List<Etudiant> le = new ArrayList<>();
        apiProf.FindEtudiantbyId(selectedId).enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response.body());
                    le.add(response.body());
                    professeur.setIdE(le);

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
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                Logger.getLogger(activity_creer_compte_respo.class.getName())
                        .log(Level.SEVERE, " ERROR OCCURED", t);
            }
        });

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        int pos = parent.getSelectedItemPosition();
        selectedId = ProfesseurAdapter.profList.get(position).getId();


        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.affecter_afficher_etds_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.listerEtds:
                startActivity(new Intent(activity_affecter_prof_etd.this, activity_get_etd.class));
                return true;
            case R.id.listerProfs:
                startActivity(new Intent(activity_affecter_prof_etd.this, activity_get_prof.class));

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}