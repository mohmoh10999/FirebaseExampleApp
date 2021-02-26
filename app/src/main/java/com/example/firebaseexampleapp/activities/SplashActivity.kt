package com.example.firebaseexampleapp.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseexampleapp.R
import com.example.firebaseexampleapp.firebase.FireStoreClass
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val typeface: Typeface = Typeface.createFromAsset(assets, "carbon bl.ttf")
        txtSplash.setTypeface(typeface)
        Handler().postDelayed(Runnable {
            if (FireStoreClass().getCurrentUserID().isNotEmpty())
                startActivity(Intent(this, MainActivity::class.java))
            else
                startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }, 2000)

    }
}