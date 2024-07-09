package com.example.chatua

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.UUID

class CreateGroupChatViewModel : ViewModel() {

    val database = Firebase.database

    val chtEx = MutableLiveData<Chat>()

    var curr: User? = null



    fun createGroupChat(users: List<User>, name: String){

        val uuid = UUID.randomUUID().toString()
        val chatRef = database.reference.child("chats")
        var createdChat: Chat? = null

        for (selUser in users) {

            val crChat = Chat(name, users.toMutableList(), mutableListOf<Message>(Message("Hello world!", User("test", "test", "test"))), uuid)

            if(selUser.uid == users[0].uid)
                createdChat = crChat

            chatRef.child(selUser.uid!!).child(uuid).setValue(crChat)
        }

        chtEx.value = createdChat

    }

}