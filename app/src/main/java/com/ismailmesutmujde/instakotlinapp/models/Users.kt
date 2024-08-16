package com.ismailmesutmujde.instakotlinapp.models

class Users {
    var email: String? = null
    var password: String? = null
    var user_name: String? = null
    var name_surname: String? = null
    var phone_number: String? = null
    var email_phone_number: String? = null
    var user_id: String? = null

    constructor() {}

    constructor(email: String?, password: String?, user_id: String?, user_name: String?, name_surname: String?, phone_number: String?, email_phone_number: String?) {
        this.email = email
        this.password = password
        this.user_id = user_id
        this.user_name = user_name
        this.name_surname = name_surname
        this.phone_number = phone_number
        this.email_phone_number = email_phone_number
    }

}

