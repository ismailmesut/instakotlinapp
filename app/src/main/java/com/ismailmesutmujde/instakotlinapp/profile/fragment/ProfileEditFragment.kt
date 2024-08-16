@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentProfileEditBinding
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader
import de.hdodenhof.circleimageview.CircleImageView

class ProfileEditFragment : Fragment() {

    private lateinit var bindingPEF : FragmentProfileEditBinding

    lateinit var circleProfileImageFragment : CircleImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindingPEF =  DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, container, false)

        circleProfileImageFragment = bindingPEF.circleProfileImage

        setupProfilePicture()

        bindingPEF.imgClose.setOnClickListener {
            activity?.onBackPressed()
        }

        return bindingPEF.root
    }


    private fun setupProfilePicture() {
        //https://orig00.deviantart.net/67cd/f/2012/309/8/c/android_icon_by_gabrydesign-dm47he9.png

        var imgURL = "orig00.deviantart.net/67cd/f/2012/309/8/c/android_icon_by_gabrydesign-dm47he9.png"
        UniversalImageLoader.setImage(imgURL, circleProfileImageFragment, bindingPEF.progressBar2,"https://")
    }

}