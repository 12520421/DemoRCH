package com.atg.demorch.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import com.atg.dcard.utils.BaseActivity
import com.atg.demorch.R
import com.atg.demorch.databinding.ActivitySplashBinding
import com.atg.demorch.ext.viewBinding
import com.atg.demorch.ui.main.MainActivity
import com.atg.demorch.utils.Constants

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override fun bindView() {

    }

    override fun observer() {
        scheduleSplashScreen(this)
    }

    private fun scheduleSplashScreen(context: Context) {
        val splashDuration = getSplashScreenDuration()
        Handler().postDelayed({
            moveOn(context)
            finish()
        }, splashDuration)

    }


    private fun moveOn(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }


    private fun getSplashScreenDuration() = Constants.SPLASH_DURATION
}