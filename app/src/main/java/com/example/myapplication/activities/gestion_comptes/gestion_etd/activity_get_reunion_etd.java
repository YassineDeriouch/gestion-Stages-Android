package com.example.myapplication.activities.gestion_comptes.gestion_etd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.Entities.Reunion;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_ETD_Activity;
import com.example.myapplication.activities.Login_RESPO_Activity;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_compte_respo;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_reunion_etd extends AppCompatActivity {

    private EditText libelleReunion,dateReunion,salleReunion;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reunion_etd);
        getSupportActionBar().hide();

        int idEtd = Login_ETD_Activity.idE;

        libelleReunion = findViewById(R.id.get_etd_libelle_reunion);
        dateReunion = findViewById(R.id.get_etd_date_reunion);
        salleReunion = findViewById(R.id.get_etd_salle_reunion);

        String lib = libelleReunion.getText().toString();
        String date = dateReunion.getText().toString();
        String salle = salleReunion.getText().toString();

        Reunion reunion = new Reunion();
        getCurrentRespoCall(idEtd);

    }

    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }



    private void getCurrentRespoCall(int id) {
        showProgressDialog("récuperation des informations", "Veuillez patientez ...");
        Retrofit_Instance retrofit_instance = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);
        id = Login_ETD_Activity.idE;
        apiCallsInterface.recupererReunionByETD(id).enqueue(new Callback<Reunion>() {
            @Override
            public void onResponse(Call<Reunion> call, Response<Reunion> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    progressDialog.dismiss();
                    System.out.println("****response is: " + response);
                    libelleReunion.setText(response.body().getLibelle());
                    dateReunion.setText(response.body().getDate());
                    salleReunion.setText(response.body().getSalle());
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "erreur d'affichage !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reunion> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "erreur d'affichage !", Toast.LENGTH_LONG).show();
                Logger.getLogger(activity_get_reunion_etd.class.getName())
                        .log(Level.SEVERE, " ERROR OCCURED", t);
            }
        });


    }


}