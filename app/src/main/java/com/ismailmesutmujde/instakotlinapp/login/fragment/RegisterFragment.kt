package com.ismailmesutmujde.instakotlinapp.login.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
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
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentRegisterBinding
import com.ismailmesutmujde.instakotlinapp.login.activity.LoginActivity
import com.ismailmesutmujde.instakotlinapp.models.UserDetails
import com.ismailmesutmujde.instakotlinapp.models.Users
import com.ismailmesutmujde.instakotlinapp.utils.EventbusDataEvents
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class RegisterFragment : Fragment() {

    private lateinit var bindingRF : FragmentRegisterBinding

    var phoneNo = ""
    var verificationID = ""
    var incomingCode = ""
    var incomingEmail = ""
    var registrationProcessViaEmail = true
    lateinit var mAuth : FirebaseAuth
    lateinit var mDataRef : DatabaseReference
    lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        bindingRF = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        progressBar = bindingRF.pbRegisterUser

        mAuth = FirebaseAuth.getInstance()

        bindingRF.tvLogIn.setOnClickListener {
            var intent = Intent(activity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }


        mDataRef = FirebaseDatabase.getInstance().reference

        bindingRF.etNameSurname.addTextChangedListener(watcher)
        bindingRF.etPassword.addTextChangedListener(watcher)
        bindingRF.etUsername.addTextChangedListener(watcher)

        bindingRF.btnLogin.setOnClickListener {

            var isUsernameInUse = false

            mDataRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot!!.getValue() != null) {
                        for (user in snapshot.children) {
                            var readUser = user.getValue(Users::class.java)
                            if (readUser!!.user_name!!.equals(bindingRF.etUsername.text.toString())){
                                Toast.makeText(activity,"Username in use",Toast.LENGTH_SHORT).show()
                                isUsernameInUse = true
                                break
                            }
                        }

                        if (isUsernameInUse == false) {

                            progressBar.visibility = View.VISIBLE

                            // kullanıcı email ile kayıt olmak istiyor
                            if(registrationProcessViaEmail){
                                var password = bindingRF.etPassword.text.toString()
                                var userName = bindingRF.etUsername.text.toString()
                                var nameSurname = bindingRF.etNameSurname.text.toString()

                                mAuth.createUserWithEmailAndPassword(incomingEmail, password)
                                    .addOnCompleteListener {object: OnCompleteListener<AuthResult>{
                                        override fun onComplete(p0: Task<AuthResult>) {
                                            if (p0!!.isSuccessful) {
                                                Toast.makeText(activity, "Login Successful with E-mail. Uid: "+mAuth.currentUser!!.uid, Toast.LENGTH_SHORT).show()
                                                // oturum açan kullanıcının verilerini database kaydedelim.
                                                var userID = mAuth.currentUser!!.uid.toString()
                                                var registeredUserDetails = UserDetails("0","0","0","","","")
                                                var registeredUser = Users(incomingEmail, password, userID, userName,nameSurname,"","", registeredUserDetails)
                                                mDataRef.child("users").child(userID).setValue(registeredUser).addOnCompleteListener(
                                                    object:OnCompleteListener<Void> {
                                                        override fun onComplete(p0: Task<Void>) {
                                                            if(p0!!.isSuccessful){
                                                                Toast.makeText(activity, "User registered", Toast.LENGTH_SHORT).show()
                                                                progressBar.visibility = View.INVISIBLE
                                                            }else {
                                                                progressBar.visibility = View.INVISIBLE
                                                                mAuth.currentUser!!.delete()
                                                                    .addOnCompleteListener(
                                                                        object : OnCompleteListener<Void>{
                                                                            override fun onComplete(p0: Task<Void>) {
                                                                                if (p0!!.isSuccessful){
                                                                                    Toast.makeText(activity, "User not registered, Try again!", Toast.LENGTH_SHORT).show()
                                                                                }
                                                                            }
                                                                        }
                                                                    )
                                                            }
                                                        }
                                                    }
                                                )
                                            } else {
                                                progressBar.visibility = View.INVISIBLE
                                                Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    }
                            }
                            // kullanıcı telefon no ile kayıt olmak istiyor
                            else {
                                var password = bindingRF.etPassword.text.toString()
                                var fakeEmail = phoneNo + "@aaaa.com" // +905114113121@aaaa.com
                                var userName = bindingRF.etUsername.text.toString()
                                var nameSurname = bindingRF.etNameSurname.text.toString()
                                mAuth.createUserWithEmailAndPassword(fakeEmail, password)
                                    .addOnCompleteListener {object: OnCompleteListener<AuthResult> {
                                        override fun onComplete(p0: Task<AuthResult>) {
                                            if (p0!!.isSuccessful) {
                                                Toast.makeText(activity, "Login Successful with Phone No. Uid: "+mAuth.currentUser!!.uid, Toast.LENGTH_SHORT).show()
                                                // oturum açan kullanıcının verilerini database kaydedelim.
                                                var userID = mAuth.currentUser!!.uid.toString()
                                                var registeredUserDetails = UserDetails("0","0","0","","","")
                                                var registeredUser = Users("", password, userID, userName,nameSurname, phoneNo, fakeEmail,registeredUserDetails)
                                                mDataRef.child("users").child(userID).setValue(registeredUser).addOnCompleteListener(
                                                    object:OnCompleteListener<Void> {
                                                        override fun onComplete(p0: Task<Void>) {
                                                            if(p0!!.isSuccessful){
                                                                Toast.makeText(activity, "User registered", Toast.LENGTH_SHORT).show()
                                                                progressBar.visibility = View.INVISIBLE
                                                            }else {
                                                                progressBar.visibility = View.INVISIBLE
                                                                mAuth.currentUser!!.delete()
                                                                    .addOnCompleteListener(
                                                                        object : OnCompleteListener<Void>{
                                                                            override fun onComplete(p0: Task<Void>) {
                                                                                if (p0!!.isSuccessful){
                                                                                    Toast.makeText(activity, "User not registered, Try again!", Toast.LENGTH_SHORT).show()
                                                                                }
                                                                            }
                                                                        }
                                                                    )
                                                            }
                                                        }
                                                    }
                                                )
                                            } else {
                                                progressBar.visibility = View.INVISIBLE
                                                Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    }
                            }

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }
        return bindingRF.root
    }

    var watcher : TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length > 5) {
                if(bindingRF.etNameSurname.text.toString().length > 5 &&
                    bindingRF.etPassword.text.toString().length > 5 &&
                    bindingRF.etUsername.text.toString().length > 5) {

                    bindingRF.btnLogin.isEnabled = true
                    bindingRF.btnLogin.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
                    bindingRF.btnLogin.setBackgroundResource(R.drawable.register_button)
                } else {
                    bindingRF.btnLogin.isEnabled = false
                    bindingRF.btnLogin.setTextColor(ContextCompat.getColor(activity!!, R.color.faint_blue))
                    bindingRF.btnLogin.setBackgroundResource(R.drawable.register_button)
                }
            } else {
                bindingRF.btnLogin.isEnabled = false
                bindingRF.btnLogin.setTextColor(ContextCompat.getColor(activity!!, R.color.faint_blue))
                bindingRF.btnLogin.setBackgroundResource(R.drawable.register_button)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            TODO("Not yet implemented")
        }

    }

    @Subscribe(sticky = true)
    internal fun onRegisterInformationEvent(RegisterInformation : EventbusDataEvents.SendRegisterInformation) {

        if (RegisterInformation.registerEmail == true) {
            registrationProcessViaEmail = true
            incomingEmail = RegisterInformation.email!!
            Toast.makeText(activity, "Incoming Email : " + incomingEmail, Toast.LENGTH_SHORT).show()
            Log.e("ismail","Gelen e-mail adresi : " + incomingEmail)
        } else {
            registrationProcessViaEmail = false
            phoneNo =  RegisterInformation.phoneNo!!
            verificationID = RegisterInformation.verificationID!!
            incomingCode = RegisterInformation.code!!

            Toast.makeText(activity, "Incoming Code : " + incomingCode + " VerificationID : " + verificationID, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

}