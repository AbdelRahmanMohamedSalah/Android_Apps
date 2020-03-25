package com.mazad.mazadangy.gui.signup

import android.R.attr.password
import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mazad.mazadangy.utels.StaticMethod


class SignUpPresenter {
    val mAuth: FirebaseAuth
    var signUpInterface: SignUpInterface

    constructor(signUpInterface: SignUpInterface) {
        this.signUpInterface = signUpInterface
        mAuth = FirebaseAuth.getInstance(); }

    fun checkSignUp(email: String, pass: String, context: Context) {
        if (StaticMethod.isConnectingToInternet(context)) {
            this.mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    var userId=mAuth.currentUser?.uid
                    signUpInterface.sucessSignUp(userId.toString())

                } else {
                    signUpInterface.errorSignUp()
                }
            }

        } else {
            signUpInterface.noConnection()
        }
    }
}