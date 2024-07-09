package com.example.chatua

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class UsersListAdapter(val mainInt: OnItemClick) : RecyclerView.Adapter<UsersListAdapter.UsersViewHolder>() {

    interface OnItemClick{
        fun onClick(clickUser: User)
    }


    var itemUsers = listOf<User>()

    fun setItems(newUsers: List<User>){
        itemUsers = newUsers
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(itemView: View) : ViewHolder(itemView){

        val name = itemView.findViewById<TextView>(R.id.tytName)
        val itemL = itemView.findViewById<LinearLayout>(R.id.linear)

        private lateinit var user: User

        fun setUser(userIn: User){
            name.text = userIn.name
            user = userIn
        }

        fun linearClickSet(){
            itemL.setOnClickListener{
                mainInt.onClick(user)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.users_item, parent, false)
        return UsersViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemUsers.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.setUser(itemUsers[position])
        holder.linearClickSet()
    }
}