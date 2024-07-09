package com.example.chatua

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatua.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val logViewModel: LogViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        logViewModel.account.observe(this){
            if (it == null){
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intMain = Intent(this,MainActivity::class.java)
                intMain.putExtra("userUID", it.uid)
                startActivity(intMain)
                finish()
            }
        }

        binding.signBut.setOnClickListener{
            val intSign = Intent(this, SignUpActivity::class.java)
            startActivity(intSign)
        }

        binding.logBut.setOnClickListener{

            if (binding.email.text.toString() != "" && binding.password.text.toString() != "") {
                logViewModel.logIn(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            }
            else
            {
                logViewModel.logFailure()
            }
        }



    }
}