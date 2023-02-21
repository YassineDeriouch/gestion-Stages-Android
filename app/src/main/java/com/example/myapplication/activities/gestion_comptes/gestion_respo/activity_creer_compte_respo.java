package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_creer_compte_respo extends AppCompatActivity {

    public static EditText nom,prenom,email,password;
    public static Button btnAddRespo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte_respo);

        getSupportActionBar().hide();

        nom = (EditText) findViewById(R.id.idResp_etab_update);
        prenom = (EditText) findViewById(R.id.prenomRespoET);
        email = (EditText) findViewById(R.id.emailRespoET);
        password= (EditText) findViewById(R.id.passwordRespoET);
        btnAddRespo = (Button) findViewById(R.id.btn_save_respo);

        btnAddRespo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResponsableStage responsableStage = new ResponsableStage();
                String nom1 = nom.getText().toString();
                String prenom1 = prenom.getText().toString();
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                Retrofit_Instance  retrofit_instance = new Retrofit_Instance();
                Api_Calls_Interface apiCallsInterface =retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);

                responsableStage.setNom(nom1);
                responsableStage.setPrenom(prenom1);
                responsableStage.setEmail(email1);
                responsableStage.setPassword(password1);

                apiCallsInterface.saveRespo(responsableStage).enqueue(new Callback<ResponsableStage>() {
                    @Override
                    public void onResponse(Call<ResponsableStage> call, Response<ResponsableStage> response) {
                        if(response.isSuccessful()){
                            System.out.println("****response is: "+response);
                            Toast.makeText(getApplicationContext(), "responsable ajouté avec succès", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code()!= 200 ||response.code()!=201||response.code()!= 202){
                            System.out.println("****response is: "+response);
                            Toast.makeText(getApplicationContext(), "Erreur d'ajout !!", Toast.LENGTH_LONG).show();
                        }                     }
                    @Override
                    public void onFailure(Call<ResponsableStage> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erreur d'enregistrement !", Toast.LENGTH_LONG).show();
                        Logger.getLogger(activity_creer_compte_respo.class.getName())
                                .log(Level.SEVERE, " ERROR OCCURED",t);
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.respo_menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.clear:
                nom.setText("");
                prenom.setText("");
                email.setText("");
                password.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

 