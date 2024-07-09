package com.example.chatua

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.flow.merge
import org.checkerframework.checker.units.qual.Current

class MessageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var currentUser = User()
    var messageList: List<Message> = listOf()

    fun setMessages(messages: List<Message>, currentUser: User) {
        messageList = messages
        this.currentUser = currentUser
        notifyDataSetChanged()
    }

    class MessageSenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userNameView = itemView.findViewById<TextView>(R.id.userNameText)
        private val messageText = itemView.findViewById<TextView>(R.id.messageText)

        fun setUserName(name: String){
            userNameView.text = name
        }

        fun setMessageText(text: String){
            messageText.text = text
        }

    }

    class MessageViewRecHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText = itemView.findViewById<TextView>(R.id.messageText)

        fun setMessageText(text: String){
            messageText.text = text
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0)
            return MessageViewRecHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_receiver_item, parent, false))
        else
            return MessageSenViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_sender_item, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList[position + 1].sender.uid == currentUser.uid)
            return 0
        else
            return 1
    }

    override fun getItemCount(): Int {
        return messageList.size - 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val recHolder = holder as MessageViewRecHolder
            recHolder.setMessageText(messageList[position + 1].text)
        }
        else{
            val senHolder = holder as MessageSenViewHolder
            senHolder.setMessageText(messageList[position + 1].text)
            senHolder.setUserName(messageList[position + 1].sender.name!!)
        }
    }
}