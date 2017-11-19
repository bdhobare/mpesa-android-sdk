package com.bdhobare.mpesa.network;

/**
 * Created by miles on 23/10/2016.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bdhobare.mpesa.utils.Pair;
import com.bdhobare.mpesa.utils.Preferences;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NetworkHandler {
    public  boolean isConnected(Context activity){
        ConnectivityManager connectivityManager=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    public static Pair<Integer, String> doGet (String path, HashMap<String, String> headers){
        HttpURLConnection connection=null;
        try {
            URL url=new URL(path);
            connection =(HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            for (Map.Entry<String, String> entry: headers.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                connection.setRequestProperty(key, value);
            }
            InputStream is ;
            if(connection.getResponseCode()/100 == 2){
                is=connection.getInputStream();
            }else{
                is=connection.getErrorStream();
            }
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer buffer=new StringBuffer();
            while ((line=bufferedReader.readLine())!= null){
                buffer.append(line);
                buffer.append('\r');
            }
            bufferedReader.close();
            return new Pair<>(connection.getResponseCode(), buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public static Pair<Integer, String> doPost(String targetURL, String params, HashMap<String, String> headers) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            for (Map.Entry<String, String> entry: headers.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                connection.setRequestProperty(key, value);
            }
            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes (params);
            wr.flush ();
            wr.close ();
            //Get Response
            InputStream is ;
            if(connection.getResponseCode()/100 ==2){
                is=connection.getInputStream();
            }else{
                is=connection.getErrorStream();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return new Pair<>(connection.getResponseCode(),response.toString());

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
