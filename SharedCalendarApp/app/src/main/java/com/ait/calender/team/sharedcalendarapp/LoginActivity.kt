package com.ait.calender.team.sharedcalendarapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginClick(v: View) {
        if (!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                etEmail.text.toString(),
                etPassword.text.toString()
        ).addOnSuccessListener {
            // show the MAIN SCREEN
            startActivity(
                    Intent(this@LoginActivity,
                            CalendarActivity::class.java)
            )
        }.addOnFailureListener{
            Toast.makeText(this@LoginActivity,
                    (getString(R.string.login_error) + it.message),Toast.LENGTH_LONG).show()
        }

    }


    fun registerClick(v: View) {
        if (!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            val user = it.user
            user.updateProfile(
                    UserProfileChangeRequest.Builder().
                            setDisplayName(userNameFromEmail(user.email!!))
                            .build()
            )
            Toast.makeText(this@LoginActivity,
                    getString(R.string.registration_ok), Toast.LENGTH_LONG).show()

        }.addOnFailureListener{
            Toast.makeText(this@LoginActivity,
                    (getString(R.string.registration_error) + it.message),Toast.LENGTH_LONG).show()
        }
    }

    private fun isFormValid(): Boolean {
        return when {
            etEmail.text.isEmpty() -> {
                etEmail.error = getString(R.string.et_email_error)
                false
            }
            etPassword.text.isEmpty() -> {
                etPassword.error = getString(R.string.et_password_error)
                false
            }
            else -> true
        }
    }


    private fun userNameFromEmail(email: String) = email.substringBefore("@")
}

