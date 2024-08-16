@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.profile.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityProfileBinding
import com.ismailmesutmujde.instakotlinapp.profile.fragment.ProfileEditFragment
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader

class ProfileActivity : AppCompatActivity() {

    private lateinit var bindingPA: ActivityProfileBinding

    private val ACTIVITY_NO = 4
    private val TAG = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingPA = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(bindingPA.root)

        setupToolbar()
        setupNavigationView()
        setupProfilePhoto()
    }

    //https://orig00.deviantart.net/67cd/f/2012/309/8/c/android_icon_by_gabrydesign-dm47he9.png


    private fun setupProfilePhoto() {
        // "http://www.ids.com.tr/IDS-Media/image/Android-logo.png"
        //"https://www.wallpaperup.com/uploads/wallpapers/2016/06/24/991808/9ab236cccae5470451c20329ca43ec6b-1400.jpg"
        var imgURL = "www.wallpaperup.com/uploads/wallpapers/2016/06/24/991808/9ab236cccae5470451c20329ca43ec6b-1400.jpg"
        UniversalImageLoader.setImage(imgURL, bindingPA.circleProfileImage, bindingPA.progressBar, "https://")
    }

    private fun setupToolbar() {
        bindingPA.imgProfileSettings.setOnClickListener {
            var intent = Intent(this, ProfileSettingsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        bindingPA.tvEditProfile.setOnClickListener {
            bindingPA.profileRoot.visibility = View.GONE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileContainer, ProfileEditFragment())
            transaction.addToBackStack("profileEditFragment added.")
            transaction.commit()
        }
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingPA.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingPA.bottomNavigationView)
        var menu = bindingPA.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    override fun onBackPressed() {
        bindingPA.profileRoot.visibility = View.VISIBLE
        super.onBackPressed()
    }
}