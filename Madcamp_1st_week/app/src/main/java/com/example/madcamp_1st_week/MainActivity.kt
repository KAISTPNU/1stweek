package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.madcamp_1st_week.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val firstFragment by lazy {FirstFragment()}
    private val secondFragment by lazy {SecondFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, FirstFragment())
            .commit()

        initNavigationBar()
        initAddButton()
    }

    fun initAddButton() {
        binding.add.setOnClickListener{
            val integrator = IntentIntegrator(this)
            integrator.setBarcodeImageEnabled(false)
            integrator.setBeepEnabled(false)
            integrator.setPrompt("화면에 QR 코드를 인식시켜주세요")
            integrator.initiateScan()
        }
    }


    fun initNavigationBar() {
        binding.navBar.setOnItemSelectedListener { item->
            when(item.itemId) {
                R.id.nav_first -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, FirstFragment())
                        .commit()
                    true
                }
                R.id.nav_second -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, SecondFragment())
                        .commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result.contents == null) {
            Toast.makeText(applicationContext, "스캔 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            var obj: JSONObject = JSONObject(result.contents)
            Log.d("Result:", obj.getString("name"))
        }
    }
}