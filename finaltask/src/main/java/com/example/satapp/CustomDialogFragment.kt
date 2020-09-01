package com.example.satapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.example.satapp.MainActivity

class CustomDialogFragment(mAuth: FirebaseAuth?) : DialogFragment() {

    var mAuth:FirebaseAuth? = mAuth

    @NonNull
    @Override
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
                .setTitle("Out account")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(true)
                .setMessage("click Ok?")
                .setPositiveButton("OK")
                {
                    dialogInterface: DialogInterface, i: Int ->
                    mAuth!!.signOut()
                    var intent = Intent(builder.context, EmailPasswordActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel", null)
                .create()
    }


}


