package com.example.chatua

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class SignViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val database = Firebase.database

    val registrationIsSuccess = MutableLiveData<Boolean>()

    fun signUp(username: String, email: String, password: String ){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful) {
                registrationIsSuccess.value = true
                auth.currentUser?.let { it1 -> addUserToDatabase(username, email, it1.uid) }
                auth.signOut()
            }
            else
            {
                registrationIsSuccess.value = false
            }


        }

    }

    fun dataIncorrect(){
        registrationIsSuccess.value = false
    }

    private fun addUserToDatabase(name: String, email: String, uid: String){
        database.reference.child("users").child(uid).setValue(User(name, email, uid))
    }


}