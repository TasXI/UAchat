package com.example.chatua

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatua.databinding.ActivityCreateGroupChatBinding

class CreateGroupChatActivity : AppCompatActivity(), AddedUserAdapter.AddedUserInt, AddUserAdapter.AddUserInt {

    lateinit var binding: ActivityCreateGroupChatBinding
    lateinit var allUsers: GroupChatData
    private val createGroupChatViewModel: CreateGroupChatViewModel by viewModels()

    lateinit var addAdapter: AddUserAdapter
    lateinit var addedAdapter: AddedUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allUsers = intent.getSerializableExtra("users") as GroupChatData

        binding.addedaddet.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        addedAdapter = AddedUserAdapter(this)
        addedAdapter.setUserList(allUsers.addedUsers)
        binding.addedaddet.adapter = addedAdapter

        binding.addadd.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        addAdapter = AddUserAdapter(this)
        addAdapter.setUserList(allUsers.otherUsers)
        binding.addadd.adapter = addAdapter

        createGroupChatViewModel.chtEx.observe(this){
            if(it != null){
                val int = Intent(this, ChatActivity::class.java)
                int.putExtra("chat", it)
                int.putExtra("user", createGroupChatViewModel.curr!!)
                startActivity(int)
                finish()
            }
        }

        binding.createChatButton.setOnClickListener{
            if (binding.editNameChat.text.toString() == "")
                Toast.makeText(this, "Enter the name of chat!", Toast.LENGTH_SHORT).show()
            else if(allUsers.addedUsers.size < 3) {
                Toast.makeText(this, "Must be 3 or more users", Toast.LENGTH_SHORT).show()
            }
            else{
                createGroupChatViewModel.curr = allUsers.addedUsers[0]
                createGroupChatViewModel.createGroupChat(allUsers.addedUsers, binding.editNameChat.text.toString())
            }
        }
    }

    override fun addedUserLis(user: User) {
        allUsers.addedUsers.remove(user)
        allUsers.otherUsers.add(user)
        addedAdapter.setUserList(allUsers.addedUsers)
        addAdapter.setUserList(allUsers.otherUsers)
    }

    override fun addUserLis(user: User) {
        allUsers.otherUsers.remove(user)
        allUsers.addedUsers.add(user)
        addedAdapter.setUserList(allUsers.addedUsers)
        addAdapter.setUserList(allUsers.otherUsers)
    }


}