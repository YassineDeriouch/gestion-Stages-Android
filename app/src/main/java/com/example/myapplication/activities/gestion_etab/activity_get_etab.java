package com.example.myapplication.activities.gestion_etab;

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
import android.widget.Toast;

import com.example.myapplication.Entities.Etablissement;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_etab.EtabListViewHelper.EtabAdapter;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_etab extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private ImageView editEtab,delEtab;
    private EtabAdapter etabAdapter;
    ItemTouchHelper touchHelper;
    public static int pos,selectId;
    public static View noDataVieww;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_etab);

        recyclerView = findViewById(R.id.EtabList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editEtab = findViewById(R.id.edit_iv);
        delEtab = findViewById(R.id.delete_iv);
        noDataVieww = findViewById(R.id.noDataViewEtabList);

        touchHelper = new ItemTouchHelper(simpleCallbackDeleteEtab);
        touchHelper.attachToRecyclerView(recyclerView);

        touchHelper = new ItemTouchHelper(simpleCallbackUpdateEtab);
        touchHelper.attachToRecyclerView(recyclerView);

        loadEtab();

    }
    //DELETE SWIPE
    ItemTouchHelper.SimpleCallback simpleCallbackDeleteEtab = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // deleteNiv();
            int position = viewHolder.getAbsoluteAdapterPosition();
            int selectedId = EtabAdapter.etablissementList.get(position).getIdEtablissement();
            pos = position;
            selectId= selectedId;

            deleteEtab(selectedId, position);

        }
    };

    // UPDATE SWIPE
    ItemTouchHelper.SimpleCallback simpleCallbackUpdateEtab = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAbsoluteAdapterPosition();
            int selectedId = EtabAdapter.etablissementList.get(position).getIdEtablissement();

            pos = position;
            selectId= selectedId;

            Etablissement etablissement = EtabAdapter.etablissementList.get(position);
            Intent intent = new Intent(activity_get_etab.this, activity_update_etab.class);
            intent.putExtra("nomE",etablissement.getNom());
            intent.putExtra("descE",etablissement.getDescription());
            intent.putExtra("idRe", etablissement.getIdResponsable());
            startActivity(intent);

        }
    };

/////////////API CALLS//////////////////////

    //**********GET ETAB***********//

    private void loadEtab() {

        Retrofit_Instance retrofitEtab = new Retrofit_Instance();
        Api_Calls_Interface apiNiv = retrofitEtab.getRetrofit().create(Api_Calls_Interface.class);

        apiNiv.recupererAllEtablissement().enqueue(new Callback<List<Etablissement>>() {
            @Override
            public void onResponse(Call<List<Etablissement>> call, Response<List<Etablissement>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Etablissement>> call, Throwable t) {
                Toast.makeText(activity_get_etab.this,"fail to load etab", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void populateListView(List<Etablissement> etabList) {

        EtabAdapter etabAdapter = new EtabAdapter(etabList);
        recyclerView.setAdapter(etabAdapter);
    }

    //***********DELETE ALL ETAB*************//

    private void deleteAllEtab(){
        Retrofit_Instance retrofitEtab = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitEtab.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.supprimerEtablissements().enqueue(new Callback<Etablissement>() {
            @Override
            public void onResponse(Call<Etablissement> call, Response<Etablissement> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Etablissement supprimé avec succès", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Aucun établissement trouvé!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Etablissement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur sppression Etablissement", Toast.LENGTH_SHORT).show();
                // Logger.getLogger(activity_creer_compte_respo.class.getName()).log(Level.SEVERE, " ERROR OCCURED",t);
            }
        });
    }

    //***********DELETE 1 ETAB*************//

    public  void deleteEtab(int selectedId, final int selectedIndex){

        Retrofit_Instance retrofitEtab = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface =retrofitEtab.getRetrofit().create(Api_Calls_Interface.class);
        apiCallsInterface.supprimerEtablissement(selectedId).enqueue(new Callback<Etablissement>() {
            @Override
            public void onResponse(Call<Etablissement> call, Response<Etablissement> response) {
                if(response.isSuccessful()){
                    System.out.println("****response is: "+response);
                    Toast.makeText(getApplicationContext(), "Etablissement supprimé avec succès", Toast.LENGTH_SHORT).show();

                }
                else if(response.code()==404){
                    System.out.println("****response is: "+response);
                    Toast.makeText(getApplicationContext(), "Etablissement "+EtabAdapter.idE+" est introuvable", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Etablissement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur lors de suppression de cet établissement", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.niveau_menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            case R.id.suppAllBtn:
                deleteAllEtab();
                return true;
            case R.id.AjoutNiv:
                startActivity(new Intent(activity_get_etab.this, activity_ajouter_etab.class));
                return true;
            case R.id.modifierNiv:
                //startActivity(new Intent(activity_get_niv.this, activity__niv.class));
            case R.id.refresh:
                loadEtab();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}