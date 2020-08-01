package com.example.finderapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finderapp.Data.InfoRepository
import com.example.finderapp.Holders.FinderHolder
import com.example.finderapp.R

class FinderAdapter(private val repos: List<InfoRepository>
) : RecyclerView.Adapter<FinderHolder>() {

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinderHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.itm_finder, parent, false)
        return FinderHolder(rootView)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: FinderHolder, position: Int) {
        holder.connect(repos[position])
    }
}