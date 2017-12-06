package com.example.gibran.preguntas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gibran.preguntas.Interface.ItemClickListener;
import com.example.gibran.preguntas.R;

/**
 * Created by Gibran on 01/12/2017.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView txt_nombre,txt_puntaje;
  private ItemClickListener itemClickListener;

  public RankingViewHolder(View itemView) {
    super(itemView);
    txt_nombre = (TextView)itemView.findViewById(R.id.txt_nombre);
    txt_puntaje = (TextView)itemView.findViewById(R.id.txt_puntaje);

    itemView.setOnClickListener(this);
  }

  public  void setItemClickListener(ItemClickListener itemClickListener){
    this.itemClickListener = itemClickListener;
  }



  public void setTxt_nombre(TextView txt_nombre) {
    this.txt_nombre = txt_nombre;
  }

  @Override
  public void onClick(View view) {

    itemClickListener.onClick(view,getAdapterPosition(),false);

  }
}
