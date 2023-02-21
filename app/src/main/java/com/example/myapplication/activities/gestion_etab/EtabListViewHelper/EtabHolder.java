package com.example.myapplication.activities.gestion_etab.EtabListViewHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Etablissement;
import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class EtabHolder extends RecyclerView.ViewHolder{

    public static TextView nomEtab, desc,idResp;
    public static ImageView editE, delE;

    public static List<Etablissement> etablissementList = new ArrayList<>();

    public EtabHolder(View itemView) {
        super(itemView);
        nomEtab = itemView.findViewById(R.id.nomEtabTV);
        idResp = itemView.findViewById(R.id.idRespoEtabTV);
        desc = itemView.findViewById(R.id.descEtabTV);
        editE= itemView.findViewById(R.id.editEtabIV);
        delE = itemView.findViewById(R.id.deleteEtabIV);

    }
    public static int getId(int position){
        return etablissementList.get(position).getIdEtablissement();
    }


}
