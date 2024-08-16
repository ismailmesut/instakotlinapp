package com.ismailmesutmujde.instakotlinapp.utils

class EventbusDataEvents{

    internal class SendRegisterInformation(var phoneNo : String?, var email : String?, var verificationID : String?, var code : String?, var registerEmail : Boolean)

}
