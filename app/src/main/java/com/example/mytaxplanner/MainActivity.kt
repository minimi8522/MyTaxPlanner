package com.example.mytaxplanner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytaxplanner.databinding.ActivityMainBinding
import com.example.mytaxplanner.fragment.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.fragment_home
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.fragment_income -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, IncomeFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.fragment_file -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FileFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.fragment_tax -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, TaxFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.fragment_suggest -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SuggestFragment()).addToBackStack(null).commit()
                    true
                }
                else -> false
            }
        }
    }
}