package com.example.myapplication.activities.gestion_etab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entities.Etablissement;
import com.example.myapplication.R;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_ajouter_etab extends AppCompatActivity {
    public static EditText nom, desc, idRes;
    public static Button btn_valider;
    Etablissement etab = new Etablissement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_etab);

        getSupportActionBar().hide();

        nom = (EditText) findViewById(R.id.nom_etab);
        desc = (EditText) findViewById(R.id.desc_etab);
        idRes = findViewById(R.id.idResp_Etab);
        btn_valider = (Button) findViewById(R.id.btn_valider_ajout_etab);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nom.getText().toString();
                String description = desc.getText().toString();
                int idResponsable = Integer.parseInt(idRes.getText().toString());

                etab.setNom(name);
                etab.setDescription(description);
                etab.setIdResponsable(idResponsable);

                Retrofit_Instance retrofitCompte = new Retrofit_Instance();
                Api_Calls_Interface apiCallsInterface =retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

                apiCallsInterface.save(etab).enqueue(new Callback<Etablissement>() {
                    @Override
                    public void onResponse(Call<Etablissement> call, Response<Etablissement> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Etablissement ajouté avec succès", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Etablissement> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erreur save Etablissement", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(activity_ajouter_etab.class.getName())
                                .log(Level.SEVERE, " ERROR OCCURED",t);
                    }
                });
                }
        });
    }
}

