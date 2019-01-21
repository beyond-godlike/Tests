package com.example.dia.tests.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dia.tests.R
import android.widget.ArrayAdapter
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    val btnNames = arrayOf(
        "C++",
        "Java",
        "Verbs",
        "Verbs 2",
        "Present Perfect or Past Simple",
        "Present Simple or Past Simple",
        "Present Simple or Present Continious"
    )

    val docNames = arrayOf(
        "q.xml",
        "j.xml",
        "v.xml",
        "v1.xml",
        "presPerfOrPastSimple.xml",
        "presSimpleOrPsatSimple.xml",
        "presSimpleOrPresContinious.xml"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.listView)

        //android.R.layout.simple_list_item_1
        val adapter = ArrayAdapter(this, R.layout.list_item, btnNames)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(this@MainActivity, TestActivity::class.java)
            Log.d("dddd", docNames?.get(position).toString())
            intent.putExtra("value", docNames?.get(position).toString())
            startActivity(intent)
        }
    }
}
