package com.example.finderapp.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.example.finderapp.Implementation.Authorization
import com.example.finderapp.R
import kotlinx.android.synthetic.main.activity_authorization.*

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        if (Authorization.anything(this)) {
            val intent = Intent(this, FinderActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
        buttonEnter.setOnClickListener {
            val nickname = editFieldNicknameId.text.toString()
            val password = editFieldPasswordId.text.toString()
            if (nickname.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Введите одновременно все данные", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            Authorization.checkData(nickname, password, this,
                Volley.newRequestQueue(this))
        }
    }
    override fun onBackPressed() { }
}
