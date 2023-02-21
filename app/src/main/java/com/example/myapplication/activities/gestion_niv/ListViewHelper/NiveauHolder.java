package com.example.myapplication.activities.gestion_niv.ListViewHelper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_niv.activity_get_niv;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;


public class NiveauHolder extends RecyclerView.ViewHolder{


    TextView NiveauNom, filiere;
    ImageView edit, del;
    View noDataView;
    public static List<Niveau> niveauList = new ArrayList<>();

    public NiveauHolder(View itemView) {
        super(itemView);
        NiveauNom = itemView.findViewById(R.id.niveauNameTV);
        filiere = itemView.findViewById(R.id.FiliereTV);
        edit= (ImageView) itemView.findViewById(R.id.edit_iv);
        del = (ImageView) itemView.findViewById(R.id.delete_iv);

    }
    public static int getId(int position){
        return niveauList.get(position).getIdNiveau();
    }


}
