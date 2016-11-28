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
import com.example.hicham.myapplication.ClientActions.ReservationClientProfile;
import com.example.hicham.myapplication.R;
import com.example.hicham.myapplication.RestaurantActions.ActivityRestaurantHistoryReservations;
import com.example.hicham.myapplication.RestaurantActions.AreaActivityRestaurant;
import com.example.hicham.myapplication.RestaurantActions.RequestHistoryReservations;
import com.example.hicham.myapplication.RestaurantActions.ReservationRestaurantProfile;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.example.hicham.myapplication.WelcomeActivity.PREFS_NAME;

/**
 * Created by hicham on 13/11/2016.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        TreeMap<String, String> dataTable = new TreeMap<>();

        dataTable.put("reservationID", remoteMessage.getData().get("reservationID"));
        dataTable.put("clientID", remoteMessage.getData().get("clientID"));
        dataTable.put("clientName", remoteMessage.getData().get("clientName"));
        dataTable.put("date", remoteMessage.getData().get("date"));
        dataTable.put("time", remoteMessage.getData().get("time"));

        if(remoteMessage.getData().get("notificationType").equals("bill")){
            dataTable.put("price", remoteMessage.getData().get("price"));
            dataTable.put("restoName", remoteMessage.getData().get("restoName"));
        }


        showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("notificationType"), dataTable);
    }

    private void showNotification(String title, String message, String notificationType, TreeMap dataTable) {

        Intent intent = null;

        Set setKey = dataTable.keySet();
        Iterator it = setKey.iterator();

        while( it.hasNext()){
            String key = (String)it.next();
            String value =((String) dataTable.get(key));

            System.out.println(key+" "+value+"\n");
        }
        if (notificationType.equals("reservation")) {
            intent = new Intent(this, ReservationRestaurantProfile.class);
            intent.putExtra("reservationID",dataTable.get("reservationID").toString());
            intent.putExtra("clientID",dataTable.get("clientID").toString());
            intent.putExtra("clientName",dataTable.get("clientName").toString());
            intent.putExtra("date",dataTable.get("date").toString());
            intent.putExtra("time",dataTable.get("time").toString());

        } else if (notificationType.equals("bill")) {
            intent = new Intent(this, ReservationClientProfile.class);
            intent.putExtra("reservationID",dataTable.get("reservationID").toString());
            intent.putExtra("clientID",dataTable.get("clientID").toString());
            intent.putExtra("clientName",dataTable.get("clientName").toString());
            intent.putExtra("price",dataTable.get("price").toString());
            intent.putExtra("date",dataTable.get("date").toString());
            intent.putExtra("time",dataTable.get("time").toString());
            intent.putExtra("restoName",dataTable.get("restoName").toString());
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
