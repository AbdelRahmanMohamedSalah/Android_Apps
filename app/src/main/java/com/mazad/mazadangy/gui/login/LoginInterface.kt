package com.mazad.mazadangy.gui.login

interface LoginInterface {
    fun emptyDialog()
    fun sucessLogin(uId:String)
    fun error()
    fun noConnection()
}