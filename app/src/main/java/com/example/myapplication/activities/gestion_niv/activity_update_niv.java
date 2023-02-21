package com.example.myapplication.activities.gestion_niv;

import static com.example.myapplication.activities.gestion_niv.activity_get_niv.selectId;

import androidx.appcompat.app.AppCompatActivity;

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

public class activity_update_niv extends AppCompatActivity {

    public static Button btn_valider_update_niv;
    public static EditText filiereView;
    public static Spinner spinnerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_niv);
        Niveau niveau = new Niveau();
        filiereView = findViewById(R.id.nom_filiere_update);
        spinnerView = findViewById(R.id.spinner_niveau_update);
        btn_valider_update_niv = findViewById(R.id.btn_update_niv);

        String niv = niveau.getNiveau();

        String nomFiliere = getIntent().getStringExtra("nomF");
        String valNiv = getIntent().getStringExtra("niv");

        filiereView.setText(nomFiliere);

        String[] arraySpinner = new String[] { "1", "2", "3", "4", "5" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerView.setAdapter(adapter);
        spinnerView.setPrompt("Niveau");

        btn_valider_update_niv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Niveau niveau = new Niveau();
                int p = activity_get_niv.pos;
                int sID = selectId;

                String nomFiliere1 = filiereView.getText().toString();
                String niv = spinnerView.getSelectedItem().toString();

                niveau.setNomFiliere(nomFiliere1);
                niveau.setNiveau(niv);

                System.out.println("id is ==========>>>>> "+selectId);
                Retrofit_Instance retrofitCompte = new Retrofit_Instance();
                Api_Calls_Interface apiCallsInterface =retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);
                apiCallsInterface.modifierNiveau(niveau,selectId).enqueue(new Callback<Niveau>() {
                    @Override
                    public void onResponse(Call<Niveau> call, Response<Niveau> response) {
                        System.out.println("response: "+ response.code());
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "niveau modifié avec succès", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Erreur de modification", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Niveau> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erreur serveur ", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(activity_ajouter_niv.class.getName())
                                .log(Level.SEVERE, " ERROR OCCURED",t);
                    }
                });

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> parent) {
    }
}