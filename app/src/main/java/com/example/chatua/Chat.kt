package com.example.chatua

import java.io.Serializable

data class Chat (var name: String = "unknown", var users: MutableList<User> = mutableListOf(), var messages: List<Message> = mutableListOf(), var chatUID: String = "") : Serializable