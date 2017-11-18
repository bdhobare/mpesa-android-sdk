package com.bdhobare.mpesa_android_sdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.utils.Pair;

public class MainActivity extends AppCompatActivity implements AuthListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mpesa.with(this, "ZYAS1e58VNdH03oD7CKUPBjKk1I8mbsr", "GcocPU0l7zGC3ru6");
    }

    @Override
    public void onError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
        Mpesa.getInstance();
    }

    @Override
    public void onSuccess() {
        Log.e("Success", "Success");
        Mpesa.getInstance();
    }
}
