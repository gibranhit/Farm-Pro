package com.example.gibran.preguntas.BroascastReceiver;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.gibran.preguntas.variables.Variables;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
/**
 * Created by Gibran on 26/01/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {

    handleNotification(remoteMessage.getNotification().getBody());

  }

  private void handleNotification(String body) {

    Intent pushNotificacion = new Intent(Variables.STR_PUSH);
    pushNotificacion.putExtra("mensaje",body);
    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotificacion);

  }
}
