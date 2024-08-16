package com.ismailmesutmujde.instakotlinapp.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentMessagesBinding

class MessagesFragment : Fragment() {

    private lateinit var bindingMF : FragmentMessagesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingMF = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false)
        return bindingMF.root
    }
}