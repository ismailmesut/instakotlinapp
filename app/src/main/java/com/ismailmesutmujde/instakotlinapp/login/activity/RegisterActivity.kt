@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.login.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityRegisterBinding
import com.ismailmesutmujde.instakotlinapp.home.activity.HomeActivity
import com.ismailmesutmujde.instakotlinapp.login.fragment.RegisterFragment
import com.ismailmesutmujde.instakotlinapp.login.fragment.EnterPhoneCodeFragment
import com.ismailmesutmujde.instakotlinapp.models.Users
import com.ismailmesutmujde.instakotlinapp.utils.EventbusDataEvents
import org.greenrobot.eventbus.EventBus

class RegisterActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private lateinit var bindingRA: ActivityRegisterBinding

    lateinit var fm : FragmentManager
    lateinit var mAuth: FirebaseAuth
    lateinit var mDataRef : DatabaseReference
    lateinit var mAuthListener : FirebaseAuth.AuthStateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingRA = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingRA.root)

        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()
        mDataRef = FirebaseDatabase.getInstance().reference

        fm = supportFragmentManager
        fm.addOnBackStackChangedListener(this)

        init()
    }

    private fun init() {

        bindingRA.tvLogIn.setOnClickListener {
            var intent = Intent(this@RegisterActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }


        // Textview'lerin Tıklanma Olayı
        bindingRA.tvEmail.setOnClickListener {
            bindingRA.viewShadowPhone.visibility = View.INVISIBLE
            bindingRA.viewShadowEmail.visibility = View.VISIBLE
            bindingRA.etLogInMethod.setText("")
            bindingRA.etLogInMethod.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            bindingRA.etLogInMethod.setHint("E-MAIL")

            bindingRA.btnNext.isEnabled = false
            bindingRA.btnNext.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.faint_blue))
            bindingRA.btnNext.setBackgroundResource(R.drawable.register_button)

        }

        bindingRA.tvPhone.setOnClickListener {
            bindingRA.viewShadowPhone.visibility = View.VISIBLE
            bindingRA.viewShadowEmail.visibility = View.INVISIBLE
            bindingRA.etLogInMethod.setText("")
            bindingRA.etLogInMethod.inputType = InputType.TYPE_CLASS_NUMBER
            bindingRA.etLogInMethod.setHint("PHONE")

            bindingRA.btnNext.isEnabled = false
            bindingRA.btnNext.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.faint_blue))
            bindingRA.btnNext.setBackgroundResource(R.drawable.register_button)
        }

        // EditText'e Girilen Karakterlerin Kontrolü
        bindingRA.etLogInMethod.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length >= 10) {
                    bindingRA.btnNext.isEnabled = true
                    bindingRA.btnNext.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.white))
                    bindingRA.btnNext.setBackgroundResource(R.drawable.register_button_active)
                } else {
                    bindingRA.btnNext.isEnabled = false
                    bindingRA.btnNext.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.faint_blue))
                    bindingRA.btnNext.setBackgroundResource(R.drawable.register_button)
                }
            }
        })

        // Button'a Tıklanma Olayı
        bindingRA.btnNext.setOnClickListener {

            if(bindingRA.etLogInMethod.hint.toString().equals("PHONE")){
                //Toast.makeText(this,"Phone",Toast.LENGTH_SHORT).show()
                if(isValidPhone(bindingRA.etLogInMethod.text.toString())) {

                    var isPhoneNoInUse = false

                    mDataRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot!!.getValue() != null) {
                                for (user in snapshot!!.children){
                                    var readUser = user.getValue(Users::class.java)
                                    if (readUser!!.phone_number!!.equals(bindingRA.etLogInMethod.text.toString())){
                                        Toast.makeText(this@RegisterActivity,"Phone Number in use",Toast.LENGTH_SHORT).show()
                                        isPhoneNoInUse = true
                                        break
                                    }
                                }
                                if (isPhoneNoInUse == false) {
                                    bindingRA.loginRoot.visibility = View.GONE
                                    bindingRA.loginContainer.visibility = View.VISIBLE
                                    var transaction = supportFragmentManager.beginTransaction()
                                    transaction.replace(R.id.loginContainer, EnterPhoneCodeFragment())
                                    transaction.addToBackStack("EnterPhoneCodeFragment added.")
                                    transaction.commit()
                                    EventBus.getDefault().postSticky(EventbusDataEvents
                                        .SendRegisterInformation(bindingRA.etLogInMethod.text.toString(),
                                            null,
                                            null,
                                            null,
                                            false))
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })

                } else {
                    Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
            } else{
                //Toast.makeText(this,"E-mail",Toast.LENGTH_SHORT).show()
                if (isValidEmail(bindingRA.etLogInMethod.text.toString())) {

                    var isEmailInUse = false

                    mDataRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot!!.getValue() != null) {
                                for (user in snapshot!!.children){
                                    var readUser = user.getValue(Users::class.java)
                                    if (readUser!!.email!!.equals(bindingRA.etLogInMethod.text.toString())){
                                        Toast.makeText(this@RegisterActivity,"Email in use",Toast.LENGTH_SHORT).show()
                                        isEmailInUse = true
                                        break
                                    }
                                }
                                if (isEmailInUse == false) {
                                    bindingRA.loginRoot.visibility = View.GONE
                                    bindingRA.loginContainer.visibility = View.VISIBLE
                                    var transaction = supportFragmentManager.beginTransaction()
                                    transaction.replace(R.id.loginContainer, RegisterFragment())
                                    transaction.addToBackStack("RegisterFragment added.")
                                    transaction.commit()
                                    EventBus.getDefault().post(EventbusDataEvents
                                        .SendRegisterInformation(null,
                                            bindingRA.etLogInMethod.text.toString(),
                                            null,
                                            null,
                                            true))
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })


                } else {
                    Toast.makeText(this, "Please enter a valid e-mail address", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onBackStackChanged() {
        val itemCount = fm.backStackEntryCount
        if(itemCount == 0) {
            bindingRA.loginRoot.visibility = View.VISIBLE
        }
    }

    private fun isValidEmail(toCheckEmail : String) : Boolean {
        if(toCheckEmail == null) {
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(toCheckEmail).matches()
    }

    private fun isValidPhone(toCheckPhone : String) : Boolean {
        if(toCheckPhone == null || toCheckPhone.length > 14) {
            return false
        }
        return android.util.Patterns.PHONE.matcher(toCheckPhone).matches()
    }

    private fun setupAuthListener() {

        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    var intent = Intent(this@RegisterActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
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