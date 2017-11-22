package com.bdhobare.mpesa_android_sdk;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by miles on 20/11/2017.
 */

public class MPESAInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("InstanceID", refreshedToken);
        editor.commit();

    }

}