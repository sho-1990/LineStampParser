package com.example.shsato.linestampparser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.shsato.linestampparser.fragment.LineFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(bar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.title = ""
        }
        if (savedInstanceState == null) {
            val f : Fragment = LineFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_base, f).commit()
        }
    }
}
