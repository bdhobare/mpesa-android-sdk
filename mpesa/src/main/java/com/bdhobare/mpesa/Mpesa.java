package com.bdhobare.mpesa;

import android.content.Context;
import android.os.AsyncTask;

import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.network.NetworkHandler;
import com.bdhobare.mpesa.utils.Config;
import com.bdhobare.mpesa.utils.Pair;
import com.bdhobare.mpesa.utils.Preferences;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by miles on 18/11/2017.
 */

public class Mpesa {
    private static AuthListener listener;
    private static Mpesa instance;
    private Mpesa(){}

    public static void with (Context context, String key, String secret){
        if (! (context instanceof AuthListener)){
            throw new RuntimeException("Context must implement AuthListener");
        }
        if (instance == null){
            instance = new Mpesa();
            Preferences.getInstance().setKey(key);
            Preferences.getInstance().setSecret(secret);
            listener = (AuthListener)context;

            //TODO initialize mpesa by getting the access token
            new GetAccessToken().execute(Config.SANDBOX_TOKEN_URL);
        }

    }

    public static Mpesa getInstance(){
        if (instance == null){
            throw new RuntimeException("Mpesa must be initialized with key and secret");
        }
        return instance;
    }

    static class GetAccessToken extends AsyncTask<String, Void, Pair<Integer, String> > {

        @Override
        protected Pair<Integer, String> doInBackground(String... strings) {
            return NetworkHandler.doGet(strings[0]);
        }

        protected void onPostExecute(Pair<Integer, String> result){
            if (result == null){
                Mpesa.getInstance().listener.onError(new Pair<>(418, Config.ERROR)); //User is a teapot :(
                return;
            }
            if (result.code == 400){
                Mpesa.getInstance().listener.onError(new Pair<>(result.code, "Invalid credentials"));
                return;
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(result.message).getAsJsonObject();
            if (result.code/100 != 2){
                //Error occurred
                String message = jo.get("errorMessage").getAsString();
                Mpesa.getInstance().listener.onError(new Pair<>(result.code, message));
                return;
            }
            String access_token = jo.get("access_token").getAsString();
            Preferences.getInstance().setAccessToken(access_token);
            Mpesa.getInstance().listener.onSuccess();
            return;
        }
    }

}
