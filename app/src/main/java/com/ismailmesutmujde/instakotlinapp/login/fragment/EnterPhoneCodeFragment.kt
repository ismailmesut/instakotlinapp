package com.ismailmesutmujde.instakotlinapp.login.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentEnterPhoneCodeBinding
import com.ismailmesutmujde.instakotlinapp.login.activity.RegisterActivity
import com.ismailmesutmujde.instakotlinapp.utils.EventbusDataEvents
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit


class EnterPhoneCodeFragment : Fragment() {

    private lateinit var bindingEPCF : FragmentEnterPhoneCodeBinding

    var incomingPhoneNo = ""

    lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth : FirebaseAuth
    var verificationID = ""
    var incomingCode = ""
    lateinit var progressBar : ProgressBar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        bindingEPCF = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_phone_code, container,false)

        bindingEPCF.tvUserPhoneNo.setText(incomingPhoneNo)

        progressBar = bindingEPCF.pbCheckPhoneNo

        setupCallback()

        bindingEPCF.btnPhoneCodeNext.setOnClickListener {
            if(incomingCode.equals(bindingEPCF.etVerificationCode.text.toString())){
                //Toast.makeText(activity, "You can continue", Toast.LENGTH_SHORT).show()
                EventBus.getDefault().postSticky(EventbusDataEvents.SendRegisterInformation(
                    incomingPhoneNo,
                    null,
                    verificationID,
                    incomingCode,
                    false))
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer, RegisterFragment())
                transaction.addToBackStack("RegisterFragment added.")
                transaction.commit()
            } else {
                Toast.makeText(activity, "Wrong code", Toast.LENGTH_SHORT).show()
            }
        }



        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(incomingPhoneNo) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(Activity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        return bindingEPCF.root
    }

    private fun setupCallback() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                if (!credential.smsCode.isNullOrEmpty()){
                    incomingCode = credential.smsCode!!
                    progressBar.visibility = View.INVISIBLE
                    Log.e("Error", "on verification completed sms gelmiş : " + incomingCode)
                } else {
                    Log.e("Error", "on verification completed sms gelmeyecek")
                }


            }

            override fun onVerificationFailed(e: FirebaseException) {
                progressBar.visibility = View.INVISIBLE
                Log.e("ERROR", "An error occurred" + e.message)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                verificationID = verificationId
                progressBar.visibility = View.VISIBLE
                Log.e("INFORMATION", "onCodeSent çalıştı")
            }
        }
    }


    @Subscribe(sticky = true)
    internal fun onPhoneNoEvent(RegisterInformation : EventbusDataEvents.SendRegisterInformation) {
        incomingPhoneNo = RegisterInformation.phoneNo!!
        Log.e("ismail","Gelen tel no : " + incomingPhoneNo)
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