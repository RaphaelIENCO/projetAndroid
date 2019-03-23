package com.example.administrateur.projetandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class AvisViewHolder extends RecyclerView.ViewHolder {

    public TextView username, note, date, avis;

    public AvisViewHolder(View itemView) {
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.text_view_username);
        note = (TextView) itemView.findViewById(R.id.text_view_note);
        date = (TextView) itemView.findViewById(R.id.text_view_date);
        avis = (TextView) itemView.findViewById(R.id.text_view_avis);
    }


}
