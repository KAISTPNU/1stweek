package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.madcamp_1st_week.MainActivity
import com.example.madcamp_1st_week.databinding.ActivityLoadingBinding
import com.google.zxing.integration.android.IntentIntegrator

class LoadingActivity : AppCompatActivity() {
    private var _binding: ActivityLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.postDelayed(Runnable {
            var intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }, 1250)
    }
}