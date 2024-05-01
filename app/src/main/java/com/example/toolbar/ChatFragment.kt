package com.example.toolbar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment() {
/*
    private var param1: String? = null
    private var param2: String? = null

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messagebox : EditText
    private lateinit var sendbtn : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    val receiverRoom : String? = null
    val senderRoom : String? = null*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_chat, container, false)
        /*
    chatRecyclerView = view.findViewById(R.id.chatrecyclerView)
        messagebox = view.findViewById(R.id.messagebox)
        sendbtn = view.findViewById(R.id.sendbtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList)
        sendbtn.setOnClickListener {

        }*/
        return  view
    }

}