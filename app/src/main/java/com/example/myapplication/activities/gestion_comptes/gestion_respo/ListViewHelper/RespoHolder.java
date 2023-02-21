package com.example.myapplication.activities.gestion_comptes.gestion_respo.ListViewHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class RespoHolder extends RecyclerView.ViewHolder{
    public static TextView RespoNom, RespoId;
    public static ImageView del;
    public static List<ResponsableStage>responsableStageList = new ArrayList<>();

    public RespoHolder(View itemView) {
        super(itemView);
        RespoNom = itemView.findViewById(R.id.nomRespoListTV);
        RespoId = itemView.findViewById(R.id.idRespoListTV);
        del = (ImageView) itemView.findViewById(R.id.delete_iv3);

    }
    public static int getId(int position){
        return responsableStageList.get(position).getIdResponsable();
    }


}
