package com.example.myapplication.activities.gestion_comptes.gestion_etd.ListViewHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_etd.activity_get_etd;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_get_respo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ETDAdapter extends RecyclerView.Adapter<ETDHolder> /*implements Filterable*/ {

    public static List<Etudiant> etudiantList;
    public static List<Etudiant> filteredList;
    public static int id;
    public static Integer niv;
    public static String nom, nomFiliere, nivFil;

    public ETDAdapter(List<Etudiant> etudiantList) {
        this.etudiantList = etudiantList;
        filteredList = new ArrayList<>();
        this.filteredList = etudiantList;
    }
    public ETDAdapter(){};

    @Override
    public ETDHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_etd_list, parent, false);
        return new ETDHolder(view);
    }


    @Override
    public void onBindViewHolder(ETDHolder holder, int position) {
        Etudiant etudiant = etudiantList.get(position);
        holder.ETDNom.setText(etudiant.getPrenom().toString()+" "+ etudiant.getNom().toString());
        holder.ETDid.setText(Integer.toString(etudiant.getId()));
        holder.ETD_fil.setText("Filiere: "+etudiant.getNomFiliere()+" - ");
        holder.ETD_niv.setText(Integer.toString(etudiant.getNbniveau()));
        Etudiant currentETD = etudiantList.get(position);
        holder.itemView.setTag(etudiant.getId());

        if (etudiantList.isEmpty()) {
           activity_get_etd.noDataVieww.setVisibility(View.VISIBLE);
        } else {
            activity_get_etd.noDataVieww.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = holder.getAdapterPosition();
               // notifyItemRemoved(selectedIndex);
                nom = etudiantList.get(selectedIndex).getNom();
                id = etudiantList.get(selectedIndex).getId();
                niv = etudiantList.get(selectedIndex).getNbniveau();
                nomFiliere = etudiantList.get(selectedIndex).getNomFiliere();
                //nivFil = etudiantList.get(selectedIndex).getNBniveau()+etudiantList.get(selectedIndex).getNomFiliere();
            }
        });
    }


    @Override
    public int getItemCount() {
        return etudiantList != null ? etudiantList.size() : 0;
    }


}