package com.example.finderapp.Implementation

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.example.finderapp.Adapters.DescriptionAdapter
import com.example.finderapp.Queries.QueryString
import com.example.finderapp.Data.InfoCommit
import org.json.JSONArray
import org.json.JSONObject

class Description {

    companion object {

        fun getCommits(url: String, context: Context, queue: RequestQueue,
                       recyclerView: RecyclerView, progressBar: ProgressBar) {
            val data = Authorization.getSavedData(context)
            val request = QueryString(
                url,
                Response.Listener {
                        response -> show(recyclerView, dissectionCommit(response), context)
                        progressBar.visibility = ProgressBar.GONE
                },
                Response.ErrorListener {
                    Toast.makeText(context,"Ошибка запроса", Toast.LENGTH_LONG).show()
                    progressBar.visibility = ProgressBar.GONE
                },
                data.first, data.second)
            queue.add(request)
        }

        private fun show(recyclerView: RecyclerView,
                         list: ArrayList<InfoCommit>, context: Context) {
            recyclerView.adapter = DescriptionAdapter(list)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        private fun dissectionCommit(response: String): ArrayList<InfoCommit> {
            val list = ArrayList<InfoCommit>()
            val array = JSONArray(response)
            for (i in 0 until array.length()) {
                if (i > 9){
                    break
                }
                val item = array.getJSONObject(i)
                val commit = item.getJSONObject("commit")
                val commitMessage = commit.getString("message")
                var commitAuthor: JSONObject
                var commitDate: String? = null
                var authorLogin: String? = null
                var authorAvatarUrl: String? = null
                if (!commit.isNull("author") && !item.isNull("author")) {
                    commitAuthor = commit.getJSONObject("author")
                    commitDate = commitAuthor.getString("date").replace("T", " ").replace("Z", " ")
                    authorLogin = commitAuthor.getString("name")
                    authorAvatarUrl = item.getJSONObject("author").getString("avatar_url")
                }
                list.add(InfoCommit(commitMessage, commitDate, authorLogin, authorAvatarUrl))
            }
            return list
        }
    }
}