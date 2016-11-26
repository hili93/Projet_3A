package com.example.hicham.myapplication.Firebase;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.example.hicham.myapplication.WelcomeActivity.PREFS_NAME;

/**
 * Created by hicham on 13/11/2016.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        registerTokenSharedPref(token);
    }

    private void registerTokenSharedPref(String token) {
        //Storing token in Shared Preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Token", token);
        editor.commit();
        System.out.println("TOKEN STORED IN SHARED PREF");

    }

}
