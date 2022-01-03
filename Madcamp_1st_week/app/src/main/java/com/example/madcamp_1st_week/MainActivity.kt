package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.madcamp_1st_week.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val firstFragment by lazy {FirstFragment()}
    private val secondFragment by lazy {SecondFragment()}
    private val thirdFragment by lazy {ThirdFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationBar()
        initMenuButton()
        initAddButton()
        initMyProfileButton()

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragment, firstFragment)
            .commit()
    }

    fun initMenuButton() {
        binding.menu.setOnClickListener{
            when(binding.myProfile.visibility) {
                FloatingActionButton.INVISIBLE -> openDropDownMenu()
                FloatingActionButton.VISIBLE -> closeDropDownMenu()
            }
        }
    }

    fun openDropDownMenu() {
        binding.menu
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_open_with_rotate))

        binding.add
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.dropdown_from_top))
        binding.add.visibility = FloatingActionButton.VISIBLE;

        binding.myProfile
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.dropdown_from_top))
        binding.myProfile.visibility = FloatingActionButton.VISIBLE;
    }

    fun closeDropDownMenu() {
        binding.menu
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_close_with_rotate))

        binding.add
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.dropdown_to_top))
        binding.add.visibility = FloatingActionButton.INVISIBLE;

        binding.myProfile
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.dropdown_to_top))
        binding.myProfile.visibility = FloatingActionButton.INVISIBLE;
    }

    fun initAddButton() {
        binding.add.setOnClickListener {
            closeDropDownMenu()
            val integrator = IntentIntegrator(this)
            integrator.setBarcodeImageEnabled(false)
            integrator.setBeepEnabled(false)
            integrator.setPrompt("화면에 QR 코드를 인식시켜주세요")
            integrator.initiateScan()
        }
    }

    fun initMyProfileButton() {
        binding.myProfile.setOnClickListener {
            closeDropDownMenu()
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom)
                .replace(R.id.fragment, MyProfileFragment())
                .commit()
        }
    }

    fun initNavigationBar() {
        binding.navBar.setOnItemSelectedListener { item->
            var currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment)
            if (binding.myProfile.visibility == ImageButton.VISIBLE) closeDropDownMenu()
            when(item.itemId) {
                R.id.nav_first -> {
                    if (currentFragment !is FirstFragment) {
                        if (currentFragment is MyProfileFragment) {
                            supportFragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                                .replace(R.id.fragment, firstFragment)
                                .commit()
                        }
                        else {
                            supportFragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.fragment, firstFragment)
                                .commit()
                        }
                    }
                    true
                }
                R.id.nav_second -> {
                    if (currentFragment is FirstFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.fragment, secondFragment)
                            .commit()
                    }
                    else if (currentFragment is ThirdFragment){
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.fragment, secondFragment)
                            .commit()
                    }
                    else if (currentFragment is FeedDetailFragment){
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.fragment, secondFragment)
                            .commit()
                    }
                    else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                            .replace(R.id.fragment, secondFragment)
                            .commit()
                    }
                    true
                }
                R.id.nav_third -> {
                    if (currentFragment is FirstFragment || currentFragment is SecondFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.fragment, thirdFragment)
                            .commit()
                    }
                    else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                            .replace(R.id.fragment, thirdFragment)
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