package com.example.chatua

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatua.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding

    val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userCur = intent.getSerializableExtra("curUser") as User

        val userSel = intent.getSerializableExtra("selUser") as User

        val usList = listOf<User>(userCur, userSel)


        profileViewModel.chtEx.observe(this){

            if (it == null){
                profileViewModel.createChat()
            }
            else{
                val int = Intent(this, ChatActivity::class.java)
                int.putExtra("chat", it)
                int.putExtra("user", userCur)
                startActivity(int)
                finish()
            }

        }

        profileViewModel.user.observe(this){

            binding.textView2.text = it!!.name
            binding.textView4.text = it.email

            binding.button.setOnClickListener{

                profileViewModel.chatIsExist()
                binding.button.isEnabled = false
            }

        }

        profileViewModel.setAllUsers(usList)

        profileViewModel.setUser(userSel)


    }
}