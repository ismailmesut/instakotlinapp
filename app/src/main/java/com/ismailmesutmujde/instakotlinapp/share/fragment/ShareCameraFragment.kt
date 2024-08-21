package com.ismailmesutmujde.instakotlinapp.share.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentShareCameraBinding

class ShareCameraFragment : Fragment() {

    private lateinit var bindingSCF : FragmentShareCameraBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        // Inflate the layout for this fragment
        bindingSCF = DataBindingUtil.inflate(inflater, R.layout.fragment_share_camera, container, false)
        return bindingSCF.root
    }

}