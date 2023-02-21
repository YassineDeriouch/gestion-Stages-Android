package com.example.myapplication.activities.gestion_comptes.gestion_etd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.ListViewHelper.ETDAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.ListViewHelper.ETDHolder;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.ListViewHelper.RespoAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_creer_compte_respo;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_get_respo;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_etd extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private ImageView delEtd;
    public static int pos,selectId,niv;
    public EditText searchNiv;
    public ImageView btn_searchNiv;
    public ETDAdapter etdAdapter;
    ItemTouchHelper touchHelper;
    public static View noDataVieww;
    public FloatingActionButton fab_update;
    public List<Etudiant> searchList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_etd);
        getSupportActionBar().setTitle("Liste des étudiants");

        etdAdapter = new ETDAdapter();

        recyclerView = findViewById(R.id.EtdList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        delEtd = findViewById(R.id.delete_iv4);
        noDataVieww = findViewById(R.id.noDataViewEtdList);
        fab_update = findViewById(R.id.FAB_etd_update);
        //searchNiv = findViewById(R.id.searchNivTV);
        //btn_searchNiv = findViewById(R.id.searchNiv_IV);

        touchHelper = new ItemTouchHelper(simpleCallbackDeleteETD);
        touchHelper.attachToRecyclerView(recyclerView);
        loadETD();
        fab_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadETD();
                Toast.makeText(getApplicationContext(), "Actualisation", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //DELETE SWIPE
    ItemTouchHelper.SimpleCallback simpleCallbackDeleteETD = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // deleteNiv();
            int position = viewHolder.getAdapterPosition();
            int selectedId = ETDAdapter.etudiantList.get(position).getId();
            pos = position;
            selectId= selectedId;
            deleteEtd(selectedId, position);
            etdAdapter.notifyDataSetChanged();
            ETDAdapter.etudiantList.remove(position);
            etdAdapter.notifyItemRemoved(position);

        }

    /*public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(activity_get_etd.this, R.color.red))
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightLabel("Supprimer")
                .setSwipeRightLabelColor(ContextCompat.getColor(activity_get_etd.this, R.color.white))
                .create()
                .decorate();
    }*/
};


    private ProgressDialog progressDialog;
    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    ///////////////////// GET ALL ETD API CALL /////////////////
    private void loadETD() {
        showProgressDialog("Récuperation", "récuperation des etudiants en cours...");

        Retrofit_Instance retrofitRespo = new Retrofit_Instance();

        Api_Calls_Interface apiETD = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);

        apiETD.recupererAllEntudiants().enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                progressDialog.dismiss();
                populateListView(response.body());
                System.out.println(response.body().spliterator());
                if(response.code() == 404){
                    progressDialog.dismiss();
                    Toast.makeText(activity_get_etd.this,"La liste est vide !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity_get_etd.this,"erreur serveur: recupération", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void populateListView(List<Etudiant> etudiantList) {

        ETDAdapter etdAdapter = new ETDAdapter(etudiantList);
        recyclerView.setAdapter(etdAdapter);
    }

    /////////////////////DELETE ALL//////////////////

    private void deleteAllEtd(){
        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.supprimerAllEtudiants().enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Suppression avec succès", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "La liste des étudiants est vide", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur serveur sppression", Toast.LENGTH_SHORT).show();
                Logger.getLogger(activity_get_etd.class.getName()).log(Level.SEVERE, " ERROR OCCURED",t);
            }
        });
    }

    ////////////////////DELETE ONE //////////////

    public void deleteEtd(int selectedId, final int selectedIndex) {

        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.supprimerETD(selectedId).enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Etudiant supprimé avec succès", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Etudiant" + etdAdapter.id + " est introuvable", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur server lors de suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void findByNiv(int nbNiveau){
        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);
        //nbNiveau = ETDAdapter.niv.intValue();
//        System.out.println("nbNiveau===="+nbNiveau);
        int finalNbNiveau = nbNiveau;
        apiCallsInterface.recupererEtudiantByNiveau(nbNiveau).enqueue(new Callback<List<Etudiant>>(){
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                if(response.isSuccessful()) {

                    List<Etudiant> item = response.body();
                    // Find the index of the item in the data list.
                    int index = -1;
                    for (int i = 0; i < ETDAdapter.etudiantList.size(); i++) {
                        if (ETDAdapter.etudiantList.get(i).equals(item.get(i).getNbniveau())) {
                            index = i;
                            if ((item.get(i).getNbniveau()) == 1) {
                                Toast.makeText(getApplicationContext(), "la liste des etudiants en " + (item.get(i).getNbniveau()) + "ère année", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "la liste des etudiants en " + (item.get(i).getNbniveau()) + "ème année", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                    if (index != -1) {
                        recyclerView.scrollToPosition(index);
                    } else {
                        noDataVieww.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "la liste des étudiants de ce niveau est vide", Toast.LENGTH_SHORT).show();
                    }

                }else if (response.code() == 404){
                    Toast.makeText(getApplicationContext(), "la liste des étudiants de ce niveau est vide", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur serveur lors de la recuperation\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
        
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.list_etd_menu_toolbar, menu);
            MenuItem item = menu.findItem(R.id.search);
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setQueryHint("Rechercher par niveau");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    searchList = new ArrayList<>();
                    if(s.length()>0 && s.matches("[1-5]")){
                        for (int i = 0; i < etdAdapter.etudiantList.size(); i++) {
                            if((etdAdapter.etudiantList.get(i).getNomFiliere().contains(s.toLowerCase()))
                                    ||etdAdapter.etudiantList.get(i).getNbniveau()==(Integer.parseInt(s.toLowerCase()))){
                                Etudiant etudiant = new Etudiant();
                                etudiant.setId(etdAdapter.etudiantList.get(i).getId());
                                etudiant.setNom(etdAdapter.etudiantList.get(i).getNom());
                                etudiant.setPrenom(etdAdapter.etudiantList.get(i).getPrenom());
                                etudiant.setNomFiliere(etdAdapter.etudiantList.get(i).getNomFiliere());
                                etudiant.setNbniveau(etdAdapter.etudiantList.get(i).getNbniveau());
                                searchList.add(etudiant);
                            }
                        }
                        ETDAdapter searchEtdAdapter = new ETDAdapter(searchList);
                        recyclerView.setAdapter(searchEtdAdapter);
                    }
                    else if (s.length()==0){
                        ETDAdapter etdAdapter = new ETDAdapter(ETDAdapter.etudiantList);
                        recyclerView.setAdapter(etdAdapter);
                        loadETD();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                   // etdAdapter.getFilter().filter(s);
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);
        }
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.search:
                    return true;
                case R.id.suppAllBtn:
                    deleteAllEtd();
                    etdAdapter.notifyDataSetChanged();
                    etdAdapter.notifyItemRemoved(pos);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

    }

}