package com.example.myapplication.activities.gestion_niv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.R;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_ajouter_niv extends AppCompatActivity {
    public static Button btn_valider_ajout_niv;
    public static EditText filiere;
    public static Spinner spinner;

    public static EditText nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_niv);

        getSupportActionBar().hide();

        filiere = (EditText) findViewById(R.id.nom_filiere);
        btn_valider_ajout_niv = (Button) findViewById(R.id.btn_ajout_niv);
        Spinner s = (Spinner) findViewById(R.id.spinner_niveau);

        String[] arraySpinner = new String[] { "1", "2", "3", "4", "5" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setPrompt("Niveau");



        btn_valider_ajout_niv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Niveau niveau = new Niveau();
                String nomFiliere = filiere.getText().toString();
                String niv = s.getSelectedItem().toString();

                niveau.setNomFiliere(nomFiliere);
                niveau.setNiveau(niv);
//*********************************************
                Retrofit_Instance retrofitCompte = new Retrofit_Instance();
                Api_Calls_Interface apiCallsInterface =retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

                apiCallsInterface.save(niveau).enqueue(new Callback<Niveau>() {
                    @Override
                    public void onResponse(Call<Niveau> call, Response<Niveau> response) {
                        System.out.println("response: "+ response.code());
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "niveau ajouté avec succès", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Niveau> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erreur save Niveau", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(activity_ajouter_niv.class.getName())
                                .log(Level.SEVERE, " ERROR OCCURED",t);
                    }
                });
//*********************************************


            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> parent) {
    }

}