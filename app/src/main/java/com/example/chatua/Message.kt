package com.example.chatua

import android.widget.TextView
import java.io.Serializable

data class Message (var text: String = "", var sender: User = User()) : Serializable