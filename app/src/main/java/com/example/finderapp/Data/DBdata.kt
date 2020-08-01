package com.example.finderapp.Data

import io.realm.RealmObject

open class DBdata : RealmObject() {
    lateinit var nickname: String
    lateinit var password: String
}