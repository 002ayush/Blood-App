package com.example.toolbar

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val messageList: ArrayList<Message>,val context : Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
companion object {
    const val ITEM_SEND = 1
    const val ITEM_RECEIVE = 2
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_SEND -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
                SendViewHolder(itemView)
            }
            ITEM_RECEIVE -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.recieve, parent, false)
                ReceiveViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return  messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        when (holder) {
            is SendViewHolder -> {
                holder.sendMessage.text = message.message
            }
            is ReceiveViewHolder -> {
                holder.receiveMessage.text = message.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND
        }else{
            return ITEM_RECEIVE
        }
    }
    //We have two view instead of single view holder one is for receiving the message and one is for sending the message
    inner class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendMessage: TextView = itemView.findViewById(R.id.txtsendmsg)
    }

    inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.txtrecievemsg)
    }
}