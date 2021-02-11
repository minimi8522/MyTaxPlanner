package com.example.mytaxplanner

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mytaxplanner.databinding.ActivityMainBinding
import com.example.mytaxplanner.fragment.*

class MainActivity : AppCompatActivity() {
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
                R.id.fragment_file -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FileFragment()).commit()
                    true
                }
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

//        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
//        viewModel.getAllTax()?.value?.size
//        viewModel.getAllTax()!!.observe(this, Observer { data ->
//            Log.e("Main",data.size.toString())
//        })
    }
}