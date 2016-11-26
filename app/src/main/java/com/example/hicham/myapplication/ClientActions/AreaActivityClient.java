package com.example.hicham.myapplication.ClientActions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

public class AreaActivityClient extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_area);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etFamilyName = (EditText) findViewById(R.id.etFamilyName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etTypeClient = (EditText) findViewById(R.id.etTypeClient);
        final EditText etCity = (EditText) findViewById(R.id.etCity);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String nameClient = settings.getString("Name", "Default");
        String familyName = settings.getString("FamilyName", "Default");
        String email = settings.getString("Email", "Default");
        String city = settings.getString("City", "Default");


        String message = nameClient + " " + familyName + ", welcome to your account(Client)";
        welcomeMessage.setText(message);
        etName.setText(nameClient);
        etFamilyName.setText(familyName);
        etEmail.setText(email);
        etCity.setText(city);
        etTypeClient.setText("Client");


        etName.setEnabled(false);
        etFamilyName.setEnabled(false);
        etEmail.setEnabled(false);
        etCity.setEnabled(false);
        etTypeClient.setEnabled(false);
    }

    public void bSearchRestaurantClicked(View v) {

        final EditText etCity = (EditText) findViewById(R.id.etSearchRestaurantsCity);
        final String city = etCity.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);

                    String[] nameRestos = new String[jsonResponse.length()];
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject obj = jsonResponse.getJSONObject(i);
                        nameRestos[i] = obj.getString("name");
                    }

                    Intent getIntent = new Intent();
                    Intent intent = new Intent(AreaActivityClient.this, ResultSearchActivity.class);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    String nameClient = settings.getString("Name", "Default");

                    intent.putExtra("city", city);
                    intent.putExtra("nameClient", nameClient);
                    intent.putExtra("length", nameRestos.length);
                    intent.putExtra("nameRestos", nameRestos);

                    AreaActivityClient.this.startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SearchRequest searchRequest = new SearchRequest(city, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AreaActivityClient.this);
        queue.add(searchRequest);

    }

    public void bShowClientReservationsClicked(View v) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final String Email = settings.getString("Email", "Default");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    System.out.println("JSON LENGTH =" + jsonResponse.length());

                    String[] restoNames = new String[jsonResponse.length()];
                    String[] status = new String[jsonResponse.length()];
                    String[] reservationIDs = new String[jsonResponse.length()];

                    for (int i = 0; i < jsonResponse.length(); i++) {

                        JSONObject obj = jsonResponse.getJSONObject(i);
                        restoNames[i] = obj.getString("restoName");
                        reservationIDs[i] = obj.getString("reservation_id");
                        status[i] = obj.getString("Payed");
                    }

                    System.out.println("CLIENT RESERVATIONS: ");
                    for(int i=0;i<jsonResponse.length();i++){
                        System.out.println("ID RESERVATION = "+reservationIDs[i]+" ==> RESTO NAME = "+ restoNames[i] +" ==> PAYED = "+status[i]);
                    }

                    Intent intent = new Intent(AreaActivityClient.this, ActivityClientHistoryReservations.class);
                    intent.putExtra("restoNames",restoNames);
                    intent.putExtra("reservationIDs",reservationIDs);
                    intent.putExtra("status",status);

                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestClientReservations requestClientReservations = new RequestClientReservations(Email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AreaActivityClient.this);
        queue.add(requestClientReservations);


    }

    public void bLogoutClicked(View v) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();
        System.out.println("SHARED PREF WIPED OUT !!");
        Intent intentLogout = new Intent(AreaActivityClient.this, WelcomeActivity.class);
        startActivity(intentLogout);
    }


}

