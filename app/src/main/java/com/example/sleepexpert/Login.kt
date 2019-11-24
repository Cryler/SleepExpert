package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val buttonBack = findViewById<Button>(R.id.buttonGoToRegister)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
           performLogin()
        }

// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    private fun performLogin() {
        val labelEmail = findViewById<EditText>(R.id.labelEmailLogin).text.toString()
        val labelPassword = findViewById<EditText>(R.id.labelPasswordLogin).text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()


        if (labelEmail.isEmpty()) {
            Toast.makeText(this, "Bitte E-Mail-Adresse ausfüllen", Toast.LENGTH_SHORT).show()
            return
        } else {
            if (!labelEmail.trim().matches(emailPattern)) {
                Toast.makeText(this, "Ungültige E-Mail-Adresse", Toast.LENGTH_SHORT).show();
                return
            }
        }
        if (labelPassword.isEmpty()) {
            Toast.makeText(this, "Bitte Passwort ausfüllen", Toast.LENGTH_SHORT).show()
            return
        } else {
            if (labelPassword.length < 6) {
                Toast.makeText(this, "Ungültiges Passwort", Toast.LENGTH_SHORT).show();
                return
            }
        }

        auth.signInWithEmailAndPassword(labelEmail, labelPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "loginUserWithEmail:success")
                    val user = auth.currentUser

                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "loginUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }


}