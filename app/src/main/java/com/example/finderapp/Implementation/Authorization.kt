package com.example.finderapp.Implementation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.example.finderapp.Data.DBdata
import com.example.finderapp.Queries.QueryJson
import com.example.finderapp.FinderActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import org.json.JSONObject

class Authorization {

    companion object {

        private fun initDatabase(context: Context) {
            Realm.init(context)
            val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            Realm.setDefaultConfiguration(config)
        }

        fun checkData(nickname: String, password: String, context: Context, queue: RequestQueue) {
            val query = QueryJson(
                "https://api.github.com/rate_limit",
                Response.Listener {
                    response ->
                        if (dissectionLimit(response) > 60) {
                            saveData(context, nickname, password)
                            val intent = Intent(context, FinderActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_LONG).show()
                        }
                },
                Response.ErrorListener {
                    Toast.makeText(context, "Ошибка авторизации/соединения", Toast.LENGTH_LONG).show()
                },
                nickname, password)
            queue.add(query)
        }

        private fun dissectionLimit(jsonObject: JSONObject): Int {
            val resources = jsonObject.getJSONObject("resources")
            val core = resources.getJSONObject("core")
            return core.getInt("limit")
        }

        private fun saveData(context: Context, _login: String, _password: String) {
            var data = DBdata()
            data = data.apply {
                nickname = _login
                password = _password
            }
            initDatabase(context)
            val db = Realm.getDefaultInstance()
            db.beginTransaction()
            db.copyToRealm(data)
            db.commitTransaction()
            db.close()
        }

        fun anything(context: Context): Boolean {
            initDatabase(context)
            val db = Realm.getDefaultInstance()
            val query = db.where(DBdata::class.java).findFirst()
            val result = (query != null)
            db.close()
            return result
        }

        fun getSavedData(context: Context): Pair<String, String> {
            initDatabase(context)
            val db = Realm.getDefaultInstance()
            val query = db.where(DBdata::class.java).findFirst()
            val result = Pair(query?.nickname!!, query.password)
            db.close()
            return result
        }

        fun deleteData(context: Context) {
            initDatabase(context)
            val db = Realm.getDefaultInstance()
            db.beginTransaction()
            db.where(DBdata::class.java).findFirst()?.deleteFromRealm()
            db.commitTransaction()
            db.close()
        }
    }
}