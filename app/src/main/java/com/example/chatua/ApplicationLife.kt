package com.example.chatua

import android.app.Application

class ApplicationLife : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseRepository.initialize()
    }
}