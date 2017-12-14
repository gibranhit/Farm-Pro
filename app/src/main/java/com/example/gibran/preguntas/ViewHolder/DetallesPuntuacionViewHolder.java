package com.example.gibran.preguntas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gibran.preguntas.R;

/**
 * Created by Gibran on 06/12/2017.
 */

public class DetallesPuntuacionViewHolder extends RecyclerView.ViewHolder {

  public TextView txt_nombre,txt_score;

  public DetallesPuntuacionViewHolder(View itemView) {
    super(itemView);

    txt_nombre = (TextView)itemView.findViewById(R.id.txt_nombre);
    txt_score = (TextView)itemView.findViewById(R.id.txt_puntaje);
  }
}
