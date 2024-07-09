package com.example.chatua

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.chatua.databinding.ActivityLoginBinding
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.chatua.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val signUpViewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)


        signUpViewModel.registrationIsSuccess.observe(this) {
            binding.signBut.isEnabled = true
            if (it == false){
                Toast.makeText(this, "Failure!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            }
    }



        binding.signBut.setOnClickListener {
            binding.signBut.isEnabled = false
            if (binding.userName.text.toString() != "" && binding.email.text.toString() != "" && binding.password.text.toString() != "") {

                signUpViewModel.signUp(
                    binding.userName.text.toString(),
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            }
            else
            {
                signUpViewModel.dataIncorrect()
            }
        }

    }
}