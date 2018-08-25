package com.example.gibran.preguntas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.gibran.preguntas.BroascastReceiver.Alarma;
import com.example.gibran.preguntas.modelo.Usuario;
import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

  MaterialEditText edtNombre,edtApellido ,edtNuevoUser,edtMatricula,edtNuevaContraseña,edtNuevoEmail;// variables para registrarte
  MaterialEditText txtUsuario,txtContraseña;//variables para ingresar
  com.rey.material.widget.CheckBox checkBox;// variable de la caja de "Recordarme"

  Button btnRegistrar,btnIngresar;//variables de los botones de registrar e ingresar

  FirebaseDatabase database;//variable para la base de datos
  DatabaseReference usuarios;// variable para referencias a la base de datos

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Firebase
    database = FirebaseDatabase.getInstance(); // para leer y escribir en la base de datos instaciamos en el "DatabaseReference"
    usuarios = database.getReference("Usuarios");// ahora hacemos referencia  a una llave o "key" para escribir y leer sobre ella, tiene que ser identica a la llave escrita en firebase

    //Variables donde vamos escribir el usuario y contraseña para iniciar el juego
    //para ello vamos a buscarlo mediante su ID y con ello obtenemos sus valores
    txtUsuario = (MaterialEditText)findViewById(R.id.inicialUsuario);
    txtContraseña = (MaterialEditText)findViewById(R.id.inicialPassword);

    //
    checkBox = (com.rey.material.widget.CheckBox) findViewById(R.id.recordar);//variable donde vamos almacenar el checkbox

    //Libreria Paper (Proporciona una opcion de almacenamiento simple pero rapida que permite cambios en la estructura de datos se haga automaticamente)
    //mas informacion sobre esta liberia (https://github.com/pilgr/Paper)
    Paper.init(this);//Inicializamos Paper

    //
    btnIngresar = (Button) findViewById(R.id.btn_sign_in);//variables para indentificar los botones de  de registro e ingreso
    btnRegistrar = (Button) findViewById(R.id.btn_sign_up);

    btnRegistrar.setOnClickListener(new View.OnClickListener() {//cuando presionamos el boton de registrar
      @Override
      public void onClick(View v) {
        mostrarDialogoDeRegistro();// se ejecuta el metodo de registro de usuario
      }
    });

    btnIngresar.setOnClickListener(new View.OnClickListener() {//cuando presionamos el boton de ingresar
      @Override
      public void onClick(View v) {

        //activa el cuadro de diaa de dialogo de espera
        final ProgressDialog dialogoEspera = new ProgressDialog(MainActivity.this);
        dialogoEspera.setMessage("Por favor espere...");
        dialogoEspera.show();

        Ingresar(txtUsuario.getText().toString(),txtContraseña.getText().toString()); // ingresa el usuario y contraseña dentro del metodo de Ingresar

      }
    });

    alarmaRegistro();//se activa el metodo de notificaciones por diarias
    guardarDatos();//se activa el metodo  de leer los datos guardados del usuario para volver a entrar a la app

  }

  private void guardarDatos() {

    String usuario = Paper.book().read(Variables.USER_KEY);// lee el dato del nombre del usuario guardado
    String contraseña = Paper.book().read(Variables.PWD_KEY);// lee el dato del nombre del usuario guardado
    if (usuario != null && contraseña != null){ // si el usuario y la contraseña es diferente a null entra
      if(!usuario.isEmpty() && !contraseña.isEmpty()){// si el usuario y la contraseña es diferente a
        Ingresar(usuario,contraseña);
      }
    }
  }


  private void alarmaRegistro() {
    Calendar calendar = Calendar.getInstance();//Configuramos el tiempo de la notificacion
    calendar.set(Calendar.HOUR_OF_DAY,4);
    calendar.set(Calendar.MINUTE,47);
    calendar.set(Calendar.SECOND,0);

    Intent intent = new Intent(MainActivity.this, Alarma.class); //
    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
    am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
  }


  private void Ingresar(final String usuario,final String contraseña) {

    // para leer los datos de la base usamos el metodo "addListenerForSingleValueEvent()"
    // dentro de ese metodo ocupamos otro metodo onDataChange para leer y detectar cambios dentro de la ruta especificada
    usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.child(usuario).exists()){ //especificamos si el usuario existe dentro de la base de datos y si existe entra

          if(checkBox.isChecked()){ // cuando el checkbox este marcardo entonces guardamos el usuario y la contraseña dentro de la variable especificas que marcamos con la liberia "Paper"
            Paper.book().write(Variables.USER_KEY,txtUsuario.getText().toString());
            Paper.book().write(Variables.PWD_KEY,txtContraseña.getText().toString());

          }if (!usuario.isEmpty()){// si es usuario es diferente a vacio


            Usuario login = dataSnapshot.child(usuario).getValue(Usuario.class); //declaramos la clase Usuario y obtenemos sus valores y los guardamos en login

            if (login.getContraseña().equals(contraseña)){// si la contraseña escrita es igual a la contraseña guardada en la base de datos entra
              Intent homeActivity = new Intent(MainActivity.this,Home.class); //  entonces pasa a la siguiente layout y clase
              Variables.currentUser = login;// y envio toda la informacion a la variables globales para ser mas especifico a la informacion del  usuario
              startActivity(homeActivity);
              finish();

            }else {//si la contraseña no fuera a correcta se muestra el siguiente mensaje
              Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
            }
          }else {//si no hay ninguna usuario ingresado se muestra el siguiente mensaje
            Toast.makeText(MainActivity.this, "Por favor ingresa tu usuario", Toast.LENGTH_SHORT).show();
          }

        }else {// si el usuario no se encuentra en la base de datos se muestra el siguiente mensaje
          Toast.makeText(MainActivity.this, "Usuario no Existente", Toast.LENGTH_SHORT).show();
        }


      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  private void mostrarDialogoDeRegistro() {

    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
    alertDialog.setTitle("Registrarse");
    alertDialog.setMessage("Ingresa la informacion completa");//mostramos los dialogos dentro del layout inflado

    LayoutInflater inflater = this.getLayoutInflater();
    View sign_up_layout = inflater.inflate(R.layout.sign_up_layout,null);

    edtNuevoUser = (MaterialEditText) sign_up_layout.findViewById(R.id.Usuario);
    edtNombre = (MaterialEditText)sign_up_layout.findViewById(R.id.Nombre);
    edtApellido = (MaterialEditText)sign_up_layout.findViewById(R.id.Apellidos);
    edtNuevaContraseña = (MaterialEditText) sign_up_layout.findViewById(R.id.Password);
    edtMatricula = (MaterialEditText) sign_up_layout.findViewById(R.id.Matricula);
    edtNuevoEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.Email);


    alertDialog.setView(sign_up_layout);
    alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

    alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });

    alertDialog.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {



        final Usuario usuario = new Usuario(
          edtNombre.getText().toString(),
          edtApellido.getText().toString(),
          edtNuevoUser.getText().toString(),
          edtMatricula.getText().toString() ,
          edtNuevoEmail.getText().toString(),
          edtNuevaContraseña.getText().toString());

        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.child(usuario.getUsuario()).exists())
            {
              Toast.makeText(MainActivity.this, "El usuario ya existe!",Toast.LENGTH_SHORT).show();
            }else {
              if (usuario.getContraseña().length()<6){
                Toast.makeText(MainActivity.this, "la contraseña es muy corta,intente otra vez ",Toast.LENGTH_SHORT).show();
              }else {
                usuarios.child(usuario.getUsuario()).setValue(usuario);
                Toast.makeText(MainActivity.this, "Registro Exitoso!",Toast.LENGTH_SHORT).show();
              }

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
