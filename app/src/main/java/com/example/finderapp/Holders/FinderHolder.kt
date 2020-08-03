package com.example.finderapp.Holders

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finderapp.Activities.DescriptionActivity
import com.example.finderapp.Implementation.Elected
import com.example.finderapp.Data.InfoRepository
import com.example.finderapp.R

class FinderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.imageViewId)
    private val textViewName: TextView = itemView.findViewById(R.id.textViewNameId)
    private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescriptionId)
    private val textViewLanguage: TextView = itemView.findViewById(R.id.textViewLanguageId)
    private val textViewForks: TextView = itemView.findViewById(R.id.textViewForksId)
    private val textViewStargazers: TextView = itemView.findViewById(R.id.textViewStargazersId)
    private val textViewOwnerName: TextView = itemView.findViewById(R.id.textViewOwnerLoginId)
    private val textViewAuthor: TextView = itemView.findViewById(R.id.textViewMessageLabelId)
    private val switchElected: Switch = itemView.findViewById(R.id.switchElected)

    private fun loadDescription(context: Context, repos: InfoRepository) {
        if (repos.name == "NothingHasBeenFound") {
            return
        }
        val intent = Intent(context, DescriptionActivity::class.java)
        intent.putExtra("ID", repos.id!!)
        intent.putExtra("NAME", repos.name)
        intent.putExtra("DESCRIPTION", repos.description)
        intent.putExtra("LANGUAGE", repos.language)
        intent.putExtra("FORKS_COUNT", repos.forksCount)
        intent.putExtra("STARGAZERS_COUNT", repos.stargazersCount)
        intent.putExtra("COMMITS_URL", repos.commitsUrl)
        intent.putExtra("OWNER_AVATAR_URL", repos.ownerAvatarUrl)
        intent.putExtra("OWNER_LOGIN", repos.ownerLogin)
        context.startActivity(intent)
    }

    fun connect(repos: InfoRepository) {
        switchElected.isChecked = Elected.containsInElected(repos.id!!, itemView.context)
        textViewName.text = repos.name
        textViewDescription.text = if (repos.description == null || repos.description == "null") "" else repos.description
        textViewLanguage.text = if (repos.language == null || repos.language == "null") "" else "Язык: ${repos.language}"
        textViewForks.text = "Ψ: ${repos.forksCount.toString()}"
        textViewStargazers.text = "★: ${repos.stargazersCount.toString()}"
        Glide.with(itemView).load(repos.ownerAvatarUrl).into(imageView)
        textViewAuthor.text = "Автор:"
        textViewOwnerName.text = repos.ownerLogin
        if (repos.name == "NothingHasBeenFound") {
            textViewName.text = "По запросу ничего не найдено."
            textViewForks.text = ""; textViewStargazers.text = ""; textViewAuthor.text = ""
        }
        itemView.setOnClickListener {
            loadDescription(itemView.context, repos)
        }
        switchElected.setOnCheckedChangeListener{ _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                Elected.electRepository(repos, itemView.context)
            } else {
                Elected.deleteFromElected(repos.id, itemView.context)
            }
        }
    }
}