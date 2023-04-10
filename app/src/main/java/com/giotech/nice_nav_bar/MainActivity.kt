package com.giotech.nice_nav_bar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val navView by lazy {
        findViewById<NiceNavBar>(R.id.customNavView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView.menu.clear()
        navView.inflateMenu(R.menu.menu_nice_nav_bar_default)



        navView.setNavigationItemSelectedListener {
            true
        }
    }
}