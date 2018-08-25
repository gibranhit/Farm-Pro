package com.example.gibran.preguntas.BroascastReceiver;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Gibran on 25/01/2018.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {
  @Override
  public void onTokenRefresh() {
    super.onTokenRefresh();
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    enviarToken(refreshedToken);

  }

  private void enviarToken(String refreshedToken) {
    Log.d("Token: ",refreshedToken);

  }

}
