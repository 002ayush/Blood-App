package com.example.toolbar


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(val bloodgrps : ArrayList<Blooddetails>)  :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val currentbloodgrp = bloodgrps[position]
         holder.bloodgrptextVal.text = currentbloodgrp.donarbloodgroupValue
         holder.bloodgrpcnt.text = currentbloodgrp.count
    }

    override fun getItemCount(): Int {
        return bloodgrps.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bloodgrptextVal = itemView.findViewById<TextView>(R.id.bloodgrptxt)
        val bloodgrpcnt = itemView.findViewById<TextView>(R.id.bloodgrpcount)
    }
}