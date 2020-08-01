package com.example.finderapp.Queries

import android.util.Base64
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class QueryString(url: String, list: Response.Listener<String>, errorListener: Response.ErrorListener, nick: String, pass: String
) : StringRequest(Method.GET, url, list, errorListener) {
    private val nickname = nick
    private val password = pass
    private val listener: Response.Listener<String> = list

    override fun deliverResponse(response: String?) {
        listener.onResponse(response)
    }

    override fun getHeaders(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        val data = "$nickname:$password"
        val authorization = "Basic " + Base64.encodeToString(data.toByteArray(), Base64.NO_WRAP)
        headers["Content-Type"] = "application/json"
        headers["Authorization"] = authorization
        return headers
    }
}