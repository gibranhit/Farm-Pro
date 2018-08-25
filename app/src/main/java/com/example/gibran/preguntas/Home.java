package com.example.gibran.preguntas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gibran.preguntas.variables.Variables;

import java.util.Random;

public class Home extends AppCompatActivity {

  BottomNavigationView bottomNavigationView;

  BroadcastReceiver broadcastReceiver;


  @Override
  protected void onPause() {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("registroCompleto"));
    LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Variables.STR_PUSH));
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    registrarNotifiacion();

    bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;
        switch (item.getItemId()){
          case R.id.action_category:
            selectedFragment=CategoryFragment.newInstance();
            break;
          case R.id.action_ranking:
            selectedFragment = RankingFragment.newInstance();
            break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,selectedFragment);
        transaction.commit();
        return true;
      }


    });

    setDefaultFragment();
  }

  private void registrarNotifiacion() {
    broadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Variables.STR_PUSH)){

          String mensaje = intent.getStringExtra("mensaje");
          mostrarNotificacion("Farma-pro",mensaje);

        }


      }
    };
  }

  private void mostrarNotificacion(String titulo, String mensaje) {

    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
    PendingIntent pendingIntent =  PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
    builder.setAutoCancel(true)
      .setDefaults(Notification.DEFAULT_ALL)
      .setWhen(System.currentTimeMillis())
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle(titulo)
      .setContentText(mensaje)
      .setSound(alarmSound)
      .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(new Random().nextInt(),builder.build());
  }

  private void setDefaultFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.frame_layout,CategoryFragment.newInstance());
    transaction.commit();
  }
}
