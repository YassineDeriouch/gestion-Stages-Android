package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_RESPO_Activity;
import com.example.myapplication.activities.gestion_etab.activity_ajouter_etab;
import com.example.myapplication.activities.gestion_etab.activity_get_etab;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_profile_Respo extends AppCompatActivity {

    private View edit;
    private EditText nom,prenom,email,password;
    private TextView id;
    private Button updateBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_respo);

        getSupportActionBar().hide();

        id = findViewById(R.id.ID_RESPO);
        edit = findViewById(R.id.editer);
        nom = findViewById(R.id.nomRes);
        prenom = findViewById(R.id.prenomRes);
        email = findViewById(R.id.emailRes);
        password = findViewById(R.id.passwordRes);
        updateBtn = findViewById(R.id.editRespoSave);

        ResponsableStage responsableStage = new ResponsableStage();
        getCurrentRespoCall();

/////////////////UPDATE COMPTE RESPO ///////////////////

        updateBtn.setVisibility(View.GONE);
        //////// update
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateBtn.setVisibility(View.VISIBLE);

            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom1 = nom.getText().toString();
                String prenom1 = prenom.getText().toString();
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                responsableStage.setNom(nom1);
                responsableStage.setPrenom(prenom1);
                responsableStage.setEmail(email1);
                responsableStage.setPassword(password1);

                Retrofit_Instance retrofit_instance = new Retrofit_Instance();
                Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

                int idRespo = Login_RESPO_Activity.idR;
                System.out.println("update idR is =====> " + idRespo);
                apiCallsInterface.modifierRespo(responsableStage, idRespo).enqueue(new Callback<ResponsableStage>() {
                    @Override
                    public void onResponse(Call<ResponsableStage> call, Response<ResponsableStage> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Modifications effectuées !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsableStage> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erreur d'affichage !", Toast.LENGTH_LONG).show();
                        Logger.getLogger(activity_creer_compte_respo.class.getName())
                                .log(Level.SEVERE, " ERROR OCCURED", t);
                    }
                });

            }
        });

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


    public void getCurrentRespoCall(){
        showProgressDialog("récuperation de vos informations", "Veuillez patientez ...");

        Retrofit_Instance retrofit_instance = new Retrofit_Instance();
            Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

            int idRespo = Login_RESPO_Activity.idR;

            System.out.println("ID ============>>>>> " + idRespo);
/////////////////////////API CALL OF FIND CURRENT RESPO USING
            apiCallsInterface.FindRespoById(idRespo).enqueue(new Callback<ResponsableStage>() {
                // apiCallsInterface.LoginRespo(em,pass).enqueue(new Callback<ResponsableStage>(){
                @Override
                public void onResponse(Call<ResponsableStage> call, Response<ResponsableStage> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        System.out.println("****response is: " + response);
                        id.setText(Integer.toString(response.body().getIdResponsable()));
                        nom.setText(response.body().getNom());
                        prenom.setText(response.body().getPrenom());
                        email.setText(response.body().getEmail());
                        password.setText(response.body().getPassword());
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "erreur d'affichage !", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponsableStage> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "erreur d'affichage !", Toast.LENGTH_LONG).show();
                    Logger.getLogger(activity_creer_compte_respo.class.getName())
                            .log(Level.SEVERE, " ERROR OCCURED", t);
                }
            });
        }

}


