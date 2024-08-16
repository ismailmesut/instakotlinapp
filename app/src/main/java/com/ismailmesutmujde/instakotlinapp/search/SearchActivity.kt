package com.ismailmesutmujde.instakotlinapp.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivitySearchBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper

class SearchActivity : AppCompatActivity() {

    private lateinit var bindingSearchBinding: ActivitySearchBinding

    private val ACTIVITY_NO = 1
    private val TAG = "SearchActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingSearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(bindingSearchBinding.root)

        setupNavigationView()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingSearchBinding.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingSearchBinding.bottomNavigationView)
        var menu = bindingSearchBinding.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}