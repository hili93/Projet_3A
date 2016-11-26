package com.example.hicham.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.hicham.myapplication.ClientActions.AreaActivityClient;
import com.example.hicham.myapplication.Loging.LoginActivity;
import com.example.hicham.myapplication.RestaurantActions.AreaActivityRestaurant;

public class WelcomeActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //OnCreate method
        super.onCreate(savedInstanceState);


        //Configuring shared pref
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Boolean RememberMe = settings.getBoolean("RememberMe", false);
        System.out.println("REMEMBER ME VALUE =" + RememberMe);
        if (RememberMe) {
            Intent intent = null;
            String email = settings.getString("Email", "Default");
            System.out.println("EMAIL ====" + email);
            String password = settings.getString("Password", "Default");
            System.out.println("PASSWORD ====" + password);
            String typeClient = settings.getString("TypeClient", "Default");
            System.out.println("typeClient ====" + typeClient);
            if (typeClient.equals("Client")) {
                System.out.println();
                intent = new Intent(WelcomeActivity.this, AreaActivityClient.class);
                intent.putExtra("restaurant", false);
                intent.putExtra("client", true);
                startActivity(intent);
                System.out.println("IN THE INTENT FOR CLIENT");
            } else if (typeClient.equals("Restaurant")) {
                System.out.println();
                intent = new Intent(WelcomeActivity.this, AreaActivityRestaurant.class);
                intent.putExtra("restaurant", true);
                intent.putExtra("client", false);
                startActivity(intent);
                System.out.println("IN THE INTENT FOR RESTAURANT");
            }
        }

        setContentView(R.layout.activity_welcome);


    }

    @Override
    public void onStop() {
        super.onStop();
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //  SharedPreferences.Editor editor = settings.edit();

        // editor.clear();
        //editor.commit();
        //System.out.println("SHARED PREF WIPED OUT !!");
    }

    public void bRestaurantClicked(View view) {
        Intent loginAct = new Intent(WelcomeActivity.this, LoginActivity.class);
        loginAct.putExtra("restaurant", true);
        loginAct.putExtra("client", false);
        startActivity(loginAct);
    }

    public void bClientClicked(View view) {
        Intent loginAct = new Intent(WelcomeActivity.this, LoginActivity.class);
        loginAct.putExtra("restaurant", false);
        loginAct.putExtra("client", true);
        startActivity(loginAct);
    }
}
