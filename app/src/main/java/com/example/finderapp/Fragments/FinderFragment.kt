package com.example.finderapp.Fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.finderapp.Implementation.Searching
import com.example.finderapp.R
import kotlinx.android.synthetic.main.fragment_finder.*

class FinderFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_finder, container, false)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearchId)
        recyclerView = view.findViewById(R.id.recyclerViewId)
        requestQueue = Volley.newRequestQueue(this.activity)
        recyclerView.addItemDecoration(DividerItemDecoration(this.activity, 1))
        progressBar = view.findViewById(R.id.progressBarId)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeId)
        swipe.setColorSchemeResources(R.color.colorPrimaryDark)
        buttonSearch.setOnClickListener { updateView() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeId)
        swipe.setOnRefreshListener {
            updateView()
            swipe.isRefreshing = false
        }
    }

    private fun updateView() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = ProgressBar.VISIBLE
        val query = editTextId.text.toString()
        if (query.isBlank()) {
            progressBar.visibility = ProgressBar.GONE
            return
        }
        Searching.getRepositories(query, this.activity!!, requestQueue, recyclerView, progressBar)
    }
}
