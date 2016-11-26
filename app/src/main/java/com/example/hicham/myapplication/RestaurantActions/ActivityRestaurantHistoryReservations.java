package com.example.hicham.myapplication.RestaurantActions;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.ClientActions.ActivityClientHistoryReservations;
import com.example.hicham.myapplication.ClientActions.RequestReservationBill;
import com.example.hicham.myapplication.ClientActions.ReservationClientProfile;
import com.example.hicham.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hicham.myapplication.R.color.white;
import static com.example.hicham.myapplication.WelcomeActivity.PREFS_NAME;

public class ActivityRestaurantHistoryReservations extends AppCompatActivity {
    //public String[] clients = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_history_reservations);

        Intent intent = getIntent();
        final String nameResto = intent.getStringExtra("nameResto");
        final int length = intent.getIntExtra("length", 0);

        TextView etWelcome = (TextView) findViewById(R.id.tvWelcomeReservations);


        etWelcome.setText(nameResto + ", Welcome to your reservations history");
        etWelcome.setTextColor(Color.parseColor("#FFFFFF"));

        String[] clients = new String[length];
        String[] status = new String[length];
        String[] reservationIDs = new String[length];

        clients = intent.getStringArrayExtra("clients");
        status = intent.getStringArrayExtra("status");
        reservationIDs = intent.getStringArrayExtra("reservationIDs");

        if (length == 0) {
            System.out.println("NO RESERVATION FOUND");

            //Creating EditText to display " No reservation found !"
            EditText etNoReservationFound = new EditText(this);
            etNoReservationFound.setId(0);
            etNoReservationFound.setText("No reservation found !");

            LinearLayout ll = (LinearLayout) findViewById(R.id.activity_restaurant_history_reservations);
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            ll.addView(etNoReservationFound, lp);
        } else {
            // creating buttons for reservations history dynamically
            for (int i = 0; i < length; i++) {
                final String clientID = clients[i];
                final String currStatus = status[i];
                final String reservationID = reservationIDs[i];

                LinearLayout tempLayout = new LinearLayout(this);
                tempLayout.setOrientation(LinearLayout.HORIZONTAL);

                Button bClientID = new Button(this);
                Button bStatus = new Button(this);

                bClientID.setId(i);
                bClientID.setWidth(800);
                bClientID.setText(clients[i]);

                if (currStatus.equals("1"))
                    bStatus.setBackgroundResource(R.drawable.unpayed_icon);
                else
                    bStatus.setBackgroundResource(R.drawable.payed_icon);

                tempLayout.addView(bClientID);
                tempLayout.addView(bStatus);

                bClientID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String price = jsonResponse.getString("Price");
                                    String date = jsonResponse.getString("Date");
                                    String time = jsonResponse.getString("Time");
                                    String clientName = jsonResponse.getString("clientName");
                                    Intent intentProfil = new Intent(ActivityRestaurantHistoryReservations.this, ReservationRestaurantProfile.class);
                                    intentProfil.putExtra("clientID", clientID);
                                    intentProfil.putExtra("reservationID", reservationID);
                                    intentProfil.putExtra("date",date);
                                    intentProfil.putExtra("time",time);
                                    intentProfil.putExtra("clientName",clientName);
                                    startActivity(intentProfil);

                                    if (!price.equals("null"))
                                        intentProfil.putExtra("price", price);
                                    else
                                        intentProfil.putExtra("price", "Bill not edited yet !");
                                    startActivity(intentProfil);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };


                        //////////////////////////////////////////////////////////////////////////////////////////////////


                        RequestReservationBill requestReservationBill = new RequestReservationBill(reservationID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ActivityRestaurantHistoryReservations.this);
                        queue.add(requestReservationBill);
                    }
                });


                LinearLayout ll = (LinearLayout) findViewById(R.id.activity_restaurant_history_reservations);
                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                ll.addView(tempLayout, lp);
            }
        }
    }



























   /* public class ReservationsHistoryAsyncTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            final String name = settings.getString("Name", "DefaultName");


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {
                    System.out.println(response);
                    try {
                        JSONArray jsonResponse = new JSONArray(response);
                        System.out.println("JSON LENGTH =" + jsonResponse.length());
                        clients = new String[jsonResponse.length()];
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject obj = jsonResponse.getJSONObject(i);
                            clients[i] = obj.getString("client_id");
                        }

                        System.out.println("RESERVATIONS RESULT ASYNCTAAAAAAAAAASK:");
                        for (int i = 0; i < clients.length; i++)
                            System.out.println(clients[i]);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RequestHistoryReservations requestHistoryReservations = new RequestHistoryReservations(name, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ActivityRestaurantHistoryReservations.this);
            queue.add(requestHistoryReservations);
            return clients;
        }

        public void onPostExecute(String[] resClients){
            System.out.println("resultOnPostExecute ===:");
            for(int i=0;i<resClients.length;i++){
                System.out.println(resClients[i]);
            }
        }
    }*/

    public void bRefreshClicked(View v) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final String name = settings.getString("Name", "DefaultName");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    System.out.println("JSON LENGTH =" + jsonResponse.length());
                    String[] clients = new String[jsonResponse.length()];
                    for (int i = 0; i < jsonResponse.length(); i++) {

                        JSONObject obj = jsonResponse.getJSONObject(i);
                        clients[i] = obj.getString("client_id");
                    }

                    Intent intent = new Intent(ActivityRestaurantHistoryReservations.this, ActivityRestaurantHistoryReservations.class);
                    intent.putExtra("length", clients.length);
                    intent.putExtra("nameResto", name);
                    intent.putExtra("clients", clients);

                    System.out.println("RESERVATIONS RESULT:");
                    for (int i = 0; i < clients.length; i++)
                        System.out.println(clients[i]);

                    ActivityRestaurantHistoryReservations.this.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        RequestHistoryReservations requestHistoryReservations = new RequestHistoryReservations(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ActivityRestaurantHistoryReservations.this);
        queue.add(requestHistoryReservations);
    }
}
