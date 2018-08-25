package com.example.gibran.preguntas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.gibran.preguntas.modelo.Conexion;
import com.example.gibran.preguntas.modelo.Questions;
import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.StreamHandler;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import fr.ganfra.materialspinner.MaterialSpinner;
import steelkiwi.com.library.DotsLoaderView;

public class Start extends AppCompatActivity {

  FirebaseAnalytics mFirebaseAnalytics;
  FirebaseDatabase database;
  DatabaseReference questions,conexion;
  int intentos = 1;
  CircularProgressButton circularProgressButton, circularProgressButton1;

  Conexion regreso = new Conexion();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);

    database = FirebaseDatabase.getInstance();
    questions = database.getReference("Questions");
    conexion = database.getReference("Conexion");

    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    circularProgressButton = (CircularProgressButton)findViewById(R.id.btnCarga);

    circularProgressButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AsyncTask<String,String,String> carga = new AsyncTask<String, String, String>() {
          @Override
          protected String doInBackground(String... strings) {
            try {
              Thread.sleep(4000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }return "carga lista";
          }

          @Override
          protected void onPostExecute(String s) {
            if (s.equals("carga lista")){
              Toast.makeText(Start.this, "listo", Toast.LENGTH_SHORT).show();
              circularProgressButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));
            }
          }
        };
        circularProgressButton.startAnimation();
        carga.execute();
        circularProgressButton.setEnabled(false);
        circularProgressButton1.setEnabled(false);
        preguntasTres(Variables.categoryId);
        intentos = intentos + intentos;
        timer();
        intentoConexion();
        analisa();
      }
    });

    circularProgressButton1 = (CircularProgressButton)findViewById(R.id.btnCarga6);

    circularProgressButton1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AsyncTask<String,String,String> carga = new AsyncTask<String, String, String>() {
          @Override
          protected String doInBackground(String... strings) {
            try {
              Thread.sleep(4000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }return "done";
          }

          @Override
          protected void onPostExecute(String s) {
            if (s.equals("done")){
              Toast.makeText(Start.this, "Listo", Toast.LENGTH_SHORT).show();
              circularProgressButton1.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));
            }
          }
        };
        circularProgressButton1.startAnimation();
        carga.execute();
        circularProgressButton.setEnabled(false);
        circularProgressButton1.setEnabled(false);
        preguntaSeis(Variables.categoryId);
        intentos = intentos + intentos;
        timer();
        intentoConexion();
        analizar();
      }
    });

  }

  private void analizar() {
    Bundle parametros = new Bundle();
    parametros.putString("Nombre",Variables.currentUser.getNombre());
    parametros.putString("Preguntas","60");
    Log.d("intento",Variables.currentUser.getNombre());
    mFirebaseAnalytics.logEvent("Intentos",parametros);
  }
  private void analisa() {
    Bundle parametros = new Bundle();
    parametros.putString("Nombre",Variables.currentUser.getNombre());
    parametros.putString("Preguntas","30");
    mFirebaseAnalytics.logEvent("Intentos",parametros);
  }


  private void intentoConexion() {

    conexion.child(Variables.currentUser.getNombre()).setValue(new Conexion(intentos));
  }


  private void timer() { //este metodo cuenta un tiempo limite para despues ejecutarse
    new CountDownTimer(5000, 50) {// se especifica el tiempo del contador

      @Override
      public void onTick(long arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onFinish() { // despues de 4 segundos  se ejecuta el siguiente codigo, donde cambia de una actividad a otra
        Intent intent = new Intent(Start.this, Playing.class);
        startActivity(intent);
        finish();

      }
    }.start();
  }


  private void preguntaSeis(String categoryId) {

    //primero, limpia la lista si hay una vieja pregunta
    if (Variables.questionsList.size() > 0)
      Variables.questionsList.clear();

    questions.orderByChild("CategoryId").equalTo(categoryId).limitToFirst(60)
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Questions ques = postSnapshot.getValue(Questions.class);
            Variables.questionsList.add(ques);
            Collections.shuffle(Variables.questionsList);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });

  }

  private void todasPreguntas(String categoryId) {


    //primero, limpia la lista si hay una vieja pregunta
    if (Variables.questionsList.size() > 0)
      Variables.questionsList.clear();

    questions.orderByChild("CategoryId").equalTo(categoryId)
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Questions ques = postSnapshot.getValue(Questions.class);
            Variables.questionsList.add(ques);
            Collections.shuffle(Variables.questionsList);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });


  }

  private void preguntasTres(String categoryId) {


    //primero, limpia la lista si hay una vieja pregunta
    if (Variables.questionsList.size() > 0)
      Variables.questionsList.clear();

    questions.orderByChild("CategoryId").equalTo(categoryId).limitToFirst(30)
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Questions ques = postSnapshot.getValue(Questions.class);
            Variables.questionsList.add(ques);
            Collections.shuffle(Variables.questionsList);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });


  }
}
