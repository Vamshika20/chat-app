package com.example.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class messageadapter(val context: Context,val messageList: ArrayList<message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1;
    val ITEM_SENT=2;

    override fun getItemViewType(position: Int): Int {
        val currentmessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderid)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentmessage = itemView.findViewById<TextView>(R.id.txt_sent_message)

    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val  receivemessage = itemView.findViewById<TextView>(R.id.txt_receive_message)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1)
        {
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }
        else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {

        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentmessage = messageList[position]

        if(holder.javaClass==SentViewHolder::class.java){


            val viewHolder = holder as SentViewHolder
            holder.sentmessage.text = currentmessage.message
        }
        else{
            val viewHolder= holder as ReceiveViewHolder
            holder.receivemessage.text = currentmessage.message

        }
    }

}