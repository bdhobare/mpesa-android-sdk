package com.bdhobare.mpesa.utils;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miles on 19/11/2017.
 */

public class Utils {

    public static String addParamsToUrl(String url, HashMap<String, String> params){
        if(!url.endsWith("?"))
            url += "?";
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        return builder.build().toString();
    }
    public static  String sanitizePhoneNumber(String phone) {

        if(phone.equals("")){
            return "";
        }

        if (phone.length() < 11 & phone.startsWith("0")) {
            String p = phone.replaceFirst("^0", "254");
            return p;
        }
        if(phone.length() == 13 && phone.startsWith("+")){
            String p = phone.replaceFirst("^+", "");
            return p;
        }
        return phone;
    }
}
