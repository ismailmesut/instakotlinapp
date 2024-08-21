package com.ismailmesutmujde.instakotlinapp.share.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentShareVideoBinding

class ShareVideoFragment : Fragment() {

    private lateinit var bindingSVF : FragmentShareVideoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        // Inflate the layout for this fragment
        bindingSVF = DataBindingUtil.inflate(inflater,R.layout.fragment_share_video, container, false)
        return bindingSVF.root
    }

}