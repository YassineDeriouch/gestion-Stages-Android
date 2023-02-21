package com.example.myapplication.activities.gestion_niv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_niv.ListViewHelper.NiveauAdapter;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_niv extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private ImageView editNiv,delNiv;
    public static int pos,selectId;
    private NiveauAdapter niveauAdapter;
    ItemTouchHelper touchHelper;
    public static View noDataVieww;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_niveaux);

        recyclerView = findViewById(R.id.Nivlist_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editNiv = findViewById(R.id.edit_iv);
        delNiv = findViewById(R.id.delete_iv);
        noDataVieww = findViewById(R.id.noDataViewNivList);

        touchHelper = new ItemTouchHelper(simpleCallbackDelete);
        touchHelper.attachToRecyclerView(recyclerView);

        touchHelper = new ItemTouchHelper(simpleCallbackUpdate);
        touchHelper.attachToRecyclerView(recyclerView);

        loadNiveaux();

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
            int selectedId = NiveauAdapter.niveauList.get(position).getIdNiveau();
pos = position;
selectId= selectedId;
            deleteNiv(selectedId, position);
           // niveauAdapter.niveauList.remove(position);
           // niveauAdapter.notifyItemRemoved(position);

        }
    };
    // UPDATE SWIPE
    ItemTouchHelper.SimpleCallback simpleCallbackUpdate = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            int selectedId = NiveauAdapter.niveauList.get(position).getIdNiveau();

            pos = position;
            selectId= selectedId;

                Niveau niveau = NiveauAdapter.niveauList.get(position);
                Intent intent = new Intent(activity_get_niv.this, activity_update_niv.class);
                intent.putExtra("nomF",niveau.getNomFiliere());
                intent.putExtra("niv",niveau.getNiveau());
                startActivity(intent);

        }
    };



    private void loadNiveaux() {
        Retrofit_Instance retrofitNiv = new Retrofit_Instance();

        Api_Calls_Interface apiNiv = retrofitNiv.getRetrofit().create(Api_Calls_Interface.class);

        apiNiv.recupererAllNiveaux().enqueue(new Callback<List<Niveau>>() {
            @Override
            public void onResponse(Call<List<Niveau>> call, Response<List<Niveau>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Niveau>> call, Throwable t) {
                Toast.makeText(activity_get_niv.this,"fail to load employees", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void populateListView(List<Niveau> niveauList) {

        NiveauAdapter niveauAdapter = new NiveauAdapter(niveauList);
        recyclerView.setAdapter(niveauAdapter);
    }

/////////////////////DELETE ALL//////////////////

    private void deleteAllNiveaux(){
        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

        apiCallsInterface.deleteAll().enqueue(new Callback<Niveau>() {
            @Override
            public void onResponse(Call<Niveau> call, Response<Niveau> response) {
                if (response.isSuccessful()) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "Niveau supprimé avec succès", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    System.out.println("****response is: " + response);
                    Toast.makeText(getApplicationContext(), "La liste des niveaux est vide", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Niveau> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "erreur sppression Niveaux", Toast.LENGTH_SHORT).show();
                // Logger.getLogger(activity_creer_compte_respo.class.getName()).log(Level.SEVERE, " ERROR OCCURED",t);
            }
        });
    }

    ////////////////////DELETE ONE //////////////

    public  void deleteNiv(int selectedId, final int selectedIndex){

           Retrofit_Instance retrofitCompte = new Retrofit_Instance();
           Api_Calls_Interface apiCallsInterface =retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);
           activity_get_niv activity_get_niv = new activity_get_niv();
           apiCallsInterface.delete(selectedId).enqueue(new Callback<Niveau>() {
            @Override
                public void onResponse(Call<Niveau> call, Response<Niveau> response) {
                    if(response.isSuccessful()){
                        System.out.println("****response is: "+response);
                            Toast.makeText(getApplicationContext(), "Niveau supprimé avec succès", Toast.LENGTH_SHORT).show();

                    }
                    else if(response.code()==404){
                        System.out.println("****response is: "+response);
                            Toast.makeText(getApplicationContext(), "Niveau "+NiveauAdapter.id+" est introuvable", Toast.LENGTH_SHORT).show();
                    }

            }
            @Override
                public void onFailure(Call<Niveau> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "erreur spprimer Niveau", Toast.LENGTH_SHORT).show();
             }
           });

}

    /////////////////////////UPDATE //////////////

    /*public void updateNiv(int selectedId, final int selectedIndex){
        Retrofit_Instance retrofitCompte = new Retrofit_Instance();
        Api_Calls_Interface apiCallsInterface =retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);
        Niveau niveau =new Niveau();
        apiCallsInterface.modifierNiveau(niveau,selectedId).enqueue(new Callback<Niveau>() {
            @Override
            public void onResponse(Call<Niveau> call, Response<Niveau> response) {

            }

            @Override
            public void onFailure(Call<Niveau> call, Throwable t) {

            }
        });

    }
*/
    @Override
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
                deleteAllNiveaux();
                return true;
            case R.id.AjoutNiv:
                startActivity(new Intent(activity_get_niv.this, activity_ajouter_niv.class));
                return true;
            case R.id.modifierNiv:
                //startActivity(new Intent(activity_get_niv.this, activity__niv.class));
            case R.id.refresh:
              loadNiveaux();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /////////////////

}