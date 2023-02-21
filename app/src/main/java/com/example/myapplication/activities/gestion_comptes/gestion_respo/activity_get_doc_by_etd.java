package com.example.myapplication.activities.gestion_comptes.gestion_respo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Entities.Document;
import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.ListViewHelper.ETDAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.GrivView_Helper.ETDdocAdapter;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.GrivView_Helper.ETDdocHolder;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_get_doc_by_etd extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private ImageView fileEtd;
    public static int pos,selectId,niv;
    public ImageView btn_searchNiv;
    public ETDdocAdapter etdDocAdapter;
    public static View noDataVieww;
    public FloatingActionButton fab_update;
    public List<Etudiant> searchList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_doc_by_etd);
        getSupportActionBar().setTitle("Liste des étudiants");

        etdDocAdapter = new ETDdocAdapter();

        recyclerView = findViewById(R.id.DocETDList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileEtd = findViewById(R.id.iv_etd_file);
        noDataVieww = findViewById(R.id.noDataViewDocEtd_list);
        //searchNiv = findViewById(R.id.searchNivTV);
        //btn_searchNiv = findViewById(R.id.searchNiv_IV);

        loadDocEtd();

        ETDdocAdapter.RecyclerViewClickListener listener = (view, position) -> {

            fileEtd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedId = ETDdocAdapter.documentList.get(position).getIdDocument();
                    System.out.println("selectedId"+ selectedId);
                    downloadFileETD(selectedId);
                }
            });

        };

       /* recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
           @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

               View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
                    int position = recyclerView.getChildAdapterPosition(child);
                    downloadFileETD(ETDdocAdapter.documentList.get(position).getIdDocument());
                    System.out.println(selectedId);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }
        });*/



        /*etdDocAdapter = new ETDdocAdapter(ETDdocAdapter.documentList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Document document) {
                int position = viewHolder.getAdapterPosition();
                int selectedId = ETDdocAdapter.documentList.get(position).getIdDocument();
                downloadFileETD(selectedId);
            }
        });*/

    }

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

    private void loadDocEtd() {
        showProgressDialog("Récuperation", "récuperation des documents des étudiants en cours...");

        Retrofit_Instance retrofitRespo = new Retrofit_Instance();

        Api_Calls_Interface apiETD = retrofitRespo.getRetrofit().create(Api_Calls_Interface.class);

        apiETD.findAllDocumentByETD().enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                progressDialog.dismiss();
                populateListView(response.body());
//                System.out.println(response.body().spliterator());
                if(response.code() == 404){
                    progressDialog.dismiss();
                    Toast.makeText(activity_get_doc_by_etd.this,"La liste est vide !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity_get_doc_by_etd.this,"erreur serveur: recupération"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void populateListView(List<Document> documentList) {

        ETDdocAdapter etDdocAdapter = new ETDdocAdapter(documentList);
        recyclerView.setAdapter(etDdocAdapter);
    }

public void downloadFileETD(int selectedId){
        showProgressDialog("Téléchargement", "Téléchargement du document en cours...");
    Retrofit_Instance retrofitCompte = new Retrofit_Instance();
    Api_Calls_Interface apiCallsInterface = retrofitCompte.getRetrofit().create(Api_Calls_Interface.class);

    apiCallsInterface.downloadFile(selectedId).enqueue(new Callback<Document>() {
        @Override
        public void onResponse(Call<Document> call, Response<Document> response) {
            if (response.isSuccessful()) {
                progressDialog.dismiss();
                System.out.println("****response is: " + response);
                Toast.makeText(getApplicationContext(), "document téléchargé avec succès", Toast.LENGTH_SHORT).show();

            } else if (response.code() == 404) {
                progressDialog.dismiss();

                System.out.println("****response is: " + response);
                Toast.makeText(getApplicationContext(), "document" + etdDocAdapter.id + " est introuvable", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<Document> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "erreur server lors de suppression de téléchargement", Toast.LENGTH_SHORT).show();
        }
    });

}


}