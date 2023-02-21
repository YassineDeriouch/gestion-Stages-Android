package com.example.myapplication.activities.gestion_comptes.gestion_prof.SpinnerHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.Entities.Professeur;


import java.util.List;

public class ProfesseurAdapter extends ArrayAdapter<Professeur> {
    private Context context;
    public static List<Professeur> profList;
    public ProfesseurAdapter(Context context, List<Professeur> profList) {
        super(context, 0, profList);
        this.context = context;
        this.profList = profList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Professeur professeur = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText("Email: "+professeur.getEmail()+" ID: "+professeur.getId() );
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Professeur professeur = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(professeur.getId()+ " - " + professeur.getEmail());
        return convertView;
    }
}
