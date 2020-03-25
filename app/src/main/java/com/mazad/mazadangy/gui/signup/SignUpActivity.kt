package com.mazad.mazadangy.gui.signup

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mazad.mazadangy.R
import com.mazad.mazadangy.utels.ToastUtel
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity(), SignUpInterface {
    lateinit var signUpPresenter: SignUpPresenter
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseRefrance: DatabaseReference

    companion object {
        var antekaFav: Boolean = false
        var otherFav: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseDatabase = FirebaseDatabase.getInstance()
        signUpPresenter = SignUpPresenter(this)

        signUpBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signUpData()

            }
        })

    }


    private fun signUpData() {

        var firstName = firstName.text.toString().trim()
        var lastName = lNameEt.text.toString().trim()
        var neckName = nickName.text.toString().trim()
        var email = emailTv.text.toString().trim()
        var password = passwordTv.text.toString().trim()
        var conPass = confirmPassTv.text.toString().trim()
        var phone = phoneTv.text.toString().trim()
        var interistTv = interistTv.text.toString().trim()
        if (antekatChB.isChecked) {
            antekaFav = true

        }
        if (othertChB.isChecked) {
            otherFav = true
        }
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                neckName
            )
            && TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPass) || TextUtils.isEmpty(
                phone
            )
            || TextUtils.isEmpty(interistTv)
        ) {
            ToastUtel.errorToast(this, "من فضلك املأ الفراغات")
        } else if (!password.equals(conPass)) {
            ToastUtel.errorToast(this, "كلمة السر غير متطابفة!")

        } else {
            signUpPresenter.checkSignUp(email, password, this)
        }


    }

    override fun errorSignUp() {
        ToastUtel.errorToast(this, "من فضلك ادخل البيانات بشكل صحيح ")
    }

    override fun sucessSignUp(uId: String) {
        databaseRefrance = firebaseDatabase.getReference("users").child(uId)
        databaseRefrance.child("firstName").setValue(firstName.text.toString().trim())
        databaseRefrance.child("lastName").setValue(lNameEt.text.toString().trim())
        databaseRefrance.child("nickname").setValue(nickName.text.toString().trim())
        databaseRefrance.child("email").setValue(emailTv.text.toString().trim())
        databaseRefrance.child("phoneNumber").setValue(phoneTv.text.toString().trim())
        databaseRefrance.child("uId").setValue(uId)
        databaseRefrance.child("interest").setValue(interistTv.text.toString().trim())
        databaseRefrance.child("categories").child("anteka").setValue(antekaFav)
        databaseRefrance.child("categories").child("other").setValue(otherFav)

        ToastUtel.showSuccessToast(this, "تم تسجيل حساب جديد بنجاح")
        finish()
    }

    override fun noConnection() {
            ToastUtel.errorToast(this, "من فضلك تاكد من وجود انترنت ")

    }

}
