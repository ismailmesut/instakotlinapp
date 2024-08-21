package com.ismailmesutmujde.instakotlinapp.login.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityLoginBinding
import com.ismailmesutmujde.instakotlinapp.home.activity.HomeActivity
import com.ismailmesutmujde.instakotlinapp.models.Users

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLA : ActivityLoginBinding

    lateinit var mDataRef : DatabaseReference
    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingLA = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLA.root)

        setupAuthListener()

        mAuth = FirebaseAuth.getInstance()
        mDataRef = FirebaseDatabase.getInstance().reference

        init()
    }



    fun init() {
        bindingLA.etEmailPhoneOrUsername.addTextChangedListener(watcher)
        bindingLA.etLoginPassword.addTextChangedListener(watcher)

        bindingLA.btnEnterLogin.setOnClickListener {
            controlLoginUser(bindingLA.etEmailPhoneOrUsername.text.toString(), bindingLA.etLoginPassword.text.toString())
        }

        bindingLA.tvLogIn.setOnClickListener {
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

    }

    // Oturum Açacak Kullanıcıyı Denetle Fonksiyonu
    private fun controlLoginUser(emailPhoneOrUsername: String, loginPassword: String) {

        var foundUser = false

        mDataRef.child("users").orderByChild("email").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot!!.children) {
                    var readUser = ds.getValue(Users::class.java)

                    if(readUser!!.email!!.toString().equals(emailPhoneOrUsername)){
                        signIn(readUser, loginPassword, false)
                        foundUser = true
                        break
                    } else if (readUser!!.user_name!!.toString().equals(emailPhoneOrUsername)) {
                        signIn(readUser, loginPassword, false)
                        foundUser = true
                        break
                    } else if (readUser!!.phone_number!!.toString().equals(emailPhoneOrUsername)) {
                        signIn(readUser, loginPassword, true)
                        foundUser = true
                        break
                    }
                }
                if (foundUser == false){
                    Toast.makeText(this@LoginActivity, "User not found.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    // Oturum Aç Fonksiyonu
    private fun signIn(readUser : Users, loginPassword: String, loginWithPhoneNumber : Boolean) {
        var emailToLogIn = ""

        if(loginWithPhoneNumber == true) {
            emailToLogIn = readUser.email_phone_number.toString()
        } else {
            emailToLogIn = readUser.email.toString()
        }

        mAuth.signInWithEmailAndPassword(emailToLogIn, loginPassword)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0!!.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Logged in : "+mAuth.currentUser!!.uid, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@LoginActivity, "Username/Password incorrect. ", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    var watcher:TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (bindingLA.etEmailPhoneOrUsername.text.toString().length >= 6 && bindingLA.etLoginPassword.text.toString().length >= 6) {
                bindingLA.btnEnterLogin.isEnabled = true
                bindingLA.btnEnterLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                bindingLA.btnEnterLogin.setBackgroundResource(R.drawable.register_button_active)
            } else {
                bindingLA.btnEnterLogin.isEnabled = false
                bindingLA.btnEnterLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.faint_blue))
                bindingLA.btnEnterLogin.setBackgroundResource(R.drawable.register_button)
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }



    private fun setupAuthListener() {

        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser
                if (user != null) {

                    var intent = Intent(this@LoginActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                } else {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }

    }

}