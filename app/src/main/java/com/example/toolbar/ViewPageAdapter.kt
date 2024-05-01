package com.example.toolbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPageAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            ProfileFragment()
        }else{
            ChatFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0){
            "Profile"
        }else{
            "Chat"
        }
    }


}