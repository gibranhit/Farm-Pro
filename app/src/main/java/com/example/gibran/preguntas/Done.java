package com.example.gibran.preguntas;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gibran.preguntas.modelo.Category;
import com.example.gibran.preguntas.modelo.QuestionScore;
import com.example.gibran.preguntas.modelo.Questions;
import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends AppCompatActivity {

  Button btnTryAgain;
  TextView txtResultScore,getTxtResultQuestions,getTxtIcorrectQuestions;
  ProgressBar progressBar;

  FirebaseDatabase database;
  DatabaseReference questions_score;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_done);

    database = FirebaseDatabase.getInstance();
    questions_score = database.getReference("Questions_Score");

    txtResultScore = (TextView)findViewById(R.id.txtTotalScore);
    getTxtIcorrectQuestions = (TextView)findViewById(R.id.txtTotalQuestionIncorrect);
    getTxtResultQuestions = (TextView)findViewById(R.id.txtTotalQuestion);
    progressBar = (ProgressBar)findViewById(R.id.doneProgressBar);
    btnTryAgain = (Button)findViewById(R.id.btnTryAgain);

    btnTryAgain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Done.this, Home.class);
        startActivity(intent);
        finish();

      }
    });

    Bundle extra = getIntent().getExtras();
    if (extra!=null)
    {
      int score = extra.getInt("SCORE");
      int totalQuestions = extra.getInt("TOTAL");
      int correctAnswer = extra.getInt("CORRECTAS");
      int incorrectAnswer = extra.getInt("INCORRECTAS");

      txtResultScore.setText(String.format("PUNTAJE : %d",score));
      getTxtResultQuestions.setText(String.format("CORRECTAS : %d / %d",correctAnswer,totalQuestions));
      getTxtIcorrectQuestions.setText(String.format("INCORRECTAS : %d",incorrectAnswer));

      progressBar.setMax(totalQuestions);
      progressBar.setProgress(correctAnswer);

      //subir informacio a la base de datos
      questions_score.child(String.format("%s_%s", Variables.currentUser.getNombre(),Variables.categoryId))
                                           .setValue(new QuestionScore(String.format("%s_%s",
                                             Variables.currentUser.getNombre(),
                                             Variables.categoryId),
                                             Variables.currentUser.getNombre(),
                                             String.valueOf(score),
                                             Variables.categoryId,
                                             Variables.categoryName));
    }
  }
}
