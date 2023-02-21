package com.example.myapplication.activities.gestion_comptes.gestion_prof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.Entities.Reunion;
import com.example.myapplication.R;
import com.example.myapplication.activities.Login_ETD_Activity;
import com.example.myapplication.activities.Login_PROF_Activity;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelper.ProfAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelperReunion.ReunionProfAdapter;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_reunion_prof extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private ImageView delProf;
    public static int pos,selectId;
    public ReunionProfAdapter reunionProfAdapter;
    public static View noDataVieww;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reunion_prof);
        getSupportActionBar().setTitle("Liste des reunions");
        reunionProfAdapter = new ReunionProfAdapter();

        recyclerView = findViewById(R.id.reunionProfList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noDataVieww = findViewById(R.id.noDataViewReunionProfList);
        int idPROF = Login_PROF_Activity.idP;

        loadReunionProf(idPROF);

    }

    private void loadReunionProf(int idP) {
        Retrofit_Instance retrofitRespo = new Retrofit_Instance();

        Api_Calls_Interface apiProf = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);
        idP= Login_PROF_Activity.idP;
        apiProf.recupererReunionByPROF(idP).enqueue(new Callback<List<Reunion>>() {
            @Override
            public void onResponse(Call<List<Reunion>> call, Response<List<Reunion>> response) {
                populateListView(response.body());
                System.out.println(response.body());
                if(response.code() == 404){
                    Toast.makeText(activity_get_reunion_prof.this,"La liste est vide !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Reunion>> call, Throwable t) {
                Toast.makeText(activity_get_reunion_prof.this,"erreur serveur: recupération", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void populateListView(List<Reunion> reunionList) {

        ReunionProfAdapter reunionProfAdapter = new ReunionProfAdapter(reunionList);
        recyclerView.setAdapter(reunionProfAdapter);
    }
}