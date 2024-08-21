package com.ismailmesutmujde.instakotlinapp.utils

import com.ismailmesutmujde.instakotlinapp.models.Users

class EventbusDataEvents{

    internal class SendRegisterInformation(var phoneNo : String?, var email : String?, var verificationID : String?, var code : String?, var registerEmail : Boolean)

    internal class SendUserInformation(var user : Users?)

}
