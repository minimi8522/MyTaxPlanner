package com.example.mytaxplanner

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mytaxplanner.databinding.ActivityMainBinding
import com.example.mytaxplanner.fragment.*
import com.example.mytaxplanner.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        const val PICK_IMAGE = 1000
    }
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).commit()
                    true
                }
                R.id.fragment_income -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, IncomeFragment()).commit()
                    true
                }
//                R.id.fragment_file -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FileFragment()).commit()
//                    true
//                }
                R.id.fragment_tax -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, TaxFragment()).commit()
                    true
                }
                R.id.fragment_suggest -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SuggestFragment()).commit()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.fragment_home
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }
}