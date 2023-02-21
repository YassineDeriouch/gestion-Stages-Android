package com.example.myapplication.activities.gestion_comptes.gestion_respo.ListViewHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_get_respo;
import com.example.myapplication.activities.gestion_niv.activity_get_niv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class RespoAdapter extends RecyclerView.Adapter<RespoHolder> implements Filterable {

    public static List<ResponsableStage> responsableStageList;
    List<ResponsableStage> LR;
    public static int id;
    public static String nom;
    public RespoAdapter(List<ResponsableStage> responsableStageList) {
        this.responsableStageList = responsableStageList;
        LR = new ArrayList<>();
    }
    public RespoAdapter(){};

    @Override
    public RespoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_respo_list, parent, false);
        return new RespoHolder(view);
    }


    @Override
    public void onBindViewHolder(RespoHolder holder, int position) {
        ResponsableStage responsableStage = responsableStageList.get(position);
        holder.RespoNom.setText(responsableStage.getPrenom().toString()+" "+ responsableStage.getNom().toString());
        holder.RespoId.setText(Integer.toString(responsableStage.getIdResponsable()));
        ResponsableStage currentRespo = responsableStageList.get(position);
        holder.itemView.setTag(responsableStage.getIdResponsable());

        if (responsableStageList.isEmpty()) {
           activity_get_respo.noDataVieww.setVisibility(View.VISIBLE);
        } else {
            activity_get_respo.noDataVieww.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = holder.getAdapterPosition();
               // notifyItemRemoved(selectedIndex);
                nom = responsableStageList.get(selectedIndex).getNom();
                id = responsableStageList.get(selectedIndex).getIdResponsable();

            }
        });


    }

    @Override
    public int getItemCount() {
        /*if (responsableStageList.isEmpty()) {
            return 0;
        } else {
            return responsableStageList.size();
        }*/

        return responsableStageList != null ? responsableStageList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filterRespoList ;
    }
    private Filter filterRespoList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchTxt = charSequence.toString().toLowerCase(Locale.ROOT).trim();
            List<ResponsableStage> respoListTemp = new ArrayList<>();
            if(searchTxt.length()==0 || searchTxt.isEmpty()||searchTxt==null){
                respoListTemp.addAll(LR);
            }else {
                for (ResponsableStage itemRS : LR){
                    if(itemRS.getNom().toLowerCase().trim().contains(searchTxt)
                            ||(itemRS.getPrenom().toLowerCase().trim().contains(searchTxt))){
                        respoListTemp.add(itemRS);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = LR;
            filterResults.count = LR.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            responsableStageList.clear();
            responsableStageList.addAll((Collection<? extends ResponsableStage>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}