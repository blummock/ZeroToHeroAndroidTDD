package ru.easycode.zerotoheroandroidtdd

import android.util.Patterns

interface EmailValidator {

    fun isValid(email: String): Boolean

    object Base : EmailValidator {
        override fun isValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
        }
    }
}