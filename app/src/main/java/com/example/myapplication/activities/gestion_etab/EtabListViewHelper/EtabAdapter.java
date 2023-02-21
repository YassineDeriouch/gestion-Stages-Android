package com.example.myapplication.activities.gestion_etab.EtabListViewHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Etablissement;
import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_etab.activity_get_etab;
import com.example.myapplication.activities.gestion_niv.ListViewHelper.NiveauHolder;
import com.example.myapplication.activities.gestion_niv.activity_get_niv;

import java.util.List;

public class EtabAdapter extends RecyclerView.Adapter<EtabHolder> {

    public static List<Etablissement> etablissementList;
    public static int idE,idR;
    public static String nom,des;
    public EtabAdapter(List<Etablissement> etablissementList) {
        this.etablissementList = etablissementList;
    }

    @Override
    public EtabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_etab_list, parent, false);
        return new EtabHolder(view);
    }


    @Override
    public void onBindViewHolder(EtabHolder holder, int position) {
        Etablissement etablissement = etablissementList.get(position);
        holder.nomEtab.setText(etablissement.getNom().toString());
        holder.idResp.setText(Integer.toString(etablissement.getIdResponsable()));
        holder.desc.setText(etablissement.getDescription().toString());
        Etablissement currentEtab = etablissementList.get(position);
        holder.itemView.setTag(etablissement.getIdEtablissement());

        if (etablissementList.isEmpty()) {
            activity_get_etab.noDataVieww.setVisibility(View.VISIBLE);
        } else {
            activity_get_etab.noDataVieww.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = holder.getAbsoluteAdapterPosition();
                idE = etablissementList.get(selectedIndex).getIdEtablissement();
                nom = etablissementList.get(selectedIndex).getNom();
                idR = etablissementList.get(selectedIndex).getIdResponsable();
                des = etablissementList.get(selectedIndex).getDescription();
            }
        });


    }

    @Override
    public int getItemCount() {
        /*if (etablissementList.isEmpty()) {
            return 0;
        } else {
            return etablissementList.size();
        }*/
        return etablissementList != null ? etablissementList.size() : 0;
    }

}