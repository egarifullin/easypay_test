package ru.evgendev.easypaytest.ui.utils

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.Fade
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey.Builder
import androidx.security.crypto.MasterKey
import ru.evgendev.easypaytest.R


object Utils {
    private const val SHARED_PREFERENCES_KEY = "shared_pref_key"
    private const val TOKEN_KEY = "token_key"
    private const val LOG_TOKEN_KEY = "log_token"
    private lateinit var sPref: SharedPreferences

    fun init(context: Context): Boolean {
        getEncryptedSharedPreferences(context)
        return getUserToken() != ""
    }

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey: MasterKey = Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sPref = EncryptedSharedPreferences.create(
            context,
            SHARED_PREFERENCES_KEY,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return sPref
    }

    fun writeTokenToSharedPref(token: String) {
        try {
            sPref.edit()?.putString(TOKEN_KEY, token)?.apply()
        } catch (e: Exception) {
            Log.e(
                LOG_TOKEN_KEY,
                "Error on getting encrypted shared preferences and write token",
                e
            )
        }

    }

    private fun getUserToken(): String {
        return sPref.getString(TOKEN_KEY, "") ?: ""
    }

    fun showErrorDialog(
        context: Context,
        title: String,
        text: String,
        llBackgroundShadow: LinearLayout
    ) {
        val transition = Fade()
        transition.duration = 300
        val dialog = Dialog(context, R.style.MaterialAlertDialog_rounded)
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_error, null, false)
        dialog.setContentView(dialogView)
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wlp = window.attributes
        val btnClose: Button = dialog.findViewById(R.id.btnCloseErrorDialog)
        val tvTitle: TextView = dialog.findViewById(R.id.tvTitleErrorDialog)
        val tvText: TextView = dialog.findViewById(R.id.tvTextErrorDialog)
        tvTitle.text = title
        tvText.text = text
        wlp.gravity = Gravity.BOTTOM
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        window.attributes = wlp
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        TransitionManager.beginDelayedTransition(
            llBackgroundShadow,
            transition
        )
        llBackgroundShadow.visibility = View.VISIBLE
        btnClose.setOnClickListener {
            dialog.dismiss()
            TransitionManager.beginDelayedTransition(
                llBackgroundShadow,
                transition
            )
            llBackgroundShadow.visibility = View.GONE
        }
    }
}