package com.ismailmesutmujde.instakotlinapp.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentSignOutBinding

class SignOutFragment : Fragment() {

    private lateinit var bindingSOF : FragmentSignOutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bindingSOF = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_out, container, false)
        return bindingSOF.root
    }

}