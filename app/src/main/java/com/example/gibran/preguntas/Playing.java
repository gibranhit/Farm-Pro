package com.example.gibran.preguntas;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class Playing extends AppCompatActivity implements View.OnClickListener{


  final  static  long INTERVAL = 1000;// 1 segundo
  final  static  long TIMEOUT = 31000; // 30 SEGUNDOS
  int progressValue = 0;

  CountDownTimer mCountDown;

  int index=0,score=0,thisQuestions=0,totalQuestions,correctAnswer;



  ProgressBar progressBar;
  ImageView questions_image;
  Button btnA,btnB,btnC,btnD;
  TextView txtScore,txtQuestionNum,question_text;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_playing);


   Button btncerrar = (Button)findViewById(R.id.cerrar);
     btncerrar.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
          Intent intent = new Intent(Playing.this,Done.class);
          Bundle dataSend = new Bundle();
          dataSend.putInt("SCORE",score);
          dataSend.putInt("TOTAL",totalQuestions);
          dataSend.putInt("CORRECTAS",correctAnswer);
          intent.putExtras(dataSend);
          startActivity(intent);
          finish();

       }
     });
    //Vistas
    txtScore =(TextView)findViewById(R.id.txtScore);
    txtQuestionNum = (TextView)findViewById(R.id.txtTotalQuestion);
    question_text = (TextView)findViewById(R.id.question_text);
    questions_image = (ImageView)findViewById(R.id.question_image);

    progressBar = (ProgressBar)findViewById(R.id.progressBar);

    btnA = (Button)findViewById(R.id.btnAnswerA);
    btnB = (Button)findViewById(R.id.btnAnswerB);
    btnC = (Button)findViewById(R.id.btnAnswerC);
    btnD = (Button)findViewById(R.id.btnAnswerD);

    btnA.setOnClickListener(this);
    btnB.setOnClickListener(this);
    btnC.setOnClickListener(this);
    btnD.setOnClickListener(this);


  }



  @Override
  public void onClick(View view) {

    mCountDown.cancel();
    if (index < totalQuestions)
    { // si todavia tiene preguntas en la lista

      Button  clickedButton = (Button)view;
      if (clickedButton.getText().equals(Variables.questionsList.get(index).getCorrectAnswer()))
      {
        //Si eliges la respuesta correcta
        score +=10;
        correctAnswer++;
        mostrarPregunta(++index);
        //pasa a la siguiente pregunta
      }
      else//i eliges la respuesta incorrecta
        {
          Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
          mostrarPregunta(++index);

          /*Intent intent = new Intent(this,Done.class);
          Bundle dataSend = new Bundle();
          dataSend.putInt("SCORE",score);
          dataSend.putInt("TOTAL",totalQuestions);
          dataSend.putInt("CORRECTAS",correctAnswer);
          intent.putExtras(dataSend);
          startActivity(intent);
          finish();*/

        }
        txtScore.setText(String.format("%d",score));

    }

  }

  private void mostrarPregunta(int index) {

    if (index<totalQuestions)
    {
      thisQuestions++;
      txtQuestionNum.setText(String.format("%d / %d",thisQuestions,totalQuestions));
      progressBar.setProgress(0);
      progressValue=0;
      if (Variables.questionsList.get(index).getIsImageQuestion().equals("true"))
      {
        //si es imagen
        Picasso.with(getBaseContext())
          .load(Variables.questionsList.get(index).getQuestions())
          .into(questions_image);
        questions_image.setVisibility(View.VISIBLE);
        question_text.setVisibility(View.INVISIBLE);

      }
      else
      {
         question_text.setText(Variables.questionsList.get(index).getQuestions());
        questions_image.setVisibility(View.INVISIBLE);
        question_text.setVisibility(View.VISIBLE);
      }

      btnA.setText(Variables.questionsList.get(index).getAnswerA());
      btnB.setText(Variables.questionsList.get(index).getAnswerB());
      btnC.setText(Variables.questionsList.get(index).getAnswerC());
      btnD.setText(Variables.questionsList.get(index).getAnswerD());

      mCountDown.start();// inicia el contador
    }
    else
    {//si es la pregunta final

      Intent intent = new Intent(this,Done.class);
      Bundle dataSend = new Bundle();
      dataSend.putInt("SCORE",score);
      dataSend.putInt("TOTAL",totalQuestions);
      dataSend.putInt("CORRECTAS",correctAnswer);
      intent.putExtras(dataSend);
      startActivity(intent);
      finish();

    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    totalQuestions = Variables.questionsList.size();

    mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
      @Override
      public void onTick(long minisec) {

        progressBar.setProgress(progressValue);
        progressValue++;

      }

      @Override
      public void onFinish() {

        mCountDown.cancel();
        mostrarPregunta(++index);

      }
    };

    mostrarPregunta(index);

  }
}
