package com.ismailmesutmujde.instakotlinapp.share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityShareBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class ShareActivity : AppCompatActivity() {

    private lateinit var bindingShA: ActivityShareBinding

    private val ACTIVITY_NO = 2
    private val TAG = "ShareActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingShA = ActivityShareBinding.inflate(layoutInflater)
        setContentView(bindingShA.root)

        setupNavigationView()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingShA.bnvShare)
        BottomNavigationViewHelper.setupNavigation(this, bindingShA.bnvShare)
        var menu = bindingShA.bnvShare.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}