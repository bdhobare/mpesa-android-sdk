<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Transaction;
use Carbon\Carbon;
use App\Firebase\Firebase;
use App\Firebase\Push;

class MPESAController extends Controller
{
  public function index(Request $request){
    $firebaseId = $request->route('firebaseId');

    $result = json_decode($request->getContent());
    $Body = $result->Body;
    $stkCallback = $Body->stkCallback;

    $MerchantRequestID = $stkCallback->MerchantRequestID;
    $CheckoutRequestID = $stkCallback->CheckoutRequestID;
    $ResultCode = $stkCallback->ResultCode;
    $ResultDesc = $stkCallback->ResultDesc;

    $transaction = new Transaction(['merchant_request_id' => $MerchantRequestID, 'checkout_request_id' => $CheckoutRequestID,
                  'result_code' => $ResultCode, 'result_desc' => $ResultDesc]);
    if (!property_exists($stkCallback, "CallbackMetadata")) {
      $transaction->save();
      return $ResultDesc;
    }
    $CallbackMetadata = $stkCallback->CallbackMetadata;
    $Item = $CallbackMetadata->Item;

    //Wtf Safaricom! This should be an object not array :(
    foreach ($Item as $key => $value) {
      $Name = $value->Name;
      if (property_exists($value, "Value")) {
        $Value = $value->Value;

        if (strcasecmp($Name, "Amount") == 0) {
            $transaction->amount = $Value;
        }

        if (strcasecmp($Name, "MpesaReceiptNumber") == 0) {
            $transaction->mpesa_receipt_number = $Value;
        }

        if (strcasecmp($Name, "Balance") == 0) {
            $transaction->balance = $Value;
        }

        if (strcasecmp($Name, "TransactionDate") == 0) {
          $year = substr($Value, 0, 4);
          $month = substr($Value, 4, 2);
          $day = substr($Value, 6, 2);
          $hour = substr($Value, 8, 2);
          $minute = substr($Value, 10, 2);
          $second = substr($Value, 12, 2);
          $transaction->transaction_date = Carbon::create($year, $month, $day, $hour, $minute, $second, 'Africa/Nairobi');
        }

        if (strcasecmp($Name, "PhoneNumber") == 0) {
            $transaction->phone_number = (string)$Value;
        }
      }
    }
    $transaction->save();
    if($firebaseId != null){
      //send firebase push notification
      return $this->sendFirebaseNotification($transaction, $firebaseId);
    }

    return "Received";
  }
  private function sendFirebaseNotification($transaction, $firebaseId){
    $firebase = new Firebase();
    $push = new Push();
    $push->setTitle('Transaction successful.');
    $push->setMessage('Your transaction of KES.'.$transaction->amount.' was successful. MPESA Receipt Number is '.$transaction->mpesa_receipt_number);
    $payload = array();
    $payload['amount'] = $transaction->amount;
    $payload['MpesaReceiptNumber'] = $transaction->mpesa_receipt_number;
    //TODO add extra attributes of interest
    $push->setPayload($payload);

    $json = '';
    $response = '';
    $json = $push->getPush();
    $response = $firebase->send($firebaseId, $json);
    return $response;
  }
}
