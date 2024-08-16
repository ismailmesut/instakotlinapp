package com.ismailmesutmujde.instakotlinapp.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivitySearchBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class SearchActivity : AppCompatActivity() {

    private lateinit var bindingSeA: ActivitySearchBinding

    private val ACTIVITY_NO = 1
    private val TAG = "SearchActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingSeA = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(bindingSeA.root)

        setupNavigationView()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingSeA.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingSeA.bottomNavigationView)
        var menu = bindingSeA.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}