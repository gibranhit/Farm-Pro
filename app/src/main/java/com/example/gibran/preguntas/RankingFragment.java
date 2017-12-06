package com.example.gibran.preguntas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gibran.preguntas.Interface.ItemClickListener;
import com.example.gibran.preguntas.Interface.LlamarRanking;
import com.example.gibran.preguntas.ViewHolder.RankingViewHolder;
import com.example.gibran.preguntas.modelo.QuestionScore;
import com.example.gibran.preguntas.modelo.Ranking;
import com.example.gibran.preguntas.variables.Variables;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {

  View myFragment;

  RecyclerView rankingList;
  LinearLayoutManager layoutManager;
  FirebaseRecyclerAdapter<Ranking,RankingViewHolder> adapter;

  FirebaseDatabase database;
  DatabaseReference questionScore,rankingTbl;
  int sum = 0;

  public static RankingFragment newInstance(){
    RankingFragment rankingFragment = new RankingFragment();
    return rankingFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    database = FirebaseDatabase.getInstance();
    questionScore = database.getReference("Questions_Score");
    rankingTbl = database.getReference("Ranking");


  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    myFragment =  inflater.inflate(R.layout.fragment_ranking,container,false);

    rankingList = (RecyclerView)myFragment.findViewById(R.id.rankingList);
    layoutManager = new LinearLayoutManager(getActivity());
    rankingList.setHasFixedSize(true);

    layoutManager.setReverseLayout(true);
    layoutManager.setStackFromEnd(true);
    rankingList.setLayoutManager(layoutManager);

    actulizarScore(Variables.currentUser.getNombre(), new LlamarRanking<Ranking>() {
      @Override
      public void llamada(Ranking ranking) {
        //actulizamos el ranking
        rankingTbl.child(ranking.getUserName())
          .setValue(ranking);
        //mostrarRanking();//despues subimos el ranking y lo mostramos los resultados
      }
    });

    adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
      Ranking.class,R.layout.layout_ranking,RankingViewHolder.class,rankingTbl.orderByChild("score")
    ) {
      @Override
      protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {

        viewHolder.txt_nombre.setText(model.getUserName());
        viewHolder.txt_puntaje.setText(String.valueOf(model.getScore()));

        viewHolder.setItemClickListener(new ItemClickListener() {
          @Override
          public void onClick(View view, int position, boolean isLongClick) {

          }
        });
      }
    };

    adapter.notifyDataSetChanged();
    rankingList.setAdapter(adapter);

    return myFragment;
  }




  private void actulizarScore(final String nombre, final LlamarRanking<Ranking> llamada) {

    questionScore.orderByChild("user").equalTo(nombre)
      .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot data:dataSnapshot.getChildren())
          {
            QuestionScore ques = data.getValue(QuestionScore.class);
             sum = sum + Integer.parseInt(ques.getScore());

          }
          Ranking ranking = new Ranking(nombre,sum);
          llamada.llamada(ranking);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
  }
}
