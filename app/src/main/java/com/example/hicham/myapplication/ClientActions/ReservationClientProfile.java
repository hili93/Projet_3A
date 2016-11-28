package com.example.hicham.myapplication.ClientActions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hicham.myapplication.R;

import org.w3c.dom.Text;

public class ReservationClientProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_client_profile);

        Intent intent = getIntent();
        String reservationID = intent.getStringExtra("reservationID");
        String restoName = intent.getStringExtra("restoName");
        String price = intent.getStringExtra("price");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");


        TextView tvReservationID = (TextView)findViewById(R.id.tvReservationID);
        TextView tvRestoName = (TextView)findViewById(R.id.tvRestoName);
        TextView tvTotalPrice = (TextView)findViewById(R.id.tvTotalPrice);
        TextView tvDate = (TextView)findViewById(R.id.tvDate);
        TextView tvTime = (TextView)findViewById(R.id.tvTime);
        RatingBar ratingBar =(RatingBar)findViewById(R.id.ratingBar);

        ratingBar.setNumStars(5);
        ratingBar.setMax(5);
        tvReservationID.setText(reservationID);
        tvRestoName.setText(restoName);
        tvTotalPrice.setText(price);
        tvDate.setText(date);
        tvTime.setText(time);
    }

}
