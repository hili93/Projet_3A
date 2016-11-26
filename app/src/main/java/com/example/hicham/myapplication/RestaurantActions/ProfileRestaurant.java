package com.example.hicham.myapplication.RestaurantActions;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.ClientActions.AreaActivityClient;
import com.example.hicham.myapplication.ClientActions.RequestReservation;
import com.example.hicham.myapplication.ClientActions.ResultSearchActivity;
import com.example.hicham.myapplication.ClientActions.SearchRequest;
import com.example.hicham.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileRestaurant extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static String nameResto = null;
    public static String nameClient = null;
    Button b_pick;
    TextView tv_result;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_restaurant);

        Intent intent = getIntent();
        nameResto = intent.getStringExtra("nameResto");
        nameClient = intent.getStringExtra("nameClient");
        TextView tvNameResto = (TextView) findViewById(R.id.tvNameResto);
        tvNameResto.setText("Welcome to the " + nameResto + " restaurant");

        b_pick = (Button) findViewById(R.id.bDateTime);
        tv_result = (TextView) findViewById(R.id.tvDateTime);

        b_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileRestaurant.this, ProfileRestaurant.this, year, month, day);
                datePickerDialog.show();
            }
        });

        final Button bReserveClicked = (Button) findViewById(R.id.bReserver);

        bReserveClicked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Reservation done !", Toast.LENGTH_SHORT);
                                toast.show();

                                System.out.println("RESERVATION INFOS");
                                System.out.println("==>resto name: " + nameResto + "\n==>client name: " + nameClient);

                                Button bSelectMenu = new Button(ProfileRestaurant.this);
                                bSelectMenu.setText("Select your menu");

                                LinearLayout ll = (LinearLayout) findViewById(R.id.activity_profile_restaurant);
                                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                                ll.addView(bSelectMenu, lp);
                            }
                        } catch (JSONException e) {
                            Toast toast = Toast.makeText(getApplicationContext(), "ERROR reservation !", Toast.LENGTH_SHORT);
                            toast.show();
                            e.printStackTrace();
                        }
                    }
                };

                RequestReservation reservationRequest = new RequestReservation(nameResto, nameClient,yearFinal+"-"+monthFinal+"-"+dayFinal,hourFinal+":"+minuteFinal, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProfileRestaurant.this);
                queue.add(reservationRequest);

            }


        });

    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        yearFinal = year;
        monthFinal = month;
        dayFinal = day;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(ProfileRestaurant.this, ProfileRestaurant.this, hour, minute, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        hourFinal = hour;
        minuteFinal = minute;

        tv_result.setText("Date: " + dayFinal + "/" + monthFinal + "/" + yearFinal + " Time: " + hourFinal + ":" + minuteFinal);
    }
}