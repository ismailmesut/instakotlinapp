package com.ismailmesutmujde.instakotlinapp.share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityShareBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class ShareActivity : AppCompatActivity() {

    private lateinit var bindingShareActivity: ActivityShareBinding

    private val ACTIVITY_NO = 2
    private val TAG = "ShareActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingShareActivity = ActivityShareBinding.inflate(layoutInflater)
        setContentView(bindingShareActivity.root)

        setupNavigationView()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingShareActivity.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingShareActivity.bottomNavigationView)
        var menu = bindingShareActivity.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}