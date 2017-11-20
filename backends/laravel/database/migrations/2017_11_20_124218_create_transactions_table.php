<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTransactionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('transactions', function (Blueprint $table) {
            $table->increments('id');
            $table->string('merchant_request_id');
            $table->string('checkout_request_id');
            $table->integer('result_code');
            $table->string('result_desc');
            $table->integer('amount')->nullable();
            $table->string('mpesa_receipt_number')->nullable();
            $table->integer('balance')->nullable()->default(0);
            $table->timestamp('transaction_date')->nullable();
            $table->string('phone_number')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('transactions');
    }
}
