package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;
import com.example.myapplication.activities.activity_admin;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.ListViewHelper.RespoAdapter;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_respo extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private ImageView delRespo;
    public static int pos,selectId;
    public RespoAdapter respoAdapter;
    ItemTouchHelper touchHelper;
    public static View noDataVieww;
    public FloatingActionButton fab_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_respo);

        getSupportActionBar().setTitle("Liste des respo.stages");

        respoAdapter = new RespoAdapter();

        recyclerView = findViewById(R.id.RespoList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        delRespo = findViewById(R.id.delete_iv3);
        noDataVieww = findViewById(R.id.noDataViewRespoList);
        fab_update = findViewById(R.id.FAB_respo_update);

        touchHelper = new ItemTouchHelper(simpleCallbackDelete);
        touchHelper.attachToRecyclerView(recyclerView);
        loadRespo();

        fab_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRespo();
            }
        });
    }

    //DELETE SWIPE
    ItemTouchHelper.SimpleCallback simpleCallbackDelete = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // deleteNiv();
            int position = viewHolder.getAdapterPosition();
            int selectedId = RespoAdapter.responsableStageList.get(position).getIdResponsable();
            pos = position;
            selectId= selectedId;
            deleteRespo(selectedId, position);
            respoAdapter.notifyDataSetChanged();
            RespoAdapter.responsableStageList.remove(position);
           respoAdapter.notifyItemRemoved(position);

        }
    };
///////////////////// GET ALL RESPO API CALL /////////////////
    private void loadRespo() {
        Retrofit_Instance retrofitRespo = new Retrofit_Instance();

        Api_Calls_Interface apiRespo = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);

        apiRespo.FindAllRespo().enqueue(new Callback<List<ResponsableStage>>() {
            @Override
            public void onResponse(Call<List<ResponsableStage>> call, Response<List<ResponsableStage>> response) {

                populateListView(response.body());
                System.out.println(response.body());
                if(response.code() == 404){
                    Toast.makeText(activity_get_respo.this,"La liste est vide !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponsableStage>> call, Throwable t) {
                Toast.makeText(activity_get_respo.this,"erreur serveur: recupération", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void populateListView(List<ResponsableStage> responsableStageList) {

        RespoAdapter respoAdapter = new RespoAdapter(responsableStageList);
        recyclerView.setAdapter(respoAdapter);
    }

    /////////////////////DELETE ALL//////////////////

    private void deleteAllRespo(){
        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.supprimerAllResponsableStages().enqueue(new Callback<ResponsableStage>() {
            @Override
            public void onResponse(Call<ResponsableStage> call, Response<ResponsableStage> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Suppression avec succès", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "La liste des responsables est vide", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsableStage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur serveur sppression", Toast.LENGTH_SHORT).show();
                Logger.getLogger(activity_get_respo.class.getName()).log(Level.SEVERE, " ERROR OCCURED",t);
            }
        });
    }

    ////////////////////DELETE ONE //////////////

    public  void deleteRespo(int selectedId, final int selectedIndex){

        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface =retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);
        apiCallsInterface.supprimerResponsableStage(selectedId).enqueue(new Callback<ResponsableStage>() {
            @Override
            public void onResponse(Call<ResponsableStage> call, Response<ResponsableStage> response) {
                if(response.isSuccessful()){
                    System.out.println("****response is: "+response);
                    Toast.makeText(getApplicationContext(), "Niveau supprimé avec succès", Toast.LENGTH_SHORT).show();

                }
                else if(response.code()==404){
                    System.out.println("****response is: "+response);
                    Toast.makeText(getApplicationContext(), "ResponsableStage "+respoAdapter.id+" est introuvable", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ResponsableStage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur server lors de suppression", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_respo_menu_toolbar, menu);
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
                respoAdapter.getFilter().filter(s);
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
                deleteAllRespo();
                respoAdapter.notifyDataSetChanged();
                respoAdapter.notifyItemRemoved(pos);
                return true;
            case R.id.AjoutRespo:
                startActivity(new Intent(activity_get_respo.this, activity_creer_compte_respo.class));
                respoAdapter.notifyDataSetChanged();
                respoAdapter.notifyItemInserted(pos);
                loadRespo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* public void searchFilter(MenuItem item){
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Rechercher");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                    respoAdapter.getFilter().filter(s.toString());
                return true;
            }
        });
    }*/

}