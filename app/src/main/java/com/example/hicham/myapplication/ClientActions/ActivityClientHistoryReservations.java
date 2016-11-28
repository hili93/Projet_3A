package com.example.hicham.myapplication.ClientActions;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityClientHistoryReservations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_client_history_reservations);

        LinearLayout tempLayout1 = new LinearLayout(this);
        tempLayout1.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvReservationID = new TextView(this);
        TextView tvRestoName = new TextView(this);
        TextView tvStatus = new TextView(this);
        tvReservationID.setText("Reservation ID");
        tvRestoName.setText("Restaurant name");
        tvStatus.setText("Payed");

        tvReservationID.setTextColor(Color.parseColor("#FFFFFF"));
        tvRestoName.setTextColor(Color.parseColor("#FFFFFF"));
        tvStatus.setTextColor(Color.parseColor("#FFFFFF"));

        tvReservationID.setWidth(160);
        tvRestoName.setWidth(210);
        tvStatus.setWidth(100);

        tempLayout1.addView(tvReservationID);
        tempLayout1.addView(tvRestoName);
        tempLayout1.addView(tvStatus);

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_area_client_history_reservations);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        ll.addView(tempLayout1, lp);

        final Intent intent = getIntent();
        final String[] restoNames = intent.getStringArrayExtra("restoNames");
        final String[] reservationIDs = intent.getStringArrayExtra("reservationIDs");
        final String[] status = intent.getStringArrayExtra("status");


        for (int i = 0; i < restoNames.length; i++) {
            LinearLayout tempLayout2 = new LinearLayout(this);
            tempLayout2.setOrientation(LinearLayout.HORIZONTAL);

            final String reservationID = reservationIDs[i];
            final String restoName = restoNames[i];
            final String currStatus = status[i];


            Button bReservationID = new Button(this);
            Button bRestoName = new Button(this);
            Button bStatus = new Button(this);
            Button bNotifyArrival = new Button(this);

            bReservationID.setText(reservationID);
            bRestoName.setText(restoName);
            bNotifyArrival.setBackgroundResource(R.drawable.icon_notify);

            bReservationID.setWidth(1);
            bRestoName.setWidth(210);
            bStatus.setWidth(30);

            if (currStatus.equals("1"))
                bStatus.setBackgroundResource(R.drawable.unpayed_icon);
            else
                bStatus.setBackgroundResource(R.drawable.payed_icon);


            bReservationID.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String price = jsonResponse.getString("Price");
                                String date = jsonResponse.getString("Date");
                                String time = jsonResponse.getString("Time");
                                Intent intent = new Intent(ActivityClientHistoryReservations.this, ReservationClientProfile.class);
                                intent.putExtra("reservationID", reservationID);
                                intent.putExtra("restoName", restoName);
                                intent.putExtra("date",date);
                                intent.putExtra("time",time);
                                if(!price.equals("null"))
                                    intent.putExtra("price",price);
                                else
                                    intent.putExtra("price","Bill not edited yet !");
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    RequestReservationBill requestReservationBill = new RequestReservationBill(reservationID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ActivityClientHistoryReservations.this);
                    queue.add(requestReservationBill);
                }


            });


            tempLayout2.addView(bReservationID);
            tempLayout2.addView(bRestoName);
            tempLayout2.addView(bStatus);
            tempLayout2.addView(bNotifyArrival);

            ll.addView(tempLayout2, lp);

        }
    }
}
