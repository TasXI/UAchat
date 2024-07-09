package com.example.chatua

import java.io.Serializable

data class GroupChatData(var name: String = "Empty", val addedUsers: MutableList<User> = mutableListOf(), val otherUsers: MutableList<User> = mutableListOf() ) : Serializable