package com.ismailmesutmujde.instakotlinapp.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

    private lateinit var bindingCF : FragmentCameraBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingCF = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        return bindingCF.root
    }
}