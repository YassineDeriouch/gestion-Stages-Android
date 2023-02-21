package com.example.myapplication.activities.gestion_comptes.gestion_respo.GrivView_Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Document;
import com.example.myapplication.R;
import com.example.myapplication.activities.gestion_comptes.gestion_respo.activity_get_doc_by_etd;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ETDdocAdapter extends RecyclerView.Adapter<ETDdocHolder> {

        public static List<Document> documentList;
        public static List<Document> filteredList;
        public static int id,idE;
        public static Integer niv;
        public static String email,nivFil;
        private RecyclerViewClickListener mListener;

        public interface RecyclerViewClickListener {
                void onClick(View view, int position);
        }

        private static ETDdocHolder.OnFileClickListener listener;

        public ETDdocAdapter(ETDdocHolder.RecyclerViewClickListener listener) {

                mListener = (RecyclerViewClickListener) listener;
        }

        public ETDdocAdapter(List<Document> documentList) {
                this.documentList = documentList;
                filteredList = new ArrayList<>();
                this.filteredList = documentList;
        }

        public ETDdocAdapter() {
        }

        @Override
        public ETDdocHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_get_doc_by_etd_list, parent, false);
                return new ETDdocHolder(view);
        }


        @Override
        public void onBindViewHolder(ETDdocHolder holder, int position) {
                Document document = documentList.get(position);
                holder.etdEmail.setText("email:"+document.getEtudiant().getEmail().toString());
                holder.etdId.setText("id: "+Integer.toString(document.getEtudiant().getId()));
                holder.etdDateEnvoi.setText(document.getDateEnvoi().toString());
                holder.etdFileName.setText(document.getFileName());
                Document currentETD = documentList.get(position);
                holder.itemView.setTag(document.getIdDocument());

                if (documentList.isEmpty()) {
                        activity_get_doc_by_etd.noDataVieww.setVisibility(View.VISIBLE);
                } else {
                        activity_get_doc_by_etd.noDataVieww.setVisibility(View.GONE);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                int selectedIndex = holder.getAdapterPosition();
                                // notifyItemRemoved(selectedIndex);
                                email = documentList.get(selectedIndex).getEtudiant().getEmail();
                                id = documentList.get(selectedIndex).getIdDocument();
                                idE = documentList.get(selectedIndex).getEtudiant().getId();
                        }
                });
        }


        @Override
        public int getItemCount() {
                return documentList != null ? documentList.size() : 0;
        }





}
    


