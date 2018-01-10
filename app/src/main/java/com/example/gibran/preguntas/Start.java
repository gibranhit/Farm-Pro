package com.example.gibran.preguntas;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.gibran.preguntas.modelo.Questions;
import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Start extends AppCompatActivity {

  Button btnTodasPreguntas,btn30,btnSeis,btnNueve;
  FirebaseDatabase database;
  DatabaseReference questions;
  MaterialSpinner spinner;
  List<Integer> ListItems = new ArrayList<>();
  ArrayAdapter<Integer> adapter;
  //RadioButton r3, r6, r9;
  int valor;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);


    database = FirebaseDatabase.getInstance();
    questions = database.getReference("Questions");

    btn30 = (Button)findViewById(R.id.btn30);

    btn30.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        preguntasTres(Variables.categoryId);
        timer();

      }
    });

    btnSeis = (Button) findViewById(R.id.btnseis);

    btnSeis.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        preguntaSeis(Variables.categoryId);
        timer();
      }
    });

    btnNueve = (Button)findViewById(R.id.btnnueve);
    btnNueve.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        preguntasNueve(Variables.categoryId);
        timer();
      }
    });

    btnTodasPreguntas = (Button) findViewById(R.id.btnTodasPreguntas);

    btnTodasPreguntas.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        todasPreguntas(Variables.categoryId);
        timer();
      }
    });

  }

  private void timer() {
    new CountDownTimer(4000, 50) {

      @Override
      public void onTick(long arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onFinish() {
        Intent intent = new Intent(Start.this, Playing.class);
        startActivity(intent);
        finish();

      }
    }.start();
  }


  private void preguntaSeis(String categoryId) {

  /*  r3 = (RadioButton) findViewById(R.id.tres);
    r6 = (RadioButton) findViewById(R.id.seis);
    r9 = (RadioButton) findViewById(R.id.nueve);
    valor();*/

  /*  spinner=(MaterialSpinner) findViewById(R.id.size_spinner);
    adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,ListItems);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);


    valorPreguntas();

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {

        valor = Integer.parseInt(spinner.getItemAtPosition(posicion).toString());
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });*/


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

/*  private int valor() {

    if (r3.isChecked()) {


      valor = 30;


    }

    if (r6.isChecked()) {


      valor = 60;


    }
    if (r9.isChecked()) {

      valor = 90;


    }
    return valor;


  }*/


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

  private void preguntasNueve(String categoryId) {


    //primero, limpia la lista si hay una vieja pregunta
    if (Variables.questionsList.size() > 0)
      Variables.questionsList.clear();

    questions.orderByChild("CategoryId").equalTo(categoryId).limitToFirst(90)
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
