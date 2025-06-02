package com.example.store20

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.store20.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUsernameReg.text.toString()
            val email = binding.editTextEmailReg.text.toString()
            val password = binding.editTextPasswordReg.text.toString()
            val confirmPassword = binding.editTextConfirmPasswordReg.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // TODO: Implement actual registration logic (e.g., save to database/backend)
                    Toast.makeText(this, "Registration Clicked (Placeholder)", Toast.LENGTH_SHORT).show()
                    // For now, navigate to LoginActivity upon successful placeholder registration
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish() // Optional: finish RegistrationActivity
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewGoToLogin.setOnClickListener {
            // Navigate back to LoginActivity
            // Using finish() here effectively takes them back to the existing LoginActivity instance
            // if it's still in the back stack, or creates a new one if specified by launchMode or flags.
            // For a simple flow, finish() is often what's desired.
            finish()
            // Alternatively, to ensure a new LoginActivity or bring to front:
            // val intent = Intent(this, LoginActivity::class.java)
            // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            // startActivity(intent)
        }
    }
}
