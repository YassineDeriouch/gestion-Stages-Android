package com.example.myapplication.activities.gestion_comptes.gestion_etd.ListViewHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ETDHolder extends RecyclerView.ViewHolder{

    public static TextView ETDNom, ETDid, ETD_fil, ETD_niv;
    public static ImageView del;
    public static List<Etudiant>etudiantList = new ArrayList<>();

    public ETDHolder(View itemView) {
        super(itemView);
        ETDNom = itemView.findViewById(R.id.nomETDListTV);
        ETDid = itemView.findViewById(R.id.idETDListTV);
        ETD_fil = itemView.findViewById(R.id.FilETDListTV);
        ETD_niv = itemView.findViewById(R.id.NivETDListTV);
        del = (ImageView) itemView.findViewById(R.id.delete_iv4);

    }
    public static int getId(int position){
        return etudiantList.get(position).getId();
    }


}
