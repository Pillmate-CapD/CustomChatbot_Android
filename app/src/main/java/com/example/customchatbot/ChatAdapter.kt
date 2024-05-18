package com.example.customchatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

enum class ButtonType {
    BUTTON_1,
    BUTTON_2
}

// 버튼 클릭 이벤트를 처리할 ButtonClickListener 인터페이스
interface ButtonClickListener {
    fun onButtonClicked(buttonType: ButtonType)
}
class ChatAdapter(private val chatItems: MutableList<ChatItem>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var buttonClickListener: ButtonClickListener? = null

    // ChatActivity에서 버튼 클릭 리스너 설정
    fun setButtonClickListener(listener: ButtonClickListener) {
        buttonClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatItem = chatItems[position]
        holder.bind(chatItem)
    }

    override fun getItemCount(): Int = chatItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val leftChatView: LinearLayout = itemView.findViewById(R.id.left_chat_view)
        private val rightChatView: LinearLayout = itemView.findViewById(R.id.right_chat_view)
        private val leftChatTextView: TextView = itemView.findViewById(R.id.left_chat_txt)
        private val rightChatTextView: TextView = itemView.findViewById(R.id.right_chat_txt)
        private val btn1: Button = itemView.findViewById(R.id.btn1)
        private val btn2: Button = itemView.findViewById(R.id.btn2)

        fun bind(chatItem: ChatItem) {
            if (chatItem.isSentByMe) {
                leftChatView.visibility = View.GONE
                rightChatView.visibility = View.VISIBLE
                rightChatTextView.text = chatItem.message
            } else {
                rightChatView.visibility = View.GONE
                leftChatView.visibility = View.VISIBLE
                leftChatTextView.text = chatItem.message
            }

            btn1.setOnClickListener {
                // 버튼 1 클릭 시 MainActivity의 메서드 호출
                // 여기에 버튼 1 클릭 시 처리할 내용 추가
                buttonClickListener?.onButtonClicked(ButtonType.BUTTON_1)
            }

            btn2.setOnClickListener {
                // 버튼 2 클릭 시 MainActivity의 메서드 호출
                // 여기에 버튼 2 클릭 시 처리할 내용 추가
                buttonClickListener?.onButtonClicked(ButtonType.BUTTON_2)
            }
        }
    }


}