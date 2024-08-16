package com.ismailmesutmujde.instakotlinapp.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityNewsBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper


class NewsActivity : AppCompatActivity() {

    private lateinit var bindingNA : ActivityNewsBinding

    private val ACTIVITY_NO = 3
    private val TAG = "NewsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingNA = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(bindingNA.root)

        setupNavigationView()

    }
    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingNA.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingNA.bottomNavigationView)
        var menu = bindingNA.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}