package com.example.gibran.preguntas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gibran.preguntas.modelo.Questions;
import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Random;

public class Start extends AppCompatActivity {

  Button btnPlay;

  FirebaseDatabase database;
  DatabaseReference questions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);

    database = FirebaseDatabase.getInstance();
    questions = database.getReference("Questions");

    cargarPregunta(Variables.categoryId);
    btnPlay = (Button)findViewById(R.id.btnPlay);

    btnPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Start.this,Playing.class);
        startActivity(intent);
        finish();
      }
    });
  }

  private void cargarPregunta(String categoryId) {

    //primero, limpia la lista si hay una vieja pregunta
    if (Variables.questionsList.size()>0)
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
}
