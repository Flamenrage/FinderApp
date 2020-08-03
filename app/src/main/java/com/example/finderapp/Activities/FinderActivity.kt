package com.example.finderapp.Activities
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.finderapp.Adapters.CommonAdapter
import com.example.finderapp.Implementation.Authorization
import com.example.finderapp.R
import kotlinx.android.synthetic.main.activity_finder.*

class FinderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finder)
        val fragmentAdapter = CommonAdapter(supportFragmentManager)
        viewPagerId.adapter = fragmentAdapter
        tabsId.setupWithViewPager(viewPagerId)
    }

    override fun onBackPressed() { }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemLogoutId -> {
                Authorization.deleteData(this)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
