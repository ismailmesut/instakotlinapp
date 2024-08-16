package com.ismailmesutmujde.instakotlinapp.login.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLA : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingLA = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLA.root)

    }
}