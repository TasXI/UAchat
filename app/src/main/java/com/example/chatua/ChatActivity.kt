package com.example.chatua

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatua.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    val chatViewModel: ChatViewModel by viewModels()

    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setBackgroundDrawable( ColorDrawable(Color.parseColor("#00bfff")) )

        binding.messageResView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MessageListAdapter()
        binding.messageResView.adapter = adapter


        val chat = intent.getSerializableExtra("chat") as Chat

        val currentUser = intent.getSerializableExtra("user") as User



        chatViewModel.getChatDataByUid(currentUser.uid!!, chat.chatUID)

        chatViewModel.foundСhat.observe(this){loadetChat ->
            supportActionBar!!.title = loadetChat!!.name

            adapter.setMessages(loadetChat.messages, currentUser)

            binding.scrollD.post {
                binding.scrollD.fullScroll(View.FOCUS_DOWN)
            }
            
            binding.sendButImg.setOnClickListener{

                val messageText = binding.textOfM.text.toString()
                val crMessage = Message(messageText, currentUser)

                if (messageText != "") {
                    chatViewModel.getIndexAndAddMessage(loadetChat, currentUser, crMessage)
                    binding.textOfM.setText("")
                }
            }
        }



    }
}