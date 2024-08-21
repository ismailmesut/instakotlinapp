package com.ismailmesutmujde.instakotlinapp.home.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityHomeBinding
import com.ismailmesutmujde.instakotlinapp.home.fragment.CameraFragment
import com.ismailmesutmujde.instakotlinapp.home.fragment.HomeFragment
import com.ismailmesutmujde.instakotlinapp.home.fragment.MessagesFragment
import com.ismailmesutmujde.instakotlinapp.login.activity.LoginActivity
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader
import com.ismailmesutmujde.instakotlinapp.utils.adapter.HomePagerAdapter
import com.nostra13.universalimageloader.core.ImageLoader

class HomeActivity : AppCompatActivity() {

    private lateinit var bindingHA: ActivityHomeBinding

    private val ACTIVITY_NO = 0
    private val TAG = "HomeActivity"

    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHA = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindingHA.root)

        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()

        initImageLoader()
        setupNavigationView()
        setupHomeViewPager()
    }

    private fun setupNavigationView() {

        val bottomNavigationViewEx = bindingHA.bnvHome
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        BottomNavigationViewHelper.setupNavigation(this@HomeActivity, bottomNavigationViewEx)
        var menu = bottomNavigationViewEx.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)

    }

    private fun setupHomeViewPager() {
        var homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        homePagerAdapter.addFragment(CameraFragment())   // id=0
        homePagerAdapter.addFragment(HomeFragment())     // id=1
        homePagerAdapter.addFragment(MessagesFragment()) // id=2

        // Activity Home da bulunan viewpagera oluşturduğumuz adaptörü atadık
        bindingHA.homeViewPager.adapter = homePagerAdapter
        // ViewPager ın HomeFragment ile başlamasını sağladık
        bindingHA.homeViewPager.setCurrentItem(1)
    }

    private fun initImageLoader() {
        var universalImageLoader =UniversalImageLoader(this)
        ImageLoader.getInstance().init(universalImageLoader.config)
    }


    private fun setupAuthListener() {

        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser
                if (user == null) {
                    var intent = Intent(this@HomeActivity, LoginActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }

    }
}