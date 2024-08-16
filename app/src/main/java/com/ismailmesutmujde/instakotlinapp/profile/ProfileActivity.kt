package com.ismailmesutmujde.instakotlinapp.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityProfileBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class ProfileActivity : AppCompatActivity() {

    private lateinit var bindingProfileActivity: ActivityProfileBinding

    private val ACTIVITY_NO = 4
    private val TAG = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingProfileActivity = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(bindingProfileActivity.root)

        setupNavigationView()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingProfileActivity.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingProfileActivity.bottomNavigationView)
        var menu = bindingProfileActivity.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}