package com.example.chatua

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

class AddedUserAdapter(val lis: AddedUserInt) : RecyclerView.Adapter<AddedUserAdapter.AddedUserViewHolder>() {

    interface AddedUserInt{
        fun addedUserLis(user: User)
    }

    private var userList: List<User> = listOf()

    fun setUserList(list: List<User>){
        userList = list
        notifyDataSetChanged()
    }

    inner class AddedUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameField: TextView = itemView.findViewById(R.id.tytName)
        val imgBut: ImageButton = itemView.findViewById(R.id.action_button)
        var addedUser: User? = null
        fun load(user: User){
            nameField.text = user.name
            addedUser = user
            imgBut.setOnClickListener{
                lis.addedUserLis(addedUser!!)
            }
        }

        fun disableButton(){
            imgBut.isEnabled = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedUserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.added_users_item, parent, false)
        return AddedUserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: AddedUserViewHolder, position: Int) {
        holder.load(userList[position])
        if (position == 0)
            holder.disableButton()
    }
}