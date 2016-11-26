package com.example.hicham.myapplication.ClientActions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hicham.myapplication.R;
import com.example.hicham.myapplication.RestaurantActions.ProfileRestaurant;

import org.w3c.dom.Text;

public class ResultSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        Intent intent = getIntent();
        final String city = intent.getStringExtra("city");
        final int length = intent.getIntExtra("length", 0);
        final String nameClient = intent.getStringExtra("nameClient");
        String[] nameRestos = new String[length];
        nameRestos = intent.getStringArrayExtra("nameRestos");

        TextView etWelcomeSearch = (TextView) findViewById(R.id.etWelcomeSearch);
        etWelcomeSearch.setText("Restaurants available in " + city);


        System.out.println("Search results: ");
        for (int i = 0; i < length; i++)
            System.out.println("==>"+nameRestos[i]);

        // creating buttons for restaurants dynamically
        for (int i = 0; i < length; i++) {
            final String nameResto = nameRestos[i];
            Button bCityResult = new Button(this);
            bCityResult.setId(i);
            bCityResult.setText(nameRestos[i]);

            bCityResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentProfil = new Intent(ResultSearchActivity.this,ProfileRestaurant.class);

                    intentProfil.putExtra("nameResto",nameResto);
                    intentProfil.putExtra("nameClient",nameClient);
                    startActivity(intentProfil);
                }
            });

            LinearLayout ll = (LinearLayout) findViewById(R.id.activity_result_search);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ll.addView(bCityResult, lp);
        }

    }
}

