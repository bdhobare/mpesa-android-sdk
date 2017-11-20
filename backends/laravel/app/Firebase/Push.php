<?php
namespace App\Firebase;
class Push {

    private $title;
    private $message;
    private $data;

    function __construct() {

    }

    public function setTitle($title) {
        $this->title = $title;
    }

    public function setMessage($message) {
        $this->message = $message;
    }

    public function setPayload($data) {
        $this->data = $data;
    }

    public function getPush() {
        $res = array();
        $res['data']['title'] = $this->title;
        $res['data']['message'] = $this->message;
        $res['data']['payload'] = $this->data;
        $res['data']['timestamp'] = date('Y-m-d G:i:s');
        return $res;
    }

}
