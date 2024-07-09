package com.example.chatua

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.util.UUID

class ProfileViewModel : ViewModel() {

    private val database = Firebase.database

    val user = MutableLiveData<User>()
    val chtEx = MutableLiveData<Chat>()

    private var allUsers = listOf<User>()

    fun setAllUsers(ll: List<User>){
        allUsers = ll
    }

    fun setUser(user: User){
        this.user.value = user
    }



    fun chatIsExist(){

        val users = allUsers
        database.reference.child("chats").child(users[0].uid!!).get().addOnCompleteListener{
            if (it.isSuccessful){
                val listChat = mutableListOf<Chat>()
                for (ch in it.result.children){
                   listChat.add(ch.getValue(Chat::class.java)!!)
                }

                if (listChat.size != 0) {
                    for (checkChat in listChat) {

                        val hasExactUsers = checkChat.users.size == users.size && checkChat.users.containsAll(users)
                        if (hasExactUsers){
                            chtEx.value = checkChat!!
                            return@addOnCompleteListener
                        }
                    }
                    chtEx.value = null
                }
                else chtEx.value = null
            }
            else{
                chtEx.value = null
            }
        }

    }



    fun createChat(){
        val usersAboba = allUsers

        val uuid = UUID.randomUUID().toString()
        val chatRef = database.reference.child("chats")
        var createdChat: Chat? = null

        for (selUser in usersAboba) {

            var usersNames = ""

            for (otherUser in usersAboba){
                if (otherUser.uid != selUser.uid){
                    usersNames += otherUser.name
                }
            }

            val crChat = Chat(usersNames, usersAboba.toMutableList(), mutableListOf<Message>(Message("Hello world!", User("test", "test", "test"))), uuid)

            if(selUser.uid == usersAboba[0].uid)
                createdChat = crChat

            chatRef.child(selUser.uid!!).child(uuid).setValue(crChat)
        }

        chtEx.value = createdChat

    }
}