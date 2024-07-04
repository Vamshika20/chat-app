package com.example.chat
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class UserAdapter(val context:Context,val userList: ArrayList<UserData>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        TODO("Not yet implemented")
        val view:View = LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return userList.size
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        TODO("Not yet implemented")
        val currentuser =userList[position]

        holder.textName.text = currentuser.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)

            intent.putExtra("name",currentuser.id)
            intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)


            context.startActivity(intent)
        }
    }
    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)

    }
}