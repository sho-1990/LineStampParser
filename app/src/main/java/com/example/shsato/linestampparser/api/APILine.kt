package com.example.shsato.linestampparser.api

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by sh.sato on 2017/08/24.
 */
class APILine {

    private lateinit var mListener: (MutableList<String>) -> Unit

    fun start(url: String, listener: (MutableList<String>) -> Unit) {

        mListener = listener

        object : Thread() {
            override fun run() {
                super.run()
                val doc = Jsoup.connect(url).get()
                parse(doc)
            }
        }.start()

    }

    private fun parse(result: Document) {
        Log.d("test", result.toString())
    }
}