package com.example.myapplication.activities.gestion_comptes.gestion_prof.ListViewHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Etudiant;
import com.example.myapplication.Entities.Professeur;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ProfHolder extends RecyclerView.ViewHolder{

    public static TextView nomProf, idProf;
    public static ImageView del;
    public static List<Professeur>professeurList = new ArrayList<>();

    public ProfHolder(View itemView) {
        super(itemView);
        nomProf = itemView.findViewById(R.id.NomProfListTV);
         idProf = itemView.findViewById(R.id.idProfListTV);
        del = (ImageView) itemView.findViewById(R.id.delete_iv5);

    }

    public static int getId(int position){
        return professeurList.get(position).getId();
    }


}
