package com.bdhobare.mpesa.utils;

/**
 * Created by miles on 18/11/2017.
 */

public class Config {
    public static final String BASE_URL = "https://sandbox.safaricom.co.ke/";
    public static String PRODUCTION_BASE_URL = "https://api.safaricom.co.ke/";

    public static final String ACCESS_TOKEN_URL = "oauth/v1/generate?grant_type=client_credentials";
    public static final String PROCESS_REQUEST_URL ="mpesa/stkpush/v1/processrequest";

    public static final String ERROR = "An error occurred while processing the request.Please check your internet connection and try again.";
    public static final String DEFAULT_TRANSACTION_TYPE = "CustomerPayBillOnline";
}
