package com.mazad.mazadangy.gui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.mazad.mazadangy.R
import com.mazad.mazadangy.gui.login.LoginActivity
import com.mazad.mazadangy.utels.StaticMethod
import com.mazad.mazadangy.utels.StaticMethod.Companion.toggleFullScreen
import kotlinx.android.synthetic.main.splash_screan.*

class SplashScrean : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screan)
        toggleFullScreen(this)
        checkInternet()
        reconnectBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                reconnectBtn.visibility = View.GONE
                checkInternet()

            }
        })
    }

    private fun checkInternet() {
        if (StaticMethod.isConnectingToInternet(this)) {
            delayShowText()

        } else {
            reconnectBtn.visibility = View.VISIBLE
            StaticMethod.showSnake(splashId, "لا يوجد اتصال بالأنترنت")
        }

    }

    private fun delayShowText() {
        val handler = Handler()

        handler.postDelayed({
            // do any thing her
            startActivity(Intent(this, LoginActivity::class.java))
            finish()


        }, 2000)

    }
}
