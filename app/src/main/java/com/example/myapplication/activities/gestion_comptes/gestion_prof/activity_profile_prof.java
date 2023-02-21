package com.example.myapplication.activities.gestion_comptes.gestion_prof;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_ETD_Activity;
import com.example.myapplication.activities.Login_PROF_Activity;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_compte_respo;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_profile_prof extends AppCompatActivity {

    private View edit;
    private EditText nom, prenom, email, password;
    private Button updateBtn;
    private TextView id;
    public int idPr = Login_PROF_Activity.idP;
    Professeur professeur = new Professeur();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_prof);
        getSupportActionBar().hide();

        id = findViewById(R.id.ID_PROF);
        nom = findViewById(R.id.nomProf);
        prenom = findViewById(R.id.prenomProf);
        email = findViewById(R.id.emailProf);
        password = findViewById(R.id.passwordProf);
        updateBtn = findViewById(R.id.editProfSave);
        edit = findViewById(R.id.editer_info_prof);

        getCurrentProfCall();

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
                updateCurrentProf();
            }
        });
    }
    public ProgressDialog progressDialog;
    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    private void getCurrentProfCall() {
        showProgressDialog("Récuperation", "récuperation de vos données en cours ...");
        Retrofit_Instance retrofit_instance = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

        System.out.println("ID ============>>>>> " + idPr);
        apiCallsInterface.FindProfById(idPr).enqueue(new Callback<Professeur>() {
            @Override
            public void onResponse(Call<Professeur> call, Response<Professeur> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    System.out.println("****response is: " + response);
                    id.setText(Integer.toString(idPr));
                    nom.setText(response.body().getNom());
                    prenom.setText(response.body().getPrenom());
                    email.setText(response.body().getEmail());
                    password.setText(response.body().getPassword());
                } switch (response.code()){
                    case 404:
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Uitlisateur introuvable !", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Erreur lors de la recuperation de vos données ! réssayez", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Professeur> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erreur serveur !", Toast.LENGTH_LONG).show();
                Logger.getLogger(activity_creer_compte_respo.class.getName())
                        .log(Level.SEVERE, " ERROR OCCURED", t);
            }
        });
    }


    /////////////////////////////// UPDATE CURRENT ETD INFOS//////////////////////

    public void updateCurrentProf(){

        String nom1 = nom.getText().toString();
        String prenom1 = prenom.getText().toString();
        String email1 = email.getText().toString();
        String password1 = password.getText().toString();

        professeur.setNom(nom1);
        professeur.setPrenom(prenom1);
        professeur.setEmail(email1);
        professeur.setPassword(password1);

        Retrofit_Instance retrofit_instance = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

        System.out.println("update idR is =====> " + idPr);
        apiCallsInterface.modifierProf(professeur, idPr).enqueue(new Callback<Professeur>() {
            @Override
            public void onResponse(Call<Professeur> call, Response<Professeur> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Modifications effectuées !", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Professeur> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur serveur !", Toast.LENGTH_LONG).show();
                Logger.getLogger(activity_creer_compte_prof.class.getName())
                        .log(Level.SEVERE, " ERROR OCCURED", t);
            }
        });

    }
}