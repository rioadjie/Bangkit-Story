package com.md12.rio.bangkitstory.utils

import android.content.Context
import android.widget.Toast

object Message {
    fun setMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}