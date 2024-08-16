@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.profile.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityProfileSettingsBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class ProfileSettingsActivity : AppCompatActivity() {
    private lateinit var bindingProfileSettingsActivity: ActivityProfileSettingsBinding

    private val ACTIVITY_NO = 4
    private val TAG = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingProfileSettingsActivity = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(bindingProfileSettingsActivity.root)

        setupToolbar()
        setupNavigationView()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingProfileSettingsActivity.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingProfileSettingsActivity.bottomNavigationView)
        var menu = bindingProfileSettingsActivity.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    private fun setupToolbar() {
        bindingProfileSettingsActivity.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}