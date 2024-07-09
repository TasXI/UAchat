package com.example.chatua

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatua.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.io.Serializable

class MainActivity : AppCompatActivity(), UsersListAdapter.OnItemClick, ChatListAdapter.GoToChat{
    private lateinit var binding: ActivityMainBinding

    var currUser: User? = null
    var usersForGroupChat: GroupChatData? = null
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOut)
        {
            val int = Intent(this, LoginActivity::class.java)
            Firebase.auth.signOut()
            startActivity(int)
            finish()
        }
        if (item.itemId == R.id.groupChat)
        {
            if (currUser != null && usersForGroupChat != null) {

                val int = Intent(this, CreateGroupChatActivity::class.java)

                int.putExtra("users", usersForGroupChat)

                startActivity(int)

            }

        }

        return super.onOptionsItemSelected(item)

    }





    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.usersList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapterUsers = UsersListAdapter(this)
        binding.usersList.adapter = adapterUsers

        binding.chatsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapterChats = ChatListAdapter(this)
        binding.chatsList.adapter = adapterChats

        mainViewModel.loadUser(intent.getStringExtra("userUID")!!)

        mainViewModel.user.observe(this){
            if(it != null){
                binding.helloUser.text = "Hello, ${it.name}"
                currUser = it
            }
        }

        mainViewModel.otherUsers.observe(this){
            adapterUsers.setItems(it!!)
            usersForGroupChat = GroupChatData()

            usersForGroupChat!!.addedUsers.add(currUser!!)
            for (us in it){
                if (us.uid != currUser!!.uid)
                    usersForGroupChat!!.otherUsers.add(us)
            }


        }

        mainViewModel.allChats.observe(this){
            adapterChats.updateChats(it!!)
        }

        mainViewModel.userIsFound.observe(this){
            if (it == true){
                mainViewModel.loadOtherUsers()
                mainViewModel.loadChats()
            }
        }

    }

    override fun onClick(clickUser: User) {
        if (currUser != null) {
            val int = Intent(this, ProfileActivity::class.java)
            int.putExtra("selUser", clickUser)
            int.putExtra("curUser", currUser)

            startActivity(int)
        }
    }

    override fun onClick(chatTo: Chat) {
        if (currUser != null) {

            val int = Intent(this, ChatActivity::class.java)
            int.putExtra("chat", chatTo)
            int.putExtra("user", currUser)
            startActivity(int)

        }
    }
}