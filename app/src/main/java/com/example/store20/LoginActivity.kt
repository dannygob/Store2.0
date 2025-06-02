package com.example.store20

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.store20.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // TODO: Implement actual login logic
            if (email.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Login Clicked (Placeholder)", Toast.LENGTH_SHORT).show()
                // For now, navigate to MainActivity upon any input
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Optional: finish LoginActivity so user can't go back
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}
