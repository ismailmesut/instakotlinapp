package com.ismailmesutmujde.instakotlinapp.home.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityHomeBinding
import com.ismailmesutmujde.instakotlinapp.home.fragment.CameraFragment
import com.ismailmesutmujde.instakotlinapp.home.fragment.HomeFragment
import com.ismailmesutmujde.instakotlinapp.home.fragment.MessagesFragment
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader
import com.ismailmesutmujde.instakotlinapp.utils.adapter.HomePagerAdapter
import com.nostra13.universalimageloader.core.ImageLoader

class HomeActivity : AppCompatActivity() {

    private lateinit var bindingHA: ActivityHomeBinding

    private val ACTIVITY_NO = 0
    private val TAG = "HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingHA = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindingHA.root)

        initImageLoader()
        setupNavigationView()
        setupHomeViewPager()
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingHA.bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this, bindingHA.bottomNavigationView)
        var menu = bindingHA.bottomNavigationView.menu
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
}