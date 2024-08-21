package com.ismailmesutmujde.instakotlinapp.profile.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentLoadingBinding


class LoadingFragment : DialogFragment() {

    private lateinit var bindingLF : FragmentLoadingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bindingLF = DataBindingUtil.inflate(inflater, R.layout.fragment_loading, container, false)

        bindingLF.progressBar3.indeterminateDrawable.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.black), PorterDuff.Mode.SRC_IN)
        return bindingLF.root
    }

}