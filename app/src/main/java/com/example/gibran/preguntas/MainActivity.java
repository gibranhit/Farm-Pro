package com.example.gibran.preguntas;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gibran.preguntas.modelo.Usuario;
import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

  MaterialEditText edtNuevoUser,edtNuevaContraseña,edtNuevoEmail;//para registrarte
  MaterialEditText edtUser,edtPassword;//para ingresar

  Button btnSignUp,btnSingIn;

  FirebaseDatabase database;
  DatabaseReference usuarios;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    //Firebase
    database = FirebaseDatabase.getInstance();
    usuarios = database.getReference("Usuarios");

    edtUser = (MaterialEditText)findViewById(R.id.inicialUsuario);
    edtPassword = (MaterialEditText)findViewById(R.id.inicialPassword);

    btnSingIn = (Button) findViewById(R.id.btn_sign_in);
    btnSignUp = (Button) findViewById(R.id.btn_sign_up);

    btnSignUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showSignUpDialog();
      }
    });

    btnSingIn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SingIn(edtUser.getText().toString(),edtPassword.getText().toString());
      }
    });

  }

  private void SingIn(final String user,final String pwd) {
    usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.child(user).exists()){

          if (!user.isEmpty()){

            Usuario login = dataSnapshot.child(user).getValue(Usuario.class);

            if (login.getContraseña().equals(pwd)){

              Intent homeActivity = new Intent(MainActivity.this,Home.class);
              Variables.currentUser = login;
              startActivity(homeActivity);
              finish();

            }else {
              Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
            }

          }else {
            Toast.makeText(MainActivity.this, "Por favor ingresa tu usuario", Toast.LENGTH_SHORT).show();
          }

        }else {
          Toast.makeText(MainActivity.this, "Usuario no Existente", Toast.LENGTH_SHORT).show();
        }


      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  private void showSignUpDialog() {

    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
    alertDialog.setTitle("Sign Up");
    alertDialog.setMessage("Por favor ingresa la informacion completa");

    LayoutInflater inflater = this.getLayoutInflater();
    View sign_up_layout = inflater.inflate(R.layout.sign_up_layout,null);

    edtNuevoUser = (MaterialEditText) sign_up_layout.findViewById(R.id.Usuario);
    edtNuevaContraseña = (MaterialEditText) sign_up_layout.findViewById(R.id.Password);
    edtNuevoEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.Email);

    alertDialog.setView(sign_up_layout);
    alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });

    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

        final Usuario usuario = new Usuario(edtNuevoUser.getText().toString(),
          edtNuevaContraseña.getText().toString(),
          edtNuevoEmail.getText().toString());

        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.child(usuario.getNombre()).exists())
            {
              Toast.makeText(MainActivity.this, "El usuario ya existe!",Toast.LENGTH_SHORT).show();
            }else {
              usuarios.child(usuario.getNombre()).setValue(usuario);
              Toast.makeText(MainActivity.this, "Registro Exitoso!",Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });
        dialog.dismiss();
      }
    });

    alertDialog.show();


  }
}
