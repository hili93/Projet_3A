package com.example.hicham.myapplication.RestaurantActions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.R;
import com.example.hicham.myapplication.WelcomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hicham.myapplication.WelcomeActivity.PREFS_NAME;

public class AreaActivityRestaurant extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_area);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etTypeClient = (EditText) findViewById(R.id.etTypeClient);
        final EditText etCity = (EditText) findViewById(R.id.etCity);

        /*Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String city = intent.getStringExtra("city");
*/
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        final String name = settings.getString("Name", "DefaultName");
        String email = settings.getString("Email", "DefaultEmail");
        String city = settings.getString("City", "DefaultCity");

        String message = name + " , welcome to your account(Restaurant)";
        welcomeMessage.setText(message);
        etName.setText(name);
        etEmail.setText(email);
        etCity.setText(city);
        etTypeClient.setText("Restaurant");


        etName.setEnabled(false);
        etEmail.setEnabled(false);
        etCity.setEnabled(false);
        etTypeClient.setEnabled(false);

        Button bShowReservations = (Button) findViewById(R.id.bShowClientReservations);
        bShowReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonResponse = new JSONArray(response);
                            System.out.println("JSON LENGTH =" + jsonResponse.length());

                            String[] clients = new String[jsonResponse.length()];
                            String[] status = new String[jsonResponse.length()];
                            String[] reservationsIDs = new String[jsonResponse.length()];

                            for (int i = 0; i < jsonResponse.length(); i++) {

                                JSONObject obj = jsonResponse.getJSONObject(i);
                                reservationsIDs[i]= obj.getString("reservation_id");
                                clients[i] = obj.getString("client_id");
                                status[i] = obj.getString("Payed");

                            }
                            Intent intent = new Intent(AreaActivityRestaurant.this, ActivityRestaurantHistoryReservations.class);
                            intent.putExtra("length", clients.length);
                            intent.putExtra("nameResto", name);
                            intent.putExtra("clients", clients);
                            intent.putExtra("status",status);
                            intent.putExtra("reservationIDs",reservationsIDs);

                            System.out.println("RESERVATIONS RESULT:");
                            for (int i = 0; i < clients.length; i++)
                                 System.out.println("Cliend ID = "+clients[i] + "==> Payed = " + status[i]);

                                AreaActivityRestaurant.this.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };
                RequestHistoryReservations requestHistoryReservations = new RequestHistoryReservations(name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AreaActivityRestaurant.this);
                queue.add(requestHistoryReservations);
            }

        });
    }

    public void bLogoutClicked(View v) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();
        System.out.println("SHARED PREF WIPED OUT !!");
        Intent intentLogout = new Intent(AreaActivityRestaurant.this, WelcomeActivity.class);
        startActivity(intentLogout);
    }
}