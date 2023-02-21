package com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelperReunion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Reunion;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelper.ProfHolder;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_get_prof;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_get_reunion_prof;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ReunionProfAdapter extends RecyclerView.Adapter<ReunionProfHolder>{

    public static List<Reunion> reunionList;
    public static int id;
    public static String nom;
    public ReunionProfAdapter(List<Reunion> reunionList) {
        this.reunionList = reunionList;
    }
    public ReunionProfAdapter(){};

    @Override
    public ReunionProfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_reunion_prof_list, parent, false);
        return new ReunionProfHolder(view);
    }


    @Override
    public void onBindViewHolder(ReunionProfHolder holder, int position) {
        Reunion reunion = reunionList.get(position);
        holder.libelleR.setText("libelle: "+reunion.getLibelle());
        holder.dateR.setText(reunion.getDate());
        holder.salleR.setText(reunion.getSalle());
        Reunion currentReunion = reunionList.get(position);
        holder.itemView.setTag(reunion.getIdReunion());

        if (reunionList.isEmpty()) {
           activity_get_reunion_prof.noDataVieww.setVisibility(View.VISIBLE);
        } else {
            activity_get_reunion_prof.noDataVieww.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = holder.getAdapterPosition();
               // notifyItemRemoved(selectedIndex);
                String libelle = reunionList.get(selectedIndex).getLibelle();
                String date = reunionList.get(selectedIndex).getDate();
                String salle = reunionList.get(selectedIndex).getSalle();

            }
        });
    }


    @Override
    public int getItemCount() {
        return reunionList != null ? reunionList.size() : 0;
    }
}