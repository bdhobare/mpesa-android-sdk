package com.bdhobare.mpesa.interfaces;

import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.utils.Pair;

/**
 * Created by miles on 18/11/2017.
 */

public interface AuthListener {
    public void onAuthError(Pair<Integer, String> result);
    public void onAuthSuccess();
}
