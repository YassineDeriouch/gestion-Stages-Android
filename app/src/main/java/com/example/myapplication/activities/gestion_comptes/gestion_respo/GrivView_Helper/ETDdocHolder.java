package com.example.myapplication.activities.gestion_comptes.gestion_respo.GrivView_Helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Entities.Document;
import com.example.myapplication.Entities.ResponsableStage;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ETDdocHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static TextView etdEmail, etdId, etdDateEnvoi, etdFileName;
    public static ImageView file_iv;
    public static List<Document> documentList = new ArrayList<>();
    private OnFileClickListener listener;


    public ETDdocHolder(View itemView) {
        super(itemView);
        etdEmail = itemView.findViewById(R.id.gv_etd_email);
        etdId = itemView.findViewById(R.id.gv_etd_ID);
        file_iv = (ImageView) itemView.findViewById(R.id.iv_etd_file);
        etdDateEnvoi = itemView.findViewById(R.id.gv_etd_DateEnvoi);
        etdFileName = itemView.findViewById(R.id.gv_etd_filename);
    }

    public static int getId(int position) {
        return documentList.get(position).getIdDocument();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    private RecyclerViewClickListener mListener;

    ETDdocHolder(View v, RecyclerViewClickListener listener) {
        super(v);
        mListener = listener;
        v.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        mListener.onClick(view, getAdapterPosition());
        mListener.onClick(view, documentList.get(getPosition()).getIdDocument());
    }


    void bind(final Document document) {
        ETDdocHolder.file_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFileClick(document);
            }
        });
    }

    interface OnFileClickListener {
    void onFileClick(Document document);
}

}