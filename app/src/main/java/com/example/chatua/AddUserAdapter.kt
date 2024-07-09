package com.example.chatua

//class AddUserAdapter {
//}



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

class AddUserAdapter(val lis: AddUserInt) : RecyclerView.Adapter<AddUserAdapter.AddUserViewHolder>() {

    interface AddUserInt{
        fun addUserLis(user: User)
    }

    private var userList: List<User> = listOf()

    fun setUserList(list: List<User>){
        userList = list
        notifyDataSetChanged()
    }

    inner class AddUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameField: TextView = itemView.findViewById(R.id.tytName)
        val imgBut: ImageButton = itemView.findViewById(R.id.action_button)
        var addUser: User? = null
        fun load(user: User){
            nameField.text = user.name
            addUser = user
            imgBut.setOnClickListener{
                lis.addUserLis(addUser!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddUserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_users_item, parent, false)
        return AddUserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: AddUserViewHolder, position: Int) {
        holder.load(userList[position])
    }
}