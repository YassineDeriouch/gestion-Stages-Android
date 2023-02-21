package com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_prof.activity_get_prof;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_get_respo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ProfAdapter extends RecyclerView.Adapter<ProfHolder> implements Filterable {

    public static List<Professeur> professeurList;
    List<Professeur> LP;
    public static int id;
    public static String nom;
    public ProfAdapter(List<Professeur> professeurList) {
        this.professeurList = professeurList;
        LP = new ArrayList<>();
    }
    public ProfAdapter(){};

    @Override
    public ProfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_prof_list, parent, false);
        return new ProfHolder(view);
    }


    @Override
    public void onBindViewHolder(ProfHolder holder, int position) {
        Professeur professeur = professeurList.get(position);
        holder.nomProf.setText(professeur.getPrenom()+" "+professeur.getNom());
        holder.idProf.setText(Integer.toString(professeur.getId()));
        Professeur currentProf = professeurList.get(position);
        holder.itemView.setTag(professeur.getId());

        if (professeurList.isEmpty()) {
           activity_get_prof.noDataVieww.setVisibility(View.VISIBLE);
        } else {
            activity_get_prof.noDataVieww.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = holder.getAdapterPosition();
               // notifyItemRemoved(selectedIndex);
                nom = professeurList.get(selectedIndex).getNom();
                id = professeurList.get(selectedIndex).getId();

            }
        });
    }


    @Override
    public int getItemCount() {
        return professeurList != null ? professeurList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filterRespoList ;
    }
    private Filter filterRespoList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchTxt = charSequence.toString().toLowerCase(Locale.ROOT).trim();
            List<Professeur> respoListTemp = new ArrayList<>();
            if(searchTxt.length()==0 || searchTxt.isEmpty()||searchTxt==null){
                respoListTemp.addAll(LP);
            }else {
                for (Professeur itemRS : LP){
                    if(itemRS.getNom().toLowerCase().trim().contains(searchTxt)
                            ||(itemRS.getPrenom().toLowerCase().trim().contains(searchTxt))){
                        respoListTemp.add(itemRS);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = LP;
            filterResults.count = LP.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            professeurList.clear();
            professeurList.addAll((Collection<? extends Professeur>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}