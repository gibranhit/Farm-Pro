package com.example.gibran.preguntas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gibran.preguntas.ViewHolder.DetallesPuntuacionViewHolder;
import com.example.gibran.preguntas.modelo.QuestionScore;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreDetalles extends AppCompatActivity {

  FirebaseDatabase database;
  DatabaseReference questions_score;

  RecyclerView scoreList;
  RecyclerView.LayoutManager layoutManager;
  FirebaseRecyclerAdapter<QuestionScore,DetallesPuntuacionViewHolder> adapter;

  String viewUser="";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_detalles);


    database = FirebaseDatabase.getInstance();
    questions_score = database.getReference("Questions_Score");

    scoreList = (RecyclerView)findViewById(R.id.scoreList);
    scoreList.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(this);
    scoreList.setLayoutManager(layoutManager);

    if (getIntent() != null)
         viewUser = getIntent().getStringExtra("viewUser");
    if (!viewUser.isEmpty())
        loadDetallesScore(viewUser);
  }

  private void loadDetallesScore(String viewUser) {

    adapter = new FirebaseRecyclerAdapter<QuestionScore, DetallesPuntuacionViewHolder>(
      QuestionScore.class,R.layout.detalles_layout,
      DetallesPuntuacionViewHolder.class,questions_score.orderByChild("user").equalTo(viewUser)) {
      @Override
      protected void populateViewHolder(DetallesPuntuacionViewHolder viewHolder, QuestionScore model, int position) {

        viewHolder.txt_nombre.setText(model.getCategoryName());
        viewHolder.txt_score.setText(model.getScore());

      }
    };
    adapter.notifyDataSetChanged();
    scoreList.setAdapter(adapter);
  }
}
