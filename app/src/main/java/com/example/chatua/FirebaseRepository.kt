package com.example.chatua

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class FirebaseRepository private constructor(){

    private val auth = Firebase.auth
    private val database = Firebase.database

    fun getAuth(): FirebaseAuth{
        return auth
    }

    fun getDataBase(): FirebaseDatabase{
        return database
    }

    companion object {
        private var INSTANCE: FirebaseRepository? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = FirebaseRepository()
            }
        }
        fun get(): FirebaseRepository {
            return INSTANCE ?:
            throw IllegalStateException("FirebaseRepository must be initialized")
        }
    }

}