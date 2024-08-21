@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.profile.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityProfileSettingsBinding
import com.ismailmesutmujde.instakotlinapp.profile.fragment.ProfileEditFragment
import com.ismailmesutmujde.instakotlinapp.profile.fragment.SignOutFragment
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class ProfileSettingsActivity : AppCompatActivity() {
    private lateinit var bindingPSA: ActivityProfileSettingsBinding

    private val ACTIVITY_NO = 4
    private val TAG = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingPSA = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(bindingPSA.root)

        setupToolbar()
        setupNavigationView()
        fragmentNavigations()
    }



    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingPSA.bnvProfileSettings)
        BottomNavigationViewHelper.setupNavigation(this, bindingPSA.bnvProfileSettings)
        var menu = bindingPSA.bnvProfileSettings.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    private fun setupToolbar() {
        bindingPSA.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fragmentNavigations() {
        bindingPSA.tvEditProfile.setOnClickListener {
            bindingPSA.profileSettingsRoot.visibility = View.GONE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileSettingsContainer, ProfileEditFragment())
            transaction.addToBackStack("profileEditFragment added.")
            transaction.commit()
        }

        bindingPSA.tvSignOut.setOnClickListener {
            var dialog = SignOutFragment()
            dialog.show(supportFragmentManager,"SignOutDialogShow")
        }
    }

    override fun onBackPressed() {
        bindingPSA.profileSettingsRoot.visibility = View.VISIBLE
        super.onBackPressed()
    }
}