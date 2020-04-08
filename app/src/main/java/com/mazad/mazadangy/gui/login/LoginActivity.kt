package com.mazad.mazadangy.gui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.mazad.mazadangy.gui.category.CategoryActivity
import com.mazad.mazadangy.gui.signup.SignUpActivity
import com.mazad.mazadangy.model.UserModel
import com.mazad.mazadangy.utels.ToastUtel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginInterface {
    lateinit var loginPresenter:LoginPresenter
    lateinit var user: FirebaseUser
    lateinit var userModel: UserModel
    lateinit var databaseRefranceUser: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter = LoginPresenter(this)
        listenerView()
s
        user = FirebaseAuth.getInstance().getCurrentUser()!!
        if (user != null) {
            startActivity(Intent(this@LoginActivity, CategoryActivity::class.java))
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
        val pref = applicationContext.getSharedPreferences("MyPref", 0)
        val editor: SharedPreferences.Editor = pref.edit()

        databaseRefranceUser = FirebaseDatabase.getInstance().getReference("users")

        val menuListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var firstname = dataSnapshot.child("firstName").getValue(String::class.java)
                var lastname = dataSnapshot.child("lastName").getValue(String::class.java)
                var email = dataSnapshot.child("email").getValue(String::class.java)
                var phonenumber = dataSnapshot.child("phoneNumber").getValue(String::class.java)
                var interest = dataSnapshot.child("interest").getValue(String::class.java)
                var nickname = dataSnapshot.child("nickname").getValue(String::class.java)
                var uid = dataSnapshot.child("uId").getValue(String::class.java)
                var image_profile = dataSnapshot.child("image_profile").getValue(String::class.java)
                userModel = UserModel(
                    uid!!,
                    phonenumber!!,
                    nickname!!,
                    lastname!!,
                    interest!!,
                    firstname!!,
                    email!!,
                    image_profile!!
                )
                editor.putString("firstName", userModel.firstName + "")
                editor.putString("lastName", userModel.lastName + "")
                editor.putString("email", userModel.email + "")
                editor.putString("phoneNumber", userModel.phoneNumber + "")
                editor.putString("interest", userModel.interest + "")
                editor.putString("nickname", userModel.nickname + "")
                editor.putString("uId", userModel.uId + "")
                editor.putString("image_profile", userModel.image_profile + "")
                editor.commit()
            }

        }

        databaseRefranceUser.child(user.uid.toString()).addListenerForSingleValueEvent(menuListener)





    }

    override fun error() {
        ToastUtel.errorToast(this, "خطأ في البريد الالكتروني او كلمه المرور ")
    }

    override fun noConnection() {
        ToastUtel.errorToast(this, "من فضلك تاكد من وجود انترنت ")
    }


}
