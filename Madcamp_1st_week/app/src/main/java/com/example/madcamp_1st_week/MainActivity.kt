package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.madcamp_1st_week.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val firstFragment by lazy {FirstFragment()}
    private val secondFragment by lazy {SecondFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationBar()
        initAddButton()

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragment, FirstFragment())
            .commit()
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
            var currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment)
            when(item.itemId) {
                R.id.nav_first -> {
                    if (currentFragment !is FirstFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.fragment, FirstFragment())
                            .commit()
                    }
                    true
                }
                R.id.nav_second -> {
                    if (currentFragment is FirstFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.fragment, SecondFragment())
                            .commit()
                    }
                    else if (currentFragment is ThirdFragment){
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.fragment, SecondFragment())
                            .commit()
                    }
                    else if (currentFragment is FeedDetailFragment){
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.fragment, SecondFragment())
                            .commit()
                    }
                    true
                }
                R.id.nav_third -> {
                    if (currentFragment !is ThirdFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.fragment, ThirdFragment())
                            .commit()
                    }
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
            var bundle = Bundle()
            bundle.putString("JSON", result.contents)

            val contactAddFragment = ContactAddFragment()
            contactAddFragment.setArguments(bundle)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, contactAddFragment)
                .commitAllowingStateLoss()
        }
    }
}