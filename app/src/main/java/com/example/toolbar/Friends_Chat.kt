package com.example.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Friends_Chat : AppCompatActivity() {
    lateinit var tab : TabLayout
    lateinit var viewPager:ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_chat)
        tab = findViewById(R.id.tab)
        viewPager = findViewById(R.id.viewPager)



        //Making the adapter to show changes in the fragment while sliding it
        val adapter = ViewPageAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        //Synchronising the tablayout and viewPager together so that upon sliding the viewPage it shows changes in the tablayout
        tab.setupWithViewPager(viewPager)
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }
}