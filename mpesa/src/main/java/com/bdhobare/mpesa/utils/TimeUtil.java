package com.bdhobare.mpesa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by miles on 19/11/2017.
 */

public class TimeUtil {
    public static String getTimestamp(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        return timeStamp;
    }
}
