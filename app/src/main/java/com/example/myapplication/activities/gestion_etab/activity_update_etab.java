package com.example.myapplication.activities.gestion_etab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entities.Etablissement;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_etab.EtabListViewHelper.EtabAdapter;
import com.example.myapplication.activities.gestion_niv.activity_ajouter_niv;
import com.example.myapplication.activities.gestion_niv.activity_get_niv;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_update_etab extends AppCompatActivity {

    public EditText nomEtabUpdateView, descEtabUpdateView, idResEtabUpdateView;
    public Button btn_valider_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_etab);

        getSupportActionBar().hide();
        nomEtabUpdateView = findViewById(R.id.nom_etab_update);
        descEtabUpdateView = findViewById(R.id.desc_etab_update);
        idResEtabUpdateView = findViewById(R.id.idResp_etab_update);
        btn_valider_update = findViewById(R.id.btn_valider_update_etab);

        Etablissement etablissement = new Etablissement();
        String nomEtab = getIntent().getStringExtra("nomE");
        String descEtab = getIntent().getStringExtra("descE");
        int idRe = getIntent().getIntExtra("idRe",etablissement.getIdResponsable());
        //int idRe = Integer.parseInt(String.valueOf(getIntent().getIntExtra("idRe",etablissement.getIdRespo())));

        nomEtabUpdateView.setText(nomEtab);
        descEtabUpdateView.setText(descEtab);
        idResEtabUpdateView.setText(Integer.toString(idRe));

        btn_valider_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = activity_get_etab.pos;
                int sID = activity_get_etab.selectId;
                System.out.println("sID =============== "+sID);
                String nomET = nomEtabUpdateView.getText().toString();
                String DescET = descEtabUpdateView.getText().toString();
                int IDRES_ET = Integer.parseInt(idResEtabUpdateView.getText().toString());

                etablissement.setNom(nomET);
                etablissement.setDescription(DescET);
                etablissement.setIdResponsable(IDRES_ET);

                Retrofit_Instance retrofitRespo = new Retrofit_Instance();
                Api_Calls_Interface apiCallsInterface =retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);
                apiCallsInterface.modifierEtablissment(etablissement,sID).enqueue(new Callback<Etablissement>() {
                    @Override
                    public void onResponse(Call<Etablissement> call, Response<Etablissement> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Etablisssement modifié avec succès", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code()==404){
                            System.out.println("****response is: "+response);
                            Toast.makeText(getApplicationContext(), "Etablissement "+ sID+" est introuvable", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Etablissement> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(activity_update_etab.class.getName())
                                .log(Level.SEVERE, " ERROR OCCURED",t);
                    }
                });



            }
        });

    }
}