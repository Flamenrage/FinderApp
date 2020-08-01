package com.example.finderapp.Holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finderapp.Data.InfoCommit
import com.example.finderapp.R

class DescriptionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessageId)
    private val textViewDate: TextView = itemView.findViewById(R.id.textViewDateId)
    private val imageView: ImageView = itemView.findViewById(R.id.imageViewId)
    private val textViewNickname: TextView = itemView.findViewById(R.id.textViewAuthorLoginId)

    fun bind(commit: InfoCommit) {
        textViewMessage.text = if (commit.message == null || commit.message == "null") "missed"
            else commit.message
        textViewDate.text = commit.date ?: "missed"
        textViewNickname.text = commit.authorLogin ?: "unknown"
        Glide.with(itemView).load(commit.authorAvatarUrl).into(imageView)
    }
}