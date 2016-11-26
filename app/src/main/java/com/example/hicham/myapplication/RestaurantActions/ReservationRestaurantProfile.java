package com.example.hicham.myapplication.RestaurantActions;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.ClientActions.ActivityClientHistoryReservations;
import com.example.hicham.myapplication.R;

import org.w3c.dom.Text;

public class ReservationRestaurantProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_profile);

        Intent intent = getIntent();
        final String reservationID = intent.getStringExtra("reservationID");
        final String clientID = intent.getStringExtra("clientID");
        final String clientName = intent.getStringExtra("clientName");
        final String date = intent.getStringExtra("date");
        final String time = intent.getStringExtra("time");

        TextView tvWelcomeReservation = (TextView) findViewById(R.id.tvWelcomeReservation);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvReservationID = (TextView) findViewById(R.id.tvReservationID);
        TextView tvClientName = (TextView) findViewById(R.id.tvClientName);
        TextView tvClientID = (TextView) findViewById(R.id.tvClientID);

        tvWelcomeReservation.setText("Reservation details, you can edit the bill and send it to the client");
        tvWelcomeReservation.setTextColor(Color.parseColor("#FFFFFF"));

        tvDate.setText(date);
        tvTime.setText(time);
        tvReservationID.setText(reservationID);
        tvClientID.setText(clientID);
        tvClientName.setText(clientName);


        final Button bSendFacture = (Button) findViewById(R.id.bSendFacture);

        bSendFacture.setOnClickListener(new View.OnClickListener() {
            EditText etFactureTotal = (EditText) findViewById(R.id.etFacutreTotal);

            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        EditText etFactureTotal = (EditText) findViewById(R.id.etFacutreTotal);
                        String price = etFactureTotal.getText().toString();

                        System.out.println("BILL INFOS " + price);


                        Toast toast = Toast.makeText(getApplicationContext(), "Bill of " + price + " sent !", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                };

                RequestSendBill requestSendBill = new RequestSendBill(reservationID, clientID, etFactureTotal.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationRestaurantProfile.this);
                queue.add(requestSendBill);
            }
        });
    }
}
