@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.utils.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SharePagerAdapter(fm : FragmentManager, tabNames : ArrayList<String>): FragmentPagerAdapter(fm) {

    private var mFragmentList : ArrayList<Fragment> = ArrayList()
    private var mTabNames : ArrayList<String> = tabNames
    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }
    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabNames.get(position)
    }

}