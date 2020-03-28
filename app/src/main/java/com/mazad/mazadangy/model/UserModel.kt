package com.mazad.mazadangy.model

import java.io.Serializable

class UserModel : Serializable {
    lateinit var uId: String
    lateinit var phoneNumber: String
    lateinit var nickname: String
    lateinit var lastName: String
    lateinit var interest: String
    lateinit var firstName: String
    lateinit var email: String
    // lateinit var categories:String
    lateinit var categories: List<String>

}