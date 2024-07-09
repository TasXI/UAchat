package com.example.chatua

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LogViewModel : ViewModel() {

    val auth = Firebase.auth
    val account = MutableLiveData<FirebaseUser?>()
    fun logIn(email: String, password: String){

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful)
                account.value = auth.currentUser
            else
                account.value = null
        }

    }

    fun logFailure(){
        account.value = null
    }


}