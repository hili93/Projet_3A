package com.example.hicham.myapplication.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.ClientActions.AreaActivityClient;
import com.example.hicham.myapplication.R;
import com.example.hicham.myapplication.RestaurantActions.ActivityRestaurantHistoryReservations;
import com.example.hicham.myapplication.RestaurantActions.AreaActivityRestaurant;
import com.example.hicham.myapplication.RestaurantActions.RequestHistoryReservations;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hicham.myapplication.WelcomeActivity.PREFS_NAME;

/**
 * Created by hicham on 13/11/2016.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"),remoteMessage.getData().get("notificationType"));
    }

    private void showNotification(String title, String message, String notificationType) {

        Intent i = null;
        if(notificationType.equals("reservation"))
            i = new Intent(this, AreaActivityRestaurant.class);
        else if(notificationType.equals("bill"))
            i = new Intent(this, AreaActivityClient.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }


}
