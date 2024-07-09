package com.example.chatua

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatListAdapter(val goToChat: GoToChat) : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    interface GoToChat{
        fun onClick(chatTo: Chat)
    }

    private var chats: List<Chat> = listOf()

    fun updateChats(chaaaaa: List<Chat>){
        chats = chaaaaa
        notifyDataSetChanged()
    }

    inner class ChatListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tytName)
        val itemL = itemView.findViewById<LinearLayout>(R.id.linear)

        private lateinit var chat: Chat

        fun setChat(chatIn: Chat){
            name.text = chatIn.name
            chat = chatIn
        }

        fun linearClickSet(){
            itemL.setOnClickListener{
                goToChat.onClick(chat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.users_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.setChat(chats[position])
        holder.linearClickSet()
    }
}