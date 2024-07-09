package com.example.chatua

import java.io.Serializable

data class User(var name: String? = null, var email: String? = null, var uid: String? = null) : Serializable{
}