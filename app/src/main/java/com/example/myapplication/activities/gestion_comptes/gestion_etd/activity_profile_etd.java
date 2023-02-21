package com.example.myapplication.activities.gestion_comptes.gestion_etd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_ETD_Activity;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_compte_respo;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_profile_etd extends AppCompatActivity {

    private View edit;
    private EditText nom, prenom, email, password;
    private Button updateBtn;
    private TextView id;
    public int idEt = Login_ETD_Activity.idE;
    Etudiant etudiant = new Etudiant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_etd);

        getSupportActionBar().hide();

        id = findViewById(R.id.ID_ETD);
        nom = findViewById(R.id.nomEtd);
        prenom = findViewById(R.id.prenomEtd);
        email = findViewById(R.id.emailEtd);
        password = findViewById(R.id.passwordEtd);
        updateBtn = findViewById(R.id.edittEtdSave);
        edit = findViewById(R.id.editer_info_etd);

        getCurrentETDCall();

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
                updateCurrentETD();
            }
        });
    }
    //////////////////////// GET CURRENT ETD API CALL ///////////////////

    private void getCurrentETDCall() {
        Retrofit_Instance retrofit_instance = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);


        System.out.println("ID ============>>>>> " + idEt);
        apiCallsInterface.FindEtudiantbyId(idEt).enqueue(new Callback<Etudiant>() {
            // apiCallsInterface.LoginRespo(em,pass).enqueue(new Callback<ResponsableStage>(){
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    id.setText(Integer.toString(idEt));
                    nom.setText(response.body().getNom());
                    prenom.setText(response.body().getPrenom());
                    email.setText(response.body().getEmail());
                    password.setText(response.body().getPassword());
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

    /////////////////////////////// UPDATE CURRENT ETD INFOS//////////////////////

    public void updateCurrentETD(){

            String nom1 = nom.getText().toString();
            String prenom1 = prenom.getText().toString();
            String email1 = email.getText().toString();
            String password1 = password.getText().toString();

            etudiant.setNom(nom1);
            etudiant.setPrenom(prenom1);
            etudiant.setEmail(email1);
            etudiant.setPassword(password1);

            Retrofit_Instance retrofit_instance = new Retrofit_Instance();
            Api_Calls_Interface apiCallsInterface = retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

            System.out.println("update idR is =====> " + idEt);
            apiCallsInterface.modifierEtudiant(etudiant, idEt).enqueue(new Callback<Etudiant>() {
                @Override
                public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Modifications effectuées !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Etudiant> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "erreur serveur !", Toast.LENGTH_LONG).show();
                    Logger.getLogger(activity_profile_etd.class.getName())
                            .log(Level.SEVERE, " ERROR OCCURED", t);
                }
            });

        }

    }