package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.madcamp_1st_week.MainActivity
import com.example.madcamp_1st_week.databinding.ActivityLoadingBinding
import com.google.zxing.integration.android.IntentIntegrator

class LoadingActivity : AppCompatActivity() {
    private var _binding: ActivityLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
            스마트폰 상태바의 색상을 바꿔서 마치 전체화면처럼 보이게 합니다
         */
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.darknavy)

        _binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
            window.decorView.postDelayed()
                -> delayMillis만큼 기다렸다가 Runnable 내부의 코드를 실행합니다.

            Intent 타입의 변수를 선언 -> Activity 간의 전환을 위해 필요
            Intent.FLAG 값을 통해 현재 Activity를 어떻게 할지 정할 수 있음
                -> 기본적으로 새로운 Activity가 실행이 된다면 Activity Stack에 쌓임
                    -> 따라서 뒤로가기를 통해 현재 Activity를 종료하게되면 Activity Stack의 최상단에 놓인 Activity를 호출
                -> Loading Activity의 경우 최초 1회만 실행해야하므로 setFlag()를 통해 stack에 집어 넣지 않고 버리는 값을 추가해야함

         */
        window.decorView.postDelayed(Runnable {
            var intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }, 1250)
    }
}