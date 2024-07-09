package com.example.chatua

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.firebase.Firebase
import com.google.firebase.analytics.ktxtesting.withAnalyticsForTest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import org.checkerframework.checker.units.qual.Current

class ChatViewModel : ViewModel() {

    val database = Firebase.database

    var foundСhat = MutableLiveData<Chat>()


    fun getIndexAndAddMessage(chatFif: Chat, current: User, inMessage: Message) {

        database.reference.child("chats").child(current.uid!!)
            .child(chatFif.chatUID).child("messages")
            .get().addOnSuccessListener {
                var maxKey = 0

                for(mes in it.children){
                    var key = mes.key!!.toInt()
                    if (key > maxKey)
                        maxKey = key
                }
                addMessage(chatFif, inMessage, maxKey + 1)
            }

    }

    private fun addMessage(chatt: Chat, inMessage: Message, foundIndex: Int){

        val redDB = database.reference.child("chats")

        for(user in chatt.users){

            redDB.child(user.uid!!).child(chatt.chatUID).child("messages").child(foundIndex.toString()).setValue(inMessage)
        }

    }

    fun getChatDataByUid(userUid: String, chatUid: String){

        database.reference.child("chats").child(userUid).child(chatUid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                foundСhat.value = snapshot.getValue(Chat::class.java)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}