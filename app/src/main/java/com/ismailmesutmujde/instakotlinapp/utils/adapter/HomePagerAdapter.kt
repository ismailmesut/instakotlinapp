@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.utils.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    private var mFragmentList : ArrayList<Fragment> = ArrayList()

    // fragment pager adapter ın yazmayı zorunlu kıldığı fonksiyonlar
    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }


    // kişisel fonksiyon
    fun addFragment(fragment : Fragment) {
        mFragmentList.add(fragment)
    }
}