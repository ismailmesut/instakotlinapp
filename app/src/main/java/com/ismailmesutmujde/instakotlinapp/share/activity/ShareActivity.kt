package com.ismailmesutmujde.instakotlinapp.share.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityShareBinding
import com.ismailmesutmujde.instakotlinapp.share.fragment.ShareCameraFragment
import com.ismailmesutmujde.instakotlinapp.share.fragment.ShareGalleryFragment
import com.ismailmesutmujde.instakotlinapp.share.fragment.ShareVideoFragment
import com.ismailmesutmujde.instakotlinapp.utils.adapter.SharePagerAdapter

class ShareActivity : AppCompatActivity() {

    private lateinit var bindingShA: ActivityShareBinding

    private val ACTIVITY_NO = 2
    private val TAG = "ShareActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingShA = ActivityShareBinding.inflate(layoutInflater)
        setContentView(bindingShA.root)

        setupShareViewPager()

    }

    private fun setupShareViewPager() {
        var tabNames = ArrayList<String>()
        tabNames.add("GALLERY")
        tabNames.add("PHOTO")
        tabNames.add("VIDEO")
        var sharePagerAdapter = SharePagerAdapter(supportFragmentManager, tabNames)
        sharePagerAdapter.addFragment(ShareGalleryFragment())
        sharePagerAdapter.addFragment(ShareCameraFragment())
        sharePagerAdapter.addFragment(ShareVideoFragment())

        bindingShA.shareViewPager.adapter = sharePagerAdapter
        bindingShA.shareTabLayout.setupWithViewPager(bindingShA.shareViewPager)
    }


}