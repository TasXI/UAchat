package com.example.chatua

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainViewModel : ViewModel() {

    private val database = Firebase.database

    val user = MutableLiveData<User>()
    val otherUsers = MutableLiveData<List<User>>()
    var userIsFound = MutableLiveData<Boolean>()
    val allChats = MutableLiveData<List<Chat>>()

    fun loadUser(uid: String) {

        database.reference.child("users").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentUser: User = snapshot.getValue(User::class.java)!!
                    user.value = currentUser
                    userIsFound.value = true
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    fun loadOtherUsers() {

        val uid = user.value!!.uid

        database.reference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = mutableListOf<User>()
                for (otherUser in snapshot.children) {
                    val checkUser = otherUser.getValue(User::class.java)!!
                    if (checkUser.uid != uid) {
                        users.add(checkUser)
                    }
                }
                otherUsers.value = users
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun loadChats(){
        val uid = user.value!!.uid

        database.reference.child("chats").child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chats =  mutableListOf<Chat>()
                for (chat in snapshot.children) {
                    chats.add(chat.getValue(Chat::class.java)!!)
                }
                allChats.value = chats
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}