package com.ehsanfallahi.loginapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.ui.MainActivity
import com.ehsanfallahi.loginapp.ui.intro.IntroSliderActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            val sliderActivity = Intent(this@SplashActivity, IntroSliderActivity::class.java)
            this@SplashActivity.startActivity(sliderActivity)
            this@SplashActivity.finish()

        }, 3500)
    }
}
