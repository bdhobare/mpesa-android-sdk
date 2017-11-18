package com.bdhobare.mpesa.utils;

import android.util.Base64;



/**
 * Created by miles on 18/11/2017.
 *
 */

public class Preferences {
    static Preferences instance;
    private String access_token = "";
    private String key = "", secret = "";

    private Preferences() {}
    public static Preferences getInstance(){
        if(instance == null){
            instance = new Preferences();
        }
        return instance;
    }
    public String getAccessToken() {
        return access_token;
    }
    public String getAuthorization(){
        String keys = key + ":" + secret;
        return Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP);
    }

    public void setAccessToken(String accessToken) {
       this.access_token = accessToken;
    }

    public void setKey(String key){
        this.key = key;
    }
    public void setSecret(String secret){
        this.secret = secret;
    }
}
