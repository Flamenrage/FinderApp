package com.example.finderapp.Implementation

import android.content.Context
import com.example.finderapp.Data.DBrepos
import com.example.finderapp.Data.InfoRepository
import io.realm.Realm
import io.realm.RealmConfiguration

class Elected {

    companion object {

        fun electRepository(repos: InfoRepository, context: Context) {
            initRealm(context)
            val db = Realm.getDefaultInstance()
            val sessionNick = Authorization.getSavedData(context).first
            val copy = db.where(DBrepos::class.java).equalTo("id", repos.id).equalTo("userLogin", sessionNick).findFirst()
            if (copy != null) {
                db.close()
                return
            }
            var realmRepos = DBrepos()
            realmRepos = realmRepos.apply {
                id = repos.id
                name = repos.name.toString()
                description = repos.description.toString()
                language = repos.language.toString()
                forksCount = repos.forksCount
                stargazersCount = repos.stargazersCount
                ownerLogin = repos.ownerLogin.toString()
                ownerAvatarUrl = repos.ownerAvatarUrl.toString()
                commitsUrl = repos.commitsUrl.toString()
                userLogin = sessionNick
            }
            db.beginTransaction()
            db.copyToRealm(realmRepos)
            db.commitTransaction()
            db.close()
        }

        private fun initRealm(context: Context) {
            Realm.init(context)
            val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            Realm.setDefaultConfiguration(config)
        }

        fun getElectedList(context: Context): List<InfoRepository> {
            initRealm(context)
            val db = Realm.getDefaultInstance()
            val sessionNick = Authorization.getSavedData(context).first
            val result = arrayListOf<InfoRepository>()
            val query = db.where(DBrepos::class.java).equalTo("userLogin", sessionNick).findAll()
            for (i in query) {
                val repository = InfoRepository(i.id, i.name, i.description, i.language, i.forksCount, i.stargazersCount,
                    i.ownerLogin, i.ownerAvatarUrl, i.commitsUrl)
                result.add(repository)
            }
            db.close()
            return result
        }

        fun deleteFromElected(id: Int, context: Context) {
            initRealm(context)
            val db = Realm.getDefaultInstance()
            val sessionNick = Authorization.getSavedData(context).first
            db.beginTransaction()
            db.where(DBrepos::class.java).equalTo("id", id).equalTo("userLogin", sessionNick).findFirst()?.deleteFromRealm()
            db.commitTransaction()
            db.close()
        }

        fun containsInElected(id: Int, context: Context): Boolean {
            initRealm(context)
            val db = Realm.getDefaultInstance()
            val sessionNick = Authorization.getSavedData(context).first
            val query = db.where(DBrepos::class.java).equalTo("id", id).equalTo("userLogin", sessionNick).findFirst()
            val result = (query != null)
            db.close()
            return result
        }
    }
}