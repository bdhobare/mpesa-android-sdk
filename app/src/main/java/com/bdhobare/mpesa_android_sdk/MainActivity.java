package com.bdhobare.mpesa_android_sdk;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.models.STKPush.Builder;
import com.bdhobare.mpesa.utils.Pair;

public class MainActivity extends AppCompatActivity implements AuthListener, MpesaListener {
    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";

    Button pay;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pay = (Button)findViewById(R.id.pay);
        Mpesa.with(this, "tORzmtpZVFSjtAcjpUc2Xb7CmM2GAMsq", "y8QpZxpGzjGHUCCg");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing");
        dialog.setIndeterminate(true);

        pay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });
    }

    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
    }

    @Override
    public void onAuthSuccess() {
        Log.e("Success", "Success");
        //TODO make payment
        pay.setEnabled(true);
    }
    private void pay(){
        dialog.show();
        STKPush.Builder builder = new Builder(BUSINESS_SHORT_CODE, PASSKEY, 1,BUSINESS_SHORT_CODE, "0700864995");
        builder.setDescription("Test Description");
        builder.setFirebaseRegID("sdgshgsfrhafhafsh");
        STKPush push = builder.build();

        Mpesa.getInstance().lipa(this, push);

    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        Log.e("Result", result.message);
    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        dialog.hide();
        Toast.makeText(this, CustomerMessage, Toast.LENGTH_SHORT).show();
    }

}
