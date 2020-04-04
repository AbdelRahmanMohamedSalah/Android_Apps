package com.mazad.mazadangy.gui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mazad.mazadangy.R
import com.mazad.mazadangy.gui.category.CategoryActivity
import com.mazad.mazadangy.gui.signup.SignUpActivity
import com.mazad.mazadangy.utels.ToastUtel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginInterface {
    lateinit var loginPresenter:LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter= LoginPresenter(this)
        listenerView()

        val user = FirebaseAuth.getInstance().getCurrentUser()
        if (user != null) {
            startActivity(Intent(this@LoginActivity,CategoryActivity::class.java))
            finish()
        }
    }


    private fun listenerView() {
        newAccTv.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            }

        })
        rBtnLogin.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                loginPresenter.checkLogin(emailLogin.text.toString().trim(),passLogin.text.toString().trim(),this@LoginActivity)
            }
        })
    }

    override fun emptyDialog() {
        ToastUtel.errorToast(this, "من فضلك املأ الفراغات")

    }

    override fun sucessLogin(uId: String) {
        ToastUtel.showSuccessToast(this, "تم تسجيل الدخول بنجاح")
        startActivity(Intent(this,CategoryActivity::class.java))

    }

    override fun error() {
        ToastUtel.errorToast(this, "خطأ في البريد الالكتروني او كلمه المرور ")
    }

    override fun noConnection() {
        ToastUtel.errorToast(this, "من فضلك تاكد من وجود انترنت ")
    }


}
