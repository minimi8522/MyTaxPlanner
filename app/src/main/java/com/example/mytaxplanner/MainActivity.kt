package com.example.mytaxplanner

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        val splashScreen = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).commit()
                    binding.topAppBar.apply {
                        setBackgroundColor(resources.getColor(R.color.teal_200,theme))
                        title = getString(R.string.fragment_home)
                    }
                    window.apply {
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        statusBarColor = resources.getColor(R.color.teal_700,theme)
                    }
                    true
                }
                R.id.fragment_income -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, IncomeFragment()).commit()
                    binding.topAppBar.apply {
                        setBackgroundColor(resources.getColor(R.color.blue,theme))
                        title = getString(R.string.fragment_income)
                    }
                    window.apply {
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        statusBarColor = resources.getColor(R.color.dark_blue,theme)
                    }
                    true
                }
                R.id.fragment_tax -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, TaxFragment()).commit()
                    binding.topAppBar.apply {
                        setBackgroundColor(resources.getColor(R.color.pink,theme))
                        title = getString(R.string.fragment_tax)
                    }
                    window.apply {
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        statusBarColor = resources.getColor(R.color.dark_pink,theme)
                    }
                    true
                }
                R.id.fragment_suggest -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SuggestFragment()).commit()
                    binding.topAppBar.apply {
                        setBackgroundColor(resources.getColor(R.color.purple_200,theme))
                        title = getString(R.string.fragment_suggest)
                    }
                    window.apply {
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        statusBarColor = resources.getColor(R.color.purple_700,theme)
                    }
                    true
                }
//                R.id.fragment_more -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.fragment, MoreFragment()).commit()
//                    binding.topAppBar.apply {
//                        setBackgroundColor(resources.getColor(R.color.teal_200,theme))
//                        title = getString(R.string.fragment_more)
//                    }
//                    window.apply {
//                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                        statusBarColor = resources.getColor(R.color.teal_700,theme)
//                    }
//                    true
//                }
//                R.id.fragment_file -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FileFragment()).commit()
//                    true
//                }
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.fragment_home
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        viewModel.calculateTaxIncome.observe(this, {

        })
    }
}