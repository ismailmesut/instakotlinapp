package com.ismailmesutmujde.instakotlinapp.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var bindingHF : FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingHF = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bindingHF.root
    }
}