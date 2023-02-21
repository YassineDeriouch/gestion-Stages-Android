package com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelperReunion;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.Entities.Reunion;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ReunionProfHolder extends RecyclerView.ViewHolder{

    public static TextView libelleR, dateR, salleR;
    public static List<Reunion>reunionList = new ArrayList<>();

    public ReunionProfHolder(View itemView) {
        super(itemView);
        libelleR = itemView.findViewById(R.id.libelle_reunionTV);
         dateR = itemView.findViewById(R.id.dateReunionV);
        salleR = itemView.findViewById(R.id.salleReunionTV);

    }

    public static int getId(int position){
        return reunionList.get(position).getIdReunion();
    }


}
