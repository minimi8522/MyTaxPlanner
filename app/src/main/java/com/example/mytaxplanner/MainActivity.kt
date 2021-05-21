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
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val selectedImage: Uri? = data?.data
            val picturePath: String? = getRealPathFromURI(selectedImage, this)
            if (picturePath != null) {
                val fragment = FileFragment()
                val args = Bundle()
                args.putString("picturePath", picturePath)
                fragment.arguments = args
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
            }
        }
    }

    private fun getRealPathFromURI(selectedImage: Uri?, activity: Activity): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = activity.managedQuery(selectedImage, projection, null, null, null) ?: return null

        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        return if (cursor.moveToFirst()) {
            return cursor.getString(column_index)
        } else {
            null
        }
    }
}