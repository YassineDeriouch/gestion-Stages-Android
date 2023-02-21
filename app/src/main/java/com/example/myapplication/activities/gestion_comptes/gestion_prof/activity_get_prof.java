package com.example.myapplication.activities.gestion_comptes.gestion_prof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelper.ProfAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.ListViewHelper.RespoAdapter;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_prof extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private ImageView delProf;
    public static int pos,selectId;
    public ProfAdapter profAdapter;
    ItemTouchHelper touchHelper;
    public static View noDataVieww;
    public FloatingActionButton fab_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_prof);
        getSupportActionBar().setTitle("Liste des professeurs");

        profAdapter = new ProfAdapter();

        recyclerView = findViewById(R.id.ProfList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        delProf = findViewById(R.id.delete_iv5);
        noDataVieww = findViewById(R.id.noDataViewProfList);
        fab_update = findViewById(R.id.FAB_prof_update);

        touchHelper = new ItemTouchHelper(simpleCallbackDeleteProf);
        touchHelper.attachToRecyclerView(recyclerView);
        loadProf();
        fab_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProf();
            }
        });
    }

    //DELETE SWIPE
    ItemTouchHelper.SimpleCallback simpleCallbackDeleteProf = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // deleteNiv();
            int position = viewHolder.getAdapterPosition();
            int selectedId = ProfAdapter.professeurList.get(position).getId();
            pos = position;
            selectId= selectedId;
            deleteProf(selectedId, position);
            profAdapter.notifyDataSetChanged();
            ProfAdapter.professeurList.remove(position);
            profAdapter.notifyItemRemoved(position);
        }
    };

    ///////////////////// GET ALL Prof API CALL /////////////////
    private void loadProf() {
        Retrofit_Instance retrofitRespo = new Retrofit_Instance();

        Api_Calls_Interface apiProf = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);

        apiProf.recupererAllProfs().enqueue(new Callback<List<Professeur>>() {
            @Override
            public void onResponse(Call<List<Professeur>> call, Response<List<Professeur>> response) {
                populateListView(response.body());
                System.out.println(response.body());
                if(response.code() == 404){
                    Toast.makeText(activity_get_prof.this,"La liste est vide !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Professeur>> call, Throwable t) {
                Toast.makeText(activity_get_prof.this,"erreur serveur: recupération", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void populateListView(List<Professeur> professeurList) {

        ProfAdapter profAdapter = new ProfAdapter(professeurList);
        recyclerView.setAdapter(profAdapter);
    }

    /////////////////////DELETE ALL//////////////////

    private void deleteAllProf(){
        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.supprimerAllProfesseurs().enqueue(new Callback<Professeur>() {
            @Override
            public void onResponse(Call<Professeur> call, Response<Professeur> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Suppression avec succès", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "La liste des professeurs est vide", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Professeur> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur serveur sppression", Toast.LENGTH_SHORT).show();
                Logger.getLogger(activity_get_prof.class.getName()).log(Level.SEVERE, " ERROR OCCURED",t);
            }
        });
    }

    ////////////////////DELETE ONE //////////////

    public  void deleteProf(int selectedId, final int selectedIndex) {

        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.supprimerProfesseur(selectedId).enqueue(new Callback<Professeur>() {
            @Override
            public void onResponse(Call<Professeur> call, Response<Professeur> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Professeur supprimé avec succès", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Professeur" + profAdapter.id + " est introuvable", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Professeur> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur server lors de suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_prof_menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Rechercher");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                profAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                //searchFilter(item);
                return true;
            case R.id.suppAllBtn:
                deleteAllProf();
                profAdapter.notifyDataSetChanged();
                profAdapter.notifyItemRemoved(pos);
                return true;
            case R.id.ajoutProf:
                startActivity(new Intent(activity_get_prof.this, activity_creer_compte_prof.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}