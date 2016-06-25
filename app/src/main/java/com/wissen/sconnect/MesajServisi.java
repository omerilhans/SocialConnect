package com.wissen.sconnect;

import android.content.Intent;
import android.net.Uri;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by omer on 22.06.2016.
 */
public class MesajServisi extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String op = remoteMessage.getData().get("op");
        String val = remoteMessage.getData().get("val");

        if (op.equals("web")) {
            if (!val.contains("http://")) {
                val = "http://" + val;
            }
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(val));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }
}
