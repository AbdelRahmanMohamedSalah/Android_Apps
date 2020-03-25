package com.mazad.mazadangy.gui.login

import android.content.Context
import android.text.TextUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mazad.mazadangy.utels.StaticMethod

class LoginPresenter {
    var loginInterface:LoginInterface
    var mAuth:FirebaseAuth
    var firebaseUser: FirebaseUser? = null
    constructor(loginInterface: LoginInterface){
        this.loginInterface=loginInterface
        mAuth=FirebaseAuth.getInstance()

    }
    fun checkLogin(email: String, pass: String, context: Context) {
        if (StaticMethod.isConnectingToInternet(context)) {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                loginInterface.emptyDialog()

            }else{
                mAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener{task: Task<AuthResult> ->
                        if (task.isSuccessful){

                            loginInterface.sucessLogin(mAuth.currentUser!!.uid)
                        }else{
                            loginInterface.error()
                        }
                    }

            }
        }else{
            loginInterface.noConnection()
        }
    }
}