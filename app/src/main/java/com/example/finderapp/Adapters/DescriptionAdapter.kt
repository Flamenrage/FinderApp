package com.example.finderapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finderapp.Data.InfoCommit
import com.example.finderapp.Holders.DescriptionHolder
import com.example.finderapp.R

class DescriptionAdapter(private val commits: ArrayList<InfoCommit>) : RecyclerView.Adapter<DescriptionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionHolder {
        val main = LayoutInflater.from(parent.context).inflate(R.layout.itm_description, parent, false)
        return DescriptionHolder(main)
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    override fun onBindViewHolder(holder: DescriptionHolder, position: Int) {
        holder.bind(commits[position])
    }
}