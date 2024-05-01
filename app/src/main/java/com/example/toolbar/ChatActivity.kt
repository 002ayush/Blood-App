package com.example.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messagebox : EditText
    private lateinit var sendbtn : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var databaseReference: DatabaseReference
    var receiverRoom : String? = null
    var senderRoom : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chatRecyclerView = findViewById(R.id.chatrecyclerView)
        messagebox = findViewById(R.id.messagebox)
        sendbtn = findViewById(R.id.sendbtn)
        messageList = ArrayList()

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(messageList,this)
        chatRecyclerView.adapter = messageAdapter
        databaseReference = FirebaseDatabase.getInstance().getReference()
        //for showing the messages in the recycler View

        val txtchat = findViewById<TextView>(R.id.txtchat)
        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiveruid + senderUid
        receiverRoom = senderUid + receiveruid
        txtchat.text = name
        Log.d("Name","My name is $name")
        supportActionBar?.title = "Ayush"
        //Adding message to the database
        sendbtn.setOnClickListener {
            val message  = messagebox.text.toString()
            val messageobject = Message(message,senderUid)
            databaseReference.child("chats").child(senderRoom!!).child("messages").push().setValue(messageobject).addOnSuccessListener {
                databaseReference.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageobject)
            }
            messagebox.text.clear()
        }
        databaseReference.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.child("message").value.toString()
                    val senderId = postSnapshot.child("senderId").value.toString()
                    val messagedetails = Message(message, senderId)
                    messageList.add(messagedetails)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}