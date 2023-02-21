package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Entities.Admin;
import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_ETD_Activity extends AppCompatActivity {

    public static int idE;
    private static EditText emailField;
    private static EditText passwordField;
    private static Button connexionBtn;
    public static String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_etd);
        getSupportActionBar().setTitle("Etudiant");
        emailField = (EditText) findViewById(R.id.loginEmail);
        passwordField = (EditText) findViewById(R.id.loginPassword);
        connexionBtn = (Button) findViewById(R.id.seConnecter);

        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();

                if (validateLogin(email, password)) {
                    doLogin(email, password);
                }
            }
        });
    }

    private ProgressDialog progressDialog;

    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }


    private boolean validateLogin(String email, String password){
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(String email,String password){
        showProgressDialog("login", "connexion en cours...");
        Retrofit_Instance retrofit_instance = new Retrofit_Instance();
        Api_Calls_Interface api_calls_interface =retrofit_instance.getRetrofit().create(Api_Calls_Interface.class);
        api_calls_interface.LoginEtd(email,password).enqueue(new Callback<Etudiant>() {

            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    idE = response.body().getId();
                    System.out.println(idE);
                    Toast.makeText(getApplicationContext(), "connecté", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_ETD_Activity.this, activity_etudiant.class));

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Login_ETD_Activity.this, "Erreur de connexion! réessayer de nouveau ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Toast.makeText(Login_ETD_Activity.this, "Impossible de se connecter", Toast.LENGTH_SHORT).show();
            }
        });

    }

}