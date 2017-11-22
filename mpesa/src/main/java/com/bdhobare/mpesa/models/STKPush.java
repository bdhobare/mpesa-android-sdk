package com.bdhobare.mpesa.models;

import android.util.Base64;

import com.bdhobare.mpesa.utils.Config;
import com.bdhobare.mpesa.utils.TimeUtil;
import com.bdhobare.mpesa.utils.Utils;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by miles on 19/11/2017.
 */

public class STKPush {

    private String businessShortCode;
    private String password;
    private String timestamp;
    private String transactionType;
    private String amount;
    private String partyA;
    private String partyB;
    private String phoneNumber;
    private String callBackURL;
    private String accountReference;
    private String transactionDesc;

    private STKPush(Builder builder) {
        this.businessShortCode = builder.businessShortCode;
        this.timestamp = TimeUtil.getTimestamp();
        this.password = getPassword(builder.businessShortCode, builder.passkey, this.timestamp);
        this.transactionType = builder.transactionType;
        this.amount = String.valueOf(builder.amount);
        this.partyA = builder.partyA;
        this.partyB = builder.partyB;
        this.phoneNumber = builder.phoneNumber;
        this.accountReference = builder.accountReference;
        this.transactionDesc = builder.transactionDesc;
        this.callBackURL = builder.callBackURL;
        if (!builder.firebaseRegID.isEmpty()){
            if (builder.callBackURL.endsWith("/")){
                this.callBackURL = builder.callBackURL + builder.firebaseRegID;
            }else {
                this.callBackURL = builder.callBackURL + "/" + builder.firebaseRegID;
            }
        }

    }
    private String getPassword(String businessShortCode, String passkey, String timestamp){
        String str = businessShortCode + passkey + timestamp;
        return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }
    public static class Builder {
        private String businessShortCode;
        private String passkey;
        private String transactionType = Config.DEFAULT_TRANSACTION_TYPE;
        private int amount;
        private String partyA;
        private String partyB;
        private String phoneNumber;
        private String callBackURL = "https://mpesa.bdhobare.com/mpesa";
        private String accountReference;
        private String transactionDesc;
        private String firebaseRegID = "";
        public Builder(String businessShortCode,String passkey, int amount, String partyB, String phoneNumber){
            this.businessShortCode = businessShortCode;
            this.passkey = passkey;
            this.amount = amount;
            this.partyA = Utils.sanitizePhoneNumber(phoneNumber);
            this.partyB = partyB;
            this.phoneNumber = Utils.sanitizePhoneNumber(phoneNumber);
            this.accountReference = Utils.sanitizePhoneNumber(phoneNumber);
            this.transactionDesc = Utils.sanitizePhoneNumber(phoneNumber);
        }
        public Builder setTransactionType(String transactionType){
            this.transactionType = transactionType;
            return this;
        }
        public Builder setCallBackURL(String callBackURL){
            this.callBackURL = callBackURL;
            return this;
        }
        public Builder setDescription(String description){
            this.transactionDesc = description;
            return this;
        }
        public Builder setAccountReference(String accountReference){
            this.accountReference = accountReference;
            return this;
        }
        public Builder setFirebaseRegID(String id){
            this.firebaseRegID = id;
            return this;
        }
        public STKPush build(){
            return new STKPush(this);
        }


    }
    public String getBusinessShortCode() {
        return businessShortCode;
    }

    public String getPassword() {
        return password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAmount() {
        return amount;
    }

    public String getPartyA() {
        return partyA;
    }

    public String getPartyB() {
        return partyB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallBackURL() {
        return callBackURL;
    }

    public String getAccountReference() {
        return accountReference;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }
}
