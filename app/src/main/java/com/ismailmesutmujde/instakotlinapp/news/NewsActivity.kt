package com.ismailmesutmujde.instakotlinapp.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityNewsBinding
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper


class NewsActivity : AppCompatActivity() {

    private lateinit var bindingNewsActivity : ActivityNewsBinding

    private val ACTIVITY_NO = 3
    private val TAG = "NewsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingNewsActivity = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(bindingNewsActivity.root)

        setupNavigationView()

    }
    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingNewsActivity.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingNewsActivity.bottomNavigationView)
        var menu = bindingNewsActivity.bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}