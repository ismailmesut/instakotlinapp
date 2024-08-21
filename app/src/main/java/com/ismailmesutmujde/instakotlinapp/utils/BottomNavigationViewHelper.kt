@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.utils

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.home.activity.HomeActivity
import com.ismailmesutmujde.instakotlinapp.search.SearchActivity
import com.ismailmesutmujde.instakotlinapp.share.activity.ShareActivity
import com.ismailmesutmujde.instakotlinapp.news.NewsActivity
import com.ismailmesutmujde.instakotlinapp.profile.activity.ProfileActivity
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.google.android.material.bottomnavigation.BottomNavigationView
class BottomNavigationViewHelper {

    //private lateinit var bottomNavigationViewEx: BottomNavigationViewEx
    companion object{
        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.enableItemShiftingMode(false)
            bottomNavigationViewEx.enableShiftingMode(false)
            bottomNavigationViewEx.setTextVisibility(false)
        }

        fun setupNavigation(context: Context, bottomNavigationViewEx: BottomNavigationViewEx){
            bottomNavigationViewEx.onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId){
                        R.id.ic_home -> {
                            val intent = Intent(context, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }
                        R.id.ic_search -> {
                            val intent = Intent(context, SearchActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }
                        R.id.ic_share -> {
                            val intent = Intent(context, ShareActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }
                        R.id.ic_news -> {
                            val intent = Intent(context, NewsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }
                        R.id.ic_profile -> {
                            val intent = Intent(context, ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }
                    }
                    return false
                }

            }

        }
    }

}