package com.example.finderapp.Activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.finderapp.Implementation.Elected
import com.example.finderapp.Implementation.Description
import com.example.finderapp.Data.InfoRepository
import com.example.finderapp.R
import kotlinx.android.synthetic.main.activity_info.*

class DescriptionActivity : AppCompatActivity() {

    companion object {
        const val ID = "ID"; const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"; const val LANGUAGE = "LANGUAGE"
        const val FORKS_COUNT = "FORKS_COUNT"; const val STARGAZERS_COUNT = "STARGAZERS_COUNT"
        const val OWNER_LOGIN = "OWNER_LOGIN"; const val OWNER_AVATAR_URL = "OWNER_AVATAR_URL"
        const val COMMITS_URL = "COMMITS_URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        setupActionBar()
        val queue = Volley.newRequestQueue(this)
        progressBarId.visibility = ProgressBar.VISIBLE
        setDataViews(queue)
        recyclerViewId.addItemDecoration(DividerItemDecoration(this, 1))
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        title = "Подробности"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setDataViews(queue: RequestQueue) {
        recyclerViewId.visibility = View.GONE
        val repository = InfoRepository(intent?.extras?.getInt(ID), intent?.extras?.getString(
            NAME
        ), intent?.extras?.getString(DESCRIPTION),
            intent?.extras?.getString(LANGUAGE), intent?.extras?.getInt(
                FORKS_COUNT
            ), intent?.extras?.getInt(STARGAZERS_COUNT),
            intent?.extras?.getString(OWNER_LOGIN), intent?.extras?.getString(
                OWNER_AVATAR_URL
            ), intent?.extras?.getString(COMMITS_URL)
        )
        textViewNameId.text = repository.name
        Glide.with(this).load(repository.ownerAvatarUrl).into(imageViewId)
        textViewOwnerLoginId.text = repository.ownerLogin
        repository.commitsUrl?.let {
            Description.getCommits(it, this, queue, recyclerViewId, progressBarId)
        }
        switchElected.isChecked = Elected.containsInElected(repository.id!!, this)
        switchElected.setOnCheckedChangeListener{ _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                Elected.electRepository(repository, this)
            } else {
                Elected.deleteFromElected(repository.id, this)
            }
        }
        recyclerViewId.visibility = View.VISIBLE
    }
}
