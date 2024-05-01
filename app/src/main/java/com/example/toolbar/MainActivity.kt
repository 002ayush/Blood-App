package com.example.toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
//////////////////////////////////////////////////////////import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    lateinit var drawerlayout : DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        drawerlayout = findViewById(R.id.drawerlayout)
        supportActionBar?.hide()
        navigationView = findViewById(R.id.navigationView)
        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.red))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val headerView = navigationView.getHeaderView(0)
        val headertxt = headerView.findViewById<TextView>(R.id.headertxt)
        val headermail = headerView.findViewById<TextView>(R.id.headermail)
        val headerimg = headerView.findViewById<ImageView>(R.id.imgView)
        headertxt.text = FirebaseAuth.getInstance().currentUser?.displayName
        headermail.text = FirebaseAuth.getInstance().currentUser?.email
        val fragmentManager: FragmentManager = supportFragmentManager

        val toggle  = ActionBarDrawerToggle(
                    this,drawerlayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer

        );
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.title = "Home"
        //loadFragment(MainFragment())

        headerimg.setOnClickListener {
            loadFragment(MeraProfile())
            drawerlayout.closeDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener {
            menuitem -> when(menuitem.itemId){
                R.id.home -> {

                    loadFragment(HomeFragment())

                    toolbar.title = "Home"
                    drawerlayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.people -> {
                    val intent = Intent(this@MainActivity,People::class.java)

                    startActivity(intent)
                    drawerlayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.donar_details -> {

                    loadFragment(donar_details())
                    drawerlayout.closeDrawer(GravityCompat.START)
                    toolbar.title = "Donar Details"
                    true
                }
                R.id.bloodtest -> {
                    val intent = Intent(this,DoctorsActivity::class.java)
                    startActivity(intent)
                    drawerlayout.closeDrawer(GravityCompat.START)
                    true
                }
            else -> false

            }


        }

    }


    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentCount: Int = fragmentManager.backStackEntryCount
        val currentFragment = fragmentManager.findFragmentById(R.id.fragment_main)
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START)
        }
        if (currentFragment is MainFragment){
            finishAffinity()
        }
        if (fragmentCount > 0) {
            fragmentManager.popBackStack()
        }
        else{
            super.onBackPressed()
        }
    }


    fun loadFragment(fragement:Fragment): Unit {
        val fragementManager = supportFragmentManager

        val fragmentTransaction = fragementManager.beginTransaction()

        fragmentTransaction.replace(R.id.container,fragement)
        drawerlayout.closeDrawers()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this@MainActivity).inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }

}




