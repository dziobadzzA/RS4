package com.example.satapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_email_password.*


@Suppress("DEPRECATION")
class EmailPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_password)
        supportActionBar!!.hide()
        // Views
        mEmailField = findViewById<EditText>(R.id.field_email)
        mPasswordField = findViewById<EditText>(R.id.field_password)

        // Buttons
        val emailSignInButton: Button = findViewById(R.id.email_sign_in_button)
        emailSignInButton.setOnClickListener(this)
        val emailCreateAccountButton: Button = findViewById(R.id.email_create_account_button)
        emailCreateAccountButton.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = AuthStateListener { firebaseAuth -> val user = firebaseAuth.currentUser
            updateUI(user)
        }

    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    private fun createAccount(email: String, password: String) {

        if (!validateForm()) { return }

        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult?> { task ->


                    if (!task.isSuccessful) {
                        Toast.makeText(this@EmailPasswordActivity, "auth_failed",
                                Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                   	 Toast.makeText(this@EmailPasswordActivity, "good job",
                                Toast.LENGTH_SHORT).show()
                    }
                    
                })
    }

    private fun signIn(email: String, password: String) {

        if (!validateForm()) { return }

        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult?> { task ->

                    if (!task.isSuccessful) {
                        Toast.makeText(this@EmailPasswordActivity, "auth_failed",
                                Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
								var intent = Intent(this, MainActivity()::class.java)
   	                 	intent.putExtra(MainActivity.MAIL, email)
                    		startActivity(intent)                    
                    }
                })
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = mEmailField!!.text.toString()

        if (TextUtils.isEmpty(email)) { mEmailField!!.error = "Required."; valid = false }
        else { mEmailField!!.error = null }

        val password = mPasswordField!!.text.toString()

        if (TextUtils.isEmpty(password)) { mPasswordField!!.error = "Required."; valid = false }
        else { mPasswordField!!.error = null }

        return valid
    }

    @SuppressLint("WrongViewCast")
    fun updateUI(user: FirebaseUser?) {

        val emailPasswordButtons:LinearLayout =  findViewById(R.id.email_password_buttons)
        val emailPasswordFields: LinearLayout = findViewById(R.id.email_password_fields)

        if (user != null) {
            //mDetailTextView!!.text = user.email
            //emailPasswordButtons.visibility = View.GONE
            //emailPasswordFields.visibility = View.GONE
            //signOutButton.visibility = View.VISIBLE

        } else {
            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View) {
         when (v.id) {
             R.id.email_create_account_button -> {
                 createAccount(mEmailField!!.text.toString(), mPasswordField!!.text.toString())
             }
             R.id.email_sign_in_button -> {
                 signIn(mEmailField!!.text.toString(), mPasswordField!!.text.toString())
             }
         }
    }

}

