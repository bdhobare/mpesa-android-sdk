package com.bdhobare.mpesa.interfaces;

import com.bdhobare.mpesa.utils.Pair;

/**
 * Created by miles on 19/11/2017.
 */

public interface MpesaListener {
    public void onMpesaError(Pair<Integer, String> result);
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage);
}
