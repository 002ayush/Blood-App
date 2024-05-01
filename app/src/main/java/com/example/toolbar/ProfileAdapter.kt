package com.example.toolbar

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(var users : List<UserDetails>,val context: Activity) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    private lateinit var myListener: ItemOnClickListener
    private lateinit var mylistenercall : ItemOnClickListener2
    interface  ItemOnClickListener2{
        fun onitemClickingcall(position: Int)
    }
    interface ItemOnClickListener{
        fun onitemClicking(position: Int)

    }
    fun setOnitemClick(listener: ItemOnClickListener){
        myListener = listener
    }
    fun setOnitemClickCall(listener: ItemOnClickListener2){
        mylistenercall = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_user,parent,false)
        return  ViewHolder(itemView,myListener,mylistenercall)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val useritems = users[position]
            holder.name.text = useritems.name
            holder.email.text = useritems.email
            holder.bloodgrp.text = useritems.bloodgrp


    }
    fun updateList(newList: List<UserDetails>) {

        users=newList
        notifyDataSetChanged()
    }
    class ViewHolder(itemView : View,listener: ItemOnClickListener,mylistenercall : ItemOnClickListener2) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.username)
        val email = itemView.findViewById<TextView>(R.id.useremail)
        val bloodgrp =  itemView.findViewById<TextView>(R.id.bloodimgView)
        init {
            itemView.findViewById<Button>(R.id.btnchat).setOnClickListener {
                listener.onitemClicking(adapterPosition)
            }
            itemView.findViewById<Button>(R.id.btncall).setOnClickListener {
                mylistenercall.onitemClickingcall(adapterPosition)
            }
        }
    }
}