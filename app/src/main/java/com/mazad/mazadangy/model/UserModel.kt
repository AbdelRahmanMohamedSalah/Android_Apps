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
    lateinit var image_profile: String

    // lateinit var categories:String
    lateinit var categories: List<String>

    constructor(
        uId: String,
        phoneNumber: String,
        nickname: String,
        lastname: String,
        interest:String,
        firstname: String,
        email: String,
        image_profile:String
    ){
        this.uId=uId
        this.phoneNumber=phoneNumber
        this.nickname=nickname
        this.lastName=lastname
        this.interest=interest
        this.firstName=firstname
        this.email=email
        this.image_profile=image_profile

    }

}