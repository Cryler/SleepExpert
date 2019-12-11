package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {
companion object{
    const val REGISTER_TAG = "RegisterActivity"
}
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            performRegister()
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }


    }

    private fun performRegister() {
        val labelUsername = findViewById<EditText>(R.id.labelUsernameRegister).text.toString()
        val labelEmail = findViewById<EditText>(R.id.labelEmailRegister).text.toString()
        val labelPassword = findViewById<EditText>(R.id.labelPasswordRegister).text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        if (labelUsername.isEmpty()) {
            Toast.makeText(this, "Bitte Username ausfüllen", Toast.LENGTH_SHORT).show()
            return
        }
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

        auth.createUserWithEmailAndPassword(labelEmail, labelPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(REGISTER_TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext, "Registrierung erfolgreich",
                        Toast.LENGTH_SHORT
                    ).show()

                    saveUserToDatabase()

                    startActivity(Intent(this, Login::class.java))
                } else {
                    // If register fails, display a message to the user.
                    Log.w(REGISTER_TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Registrierung fehlgeschlagen.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserToDatabase() {
        val uid = auth.uid ?: ""
        val level = 0

        val user = hashMapOf("uid" to uid, "username" to labelUsernameRegister.text.toString(), "level" to level)
        db.document("users/$uid")
            .set(user)
            .addOnSuccessListener {
                Log.d(REGISTER_TAG, "User successfully saved to firebase database")
            }
    }
}


