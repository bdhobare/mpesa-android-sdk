package com.bdhobare.mpesa.utils;

/**
 * Created by miles on 18/11/2017.
 * Helper class to return a response code and message from API call
 *
 */

public class Pair<X, Y> {
    public X code;
    public Y message;
    public Pair(X x, Y y){
        this.code = x;
        this.message = y;
    }
}
