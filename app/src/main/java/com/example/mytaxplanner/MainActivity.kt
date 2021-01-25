package com.example.mytaxplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.mytaxplanner.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.fragment_home -> {
                    // Respond to navigation item 1 click
                    Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.fragment_income -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(this,"Income",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.fragment_file -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(this,"File",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.fragment_tax -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(this,"Tax",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.fragment_suggest -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(this,"Suggest",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

}