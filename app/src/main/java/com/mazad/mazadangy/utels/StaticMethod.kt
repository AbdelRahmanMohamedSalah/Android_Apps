package com.mazad.mazadangy.utels
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.mazad.mazadangy.R

class StaticMethod {

    companion object {
        fun toggleFullScreen(activtiy: Activity) {
            if (activtiy.window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                activtiy.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
                activtiy.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
            } else {
                activtiy.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                activtiy.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }

        fun isConnectingToInternet(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }




        fun showSnake(view: View,string: String) {
            var snackbar: Snackbar = Snackbar.make(view, string, Snackbar.LENGTH_LONG)

            var color: Int = 0
            var sbView: View = snackbar.view
            var textView  : TextView  = sbView . findViewById (R.id.snackbar_text)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

            color = Color.YELLOW
            textView.setTextColor(color)
            snackbar.show()
        }


    }


}