package com.example.myapplication.activities.gestion_niv.ListViewHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Niveau;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_niv.activity_get_niv;
import com.example.myapplication.utils.Api_Calls_Interface;
import com.example.myapplication.utils.Retrofit_Instance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NiveauAdapter extends RecyclerView.Adapter<NiveauHolder> {

    public static List<Niveau> niveauList;
    public static int id;
    public static String niv,fil;
    public NiveauAdapter(List<Niveau> niveauList) {
        this.niveauList = niveauList;
    }

    @Override
    public NiveauHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_get_niveaux_list, parent, false);
        return new NiveauHolder(view);
    }


    @Override
    public void onBindViewHolder(NiveauHolder holder, int position) {
        Niveau niveau = niveauList.get(position);
        holder.NiveauNom.setText(niveau.getNiveau());
        holder.filiere.setText(niveau.getNomFiliere());
        Niveau currentNiv = niveauList.get(position);
        holder.itemView.setTag(niveau.getIdNiveau());

        if (niveauList.isEmpty()) {
            activity_get_niv.noDataVieww.setVisibility(View.VISIBLE);
        } else {
            activity_get_niv.noDataVieww.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = holder.getAdapterPosition();
                id = niveauList.get(selectedIndex).getIdNiveau();
               // notifyItemRemoved(selectedIndex);
                niv = niveauList.get(selectedIndex).getNiveau();
                fil = niveauList.get(selectedIndex).getNomFiliere();

            }
        });


    }

    @Override
    public int getItemCount() {
        /*if (niveauList.isEmpty()) {
            return 0;
        } else {
            return niveauList.size();
        }*/

        return niveauList != null ? niveauList.size() : 0;
    }

}