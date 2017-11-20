<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Transaction extends Model
{
  protected $fillable = [
      'merchant_request_id', 'checkout_request_id', 'result_code', 'result_desc', 'amount', 'mpesa_receipt_number', 'balance', 'transaction_date', 'phone_number',
  ];
}
