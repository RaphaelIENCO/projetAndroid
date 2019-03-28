package com.example.administrateur.projetandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AvisListAdapteur extends RecyclerView.Adapter<AvisViewHolder> {

    private List<Avis> avisList;

    private Context ctxt;

    public AvisListAdapteur(List<Avis> avisList, Context ctxt) {
        this.avisList = avisList;
        this.ctxt = ctxt;
    }

    @Override
    public AvisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_avis,parent,false);
        return new AvisViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( AvisViewHolder holder, int i) {
        Avis avis = avisList.get(i);
        holder.username.setText(avis.getUsername()+",");
        holder.note.setText(avis.getNote()+"/10,");
        holder.date.setText(avis.getDate()+" :");
        holder.avis.setText(avis.getAvis());
    }

    @Override
    public int getItemCount() {
        return avisList.size();
    }
}
