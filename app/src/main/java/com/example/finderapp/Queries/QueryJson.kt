package com.example.finderapp.Queries

import android.util.Base64
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class QueryJson(url: String, list: Response.Listener<JSONObject>, errorListener: Response.ErrorListener, nick: String, pass: String
) : Request<JSONObject>(Method.GET, url, errorListener) {
    private val nickname = nick
    private val password = pass
    private var listener: Response.Listener<JSONObject> = list

    override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
        return try {
            val jsonString = java.lang.String(response!!.data, HttpHeaderParser.parseCharset(response.headers))
            Response.success(JSONObject(jsonString.toString()), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (je: JSONException) {
            Response.error(ParseError(je))
        }
    }

    override fun deliverResponse(response: JSONObject?) {
        listener.onResponse(response)
    }

    override fun getHeaders(): MutableMap<String, String> {
        val data = "$nickname:$password"
        val headers = mutableMapOf<String, String>()
        val authorization = "Basic " + Base64.encodeToString(data.toByteArray(), Base64.NO_WRAP)
        headers["Content-Type"] = "application/json"
        headers["Authorization"] = authorization
        return headers
    }
}